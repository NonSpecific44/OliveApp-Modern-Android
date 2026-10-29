import re, sys, pathlib, shutil
root = pathlib.Path(sys.argv[1]) / "smali" / "olive" / "app"
helpers = pathlib.Path(sys.argv[2])

# 1. AsyncTask.execute(...) -> Compat.exec(task, params): restore parallel execution.
pat = re.compile(r"invoke-virtual(/range)? \{([^}]*)\}, L[\w/$]+;->execute\(\[Ljava/lang/Object;\)Landroid/os/AsyncTask;")
n = 0
for f in root.rglob("*.smali"):
    s = f.read_text()
    s2, k = pat.subn(lambda m: f"invoke-static{m.group(1) or ''} {{{m.group(2)}}}, Lolive/app/Compat;->exec(Landroid/os/AsyncTask;[Ljava/lang/Object;)Landroid/os/AsyncTask;", s)
    if k:
        f.write_text(s2); n += k
print("AsyncTask call sites patched:", n)

def edit(name, old, new, count=1):
    f = root / name
    s = f.read_text()
    assert s.count(old) == count, (name, old, s.count(old))
    f.write_text(s.replace(old, new))

# 2. ImageLoader: always cache artwork in the app's private cache (scoped storage).
edit("ImageLoader.smali",
     'const-string v1, "mounted"\n\n    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z\n\n    move-result v0\n\n    if-eqz v0, :cond_1',
     'goto :cond_1')

# 3. LoadDevices: hold a Wi-Fi multicast lock while searching for players.
edit("LoadDevices.smali",
     "invoke-virtual {p0, v6}, Lolive/app/LoadDevices;->setContentView(I)V\n",
     "invoke-virtual {p0, v6}, Lolive/app/LoadDevices;->setContentView(I)V\n\n"
     "    const/4 v6, 0x1\n\n"
     "    invoke-static {p0, v6}, Lolive/app/Compat;->multicast(Landroid/content/Context;Z)V\n\n"
     # Scan known subnets with unicast probes (SSDP multicast doesn't cross routers) and offer "Scan a network".
     "    invoke-static {p0}, Lolive/app/OliveNet;->setupPicker(Landroid/app/Activity;)V\n")
edit("LoadDevices.smali",
     ".method public cancelSearch()V\n    .locals 2\n\n    .prologue\n",
     ".method public cancelSearch()V\n    .locals 2\n\n    .prologue\n"
     "    const/4 v0, 0x0\n\n"
     "    invoke-static {p0, v0}, Lolive/app/Compat;->multicast(Landroid/content/Context;Z)V\n\n")
with open(root / "LoadDevices.smali", "a") as f:
    f.write("""
.method protected onDestroy()V
    .locals 0

    invoke-virtual {p0}, Lolive/app/LoadDevices;->cancelSearch()V

    invoke-super {p0}, Landroid/app/Activity;->onDestroy()V

    return-void
.end method
""")

# 3b. LoadDevices: the original search disconnected and reconnected Wi-Fi first. That needs
# CHANGE_WIFI_STATE and is refused for modern apps, so drop it.
edit("LoadDevices.smali", "    invoke-virtual {v4}, Landroid/net/wifi/WifiManager;->disconnect()Z\n", "")
edit("LoadDevices.smali", "    invoke-virtual {v4}, Landroid/net/wifi/WifiManager;->reconnect()Z\n", "")
# 3c. Any runtime failure while searching falls back to "type in the IP address" instead of crashing.
edit("LoadDevices.smali", ".catch Ljava/lang/UnsupportedOperationException;", ".catch Ljava/lang/RuntimeException;", count=5)

# 4. LoadTabs: on-screen replacement for the hardware MENU key.
with open(root / "LoadTabs.smali", "a") as f:
    f.write("""
.method public onMenuButton(Landroid/view/View;)V
    .locals 0

    invoke-static {p0, p1}, Lolive/app/Compat;->showTabMenu(Landroid/app/TabActivity;Landroid/view/View;)V

    return-void
.end method
""")

# 6. Work on any network (mobile data, Tailscale...), not only Wi-Fi.
for name in ("ArtistTab.smali", "LoadDevices.smali"):
    f = root / name
    t = f.read_text()
    t, k = re.subn(r"invoke-virtual \{(v\d+)\}, Landroid/net/wifi/WifiInfo;->getIpAddress\(\)I",
                   r"invoke-static {\1}, Lolive/app/Compat;->ipAddress(Landroid/net/wifi/WifiInfo;)I", t)
    assert k == 1, (name, k)
    f.write_text(t)
# Device picker: skip multicast discovery when not on Wi-Fi and say why, instead of an error.
edit("LoadDevices.smali",
     ".method public searchUPnPDevices()V\n    .locals 11\n\n    .prologue\n",
     ".method public searchUPnPDevices()V\n    .locals 11\n\n    .prologue\n"
     "    invoke-static {p0}, Lolive/app/Compat;->skipDiscovery(Landroid/app/Activity;)Z\n\n"
     "    move-result v0\n\n"
     "    if-eqz v0, :olive_discover\n\n"
     "    return-void\n\n"
     "    :olive_discover\n")

# 7. App shell: bottom-navigation tabs, app bar title/search in step with the selected tab.
edit("LoadTabs.smali",
     "invoke-virtual {v6, v7, v8}, Landroid/widget/TabHost$TabSpec;->setIndicator(Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;",
     "invoke-static {p0, v6, v7, v8}, Lolive/app/Compat;->tabIndicator(Landroid/app/Activity;Landroid/widget/TabHost$TabSpec;Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;",
     count=4)
edit("LoadTabs.smali",
     "    invoke-virtual {v6, v10}, Landroid/widget/TabHost;->setCurrentTab(I)V\n",
     "    invoke-virtual {v6, v10}, Landroid/widget/TabHost;->setCurrentTab(I)V\n\n"
     "    invoke-static {p0}, Lolive/app/Compat;->setupShell(Landroid/app/TabActivity;)V\n")
with open(root / "LoadTabs.smali", "a") as f:
    f.write("""
.method public onSearchButton(Landroid/view/View;)V
    .locals 1

    const-string v0, "search"

    invoke-static {p0, p1, v0}, Lolive/app/Compat;->tabAction(Landroid/app/TabActivity;Landroid/view/View;Ljava/lang/String;)V

    return-void
.end method
""")

# 8. Artist rows: hide the second line when it repeats the first.
f = root / "LazyAdapter.smali"
t = f.read_text()
album_set = ("    iget-object v3, v0, Lolive/app/LazyAdapter$ViewHolder;->album:Landroid/widget/TextView;\n")
i = t.index(album_set)
j = t.index("invoke-virtual {v3, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V\n", i)
j = t.index("\n", j) + 1
t = t[:j] + "\n    invoke-static {v3}, Lolive/app/Compat;->dedupeSubtitle(Landroid/widget/TextView;)V\n" + t[j:]
f.write_text(t)

# 9. Material 3 player: animated play/pause swaps; theme colours instead of per-genre colours.
COMPAT = "Lolive/app/Compat;"
for f in sorted(root.glob("Playscreen*.smali")):
    t = f.read_text()
    t = re.sub(r"invoke-virtual(/range)? \{([^}]*)\}, Landroid/widget/ImageButton;->setImageResource\(I\)V",
               lambda m: f"invoke-static{m.group(1) or ''} {{{m.group(2)}}}, {COMPAT}->setPlayIcon(Landroid/widget/ImageButton;I)V", t)
    t = re.sub(r"invoke-virtual(/range)? \{([^}]*)\}, Landroid/widget/ImageButton;->setImageDrawable\(Landroid/graphics/drawable/Drawable;\)V",
               lambda m: f"invoke-static{m.group(1) or ''} {{{m.group(2)}}}, {COMPAT}->setPlayIcon(Landroid/widget/ImageButton;Landroid/graphics/drawable/Drawable;)V", t)
    f.write_text(t)
f = root / "Playscreen.smali"
t = f.read_text()
t, k1 = re.subn(r"invoke-virtual \{(v\d+), (v\d+)\}, Landroid/widget/TextView;->setTextColor\(I\)V",
                rf"invoke-static {{\1, \2}}, {COMPAT}->themeTextColor(Landroid/widget/TextView;I)V", t)
t, k2 = re.subn(r"invoke-virtual \{(v\d+), (v\d+)\}, Landroid/widget/ProgressBar;->set(?:Progress|Background)Drawable\(Landroid/graphics/drawable/Drawable;\)V",
                rf"invoke-static {{\1, \2}}, {COMPAT}->keepProgressStyle(Landroid/widget/ProgressBar;Landroid/graphics/drawable/Drawable;)V", t)
assert (k1, k2) == (6, 2), (k1, k2)
f.write_text(t)
n = sum(g.read_text().count("->setPlayIcon(") for g in root.glob("Playscreen*.smali"))
assert n == 7, n
print("player hooks:", n, "icon swaps,", k1, "genre colours,", k2, "progress styles")

# 5. Add compiled helper classes.
for f in helpers.glob("*.smali"):
    shutil.copy(f, root / f.name)
print("helpers:", [f.name for f in helpers.glob("*.smali")])
