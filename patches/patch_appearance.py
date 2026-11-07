"""Appearance settings: lets the user choose Olive green instead of wallpaper colours (and light/dark, in code).

Runs after patch_ui.py. The M3 colour roles become theme attributes so the palette can be swapped per screen:
- the palettes are concrete colours: m3dyn_* (wallpaper colours on Android 12+, the Olive scheme before) and
  m3olive_* (the Olive scheme, light and dark);
- @color/m3_<role> becomes a colour state list reading ?attr/olive_m3_<role>, so every existing layout,
  drawable and style keeps its reference; the themes set the attributes to m3dyn_*, and the style
  OliveColors.Olive (applied by OliveTheme when chosen) points them at m3olive_*;
- things drawn outside the app's theme get concrete colours: the home-screen widget (one set per palette,
  drawn by the launcher), the notification icon and the splash screen; and colour backgrounds become drawables,
  which Android 7-9 need for themed colours.

Usage: patch_appearance.py <patched project dir> <m3 assets dir>
"""
import glob
import json
import os
import pathlib
import re
import sys

root = pathlib.Path(sys.argv[1])
assets = pathlib.Path(sys.argv[2]).resolve()
os.chdir(root)
HDR = '<?xml version="1.0" encoding="utf-8"?>\n'
NS = 'xmlns:android="http://schemas.android.com/apk/res/android"'
ROLES = ['primary', 'on_primary', 'primary_container', 'on_primary_container', 'secondary_container',
         'on_secondary_container', 'surface', 'surface_container_low', 'surface_container', 'surface_container_high',
         'surface_container_highest', 'on_surface', 'on_surface_variant', 'outline', 'outline_variant']
ROLE = '(' + '|'.join(sorted(ROLES, key=len, reverse=True)) + ')'


def write(path, content):
    p = pathlib.Path(path)
    p.parent.mkdir(parents=True, exist_ok=True)
    p.write_text(content)


def sub_file(path, pattern, repl, flags=0):
    p = pathlib.Path(path)
    s, n = re.subn(pattern, repl, p.read_text(), flags=flags)
    p.write_text(s)
    return n


# 1. Palettes: the existing role colours become the "dyn" palette; add the Olive palette.
for f in glob.glob('res/values*/colors_m3.xml'):
    sub_file(f, r'name="m3_', 'name="m3dyn_')
scheme = json.loads((assets / 'scheme.json').read_text())


def camel(role):
    head, *rest = role.split('_')
    return head + ''.join(w.title() for w in rest)


for mode, folder in (('light', 'values'), ('dark', 'values-night')):
    body = ''.join(f'    <color name="m3olive_{r}">{scheme[mode][camel(r)]}</color>\n' for r in ROLES)
    write(f'res/{folder}/colors_m3olive.xml', f'{HDR}<resources>\n{body}</resources>\n')

# 2. Roles as theme attributes, read through same-named colour state lists.
write('res/values/attrs_m3.xml', HDR + '<resources>\n'
      + ''.join(f'    <attr name="olive_m3_{r}" format="color|reference" />\n' for r in ROLES) + '</resources>\n')
for r in ROLES:
    write(f'res/color/m3_{r}.xml', f'{HDR}<selector {NS}>\n    <item android:color="?attr/olive_m3_{r}"/>\n</selector>\n')
# Colour state lists that referenced roles read the attributes directly.
for f in glob.glob('res/color/*.xml'):
    if not re.match(r'.*/m3_' + ROLE + r'\.xml$', f):
        sub_file(f, r'@color/m3_' + ROLE + r'\b', r'?attr/olive_m3_\1')


def attr_items(palette, indent='        '):
    return ''.join(f'{indent}<item name="olive_m3_{r}">@color/m3{palette}_{r}</item>\n' for r in ROLES)


# 3. Themes: wallpaper palette by default (activity and dialog-activity themes; alert dialogs inherit the
#    attributes from the screen that opens them), the Olive overlay, and drawable window backgrounds.
styles = pathlib.Path('res/values/styles.xml')
s = styles.read_text()
for base in ('<style name="Olive.Base" parent="Olive.Platform">\n', '<style name="Olive.DialogBase" parent="Olive.Platform.Dialog">\n'):
    assert s.count(base) == 1, base
    s = s.replace(base, base + attr_items('dyn'))
s = s.replace('</resources>', '    <style name="OliveColors.Olive">\n' + attr_items('olive') + '    </style>\n</resources>')
s = s.replace('<style name="OliveColors.Olive">', '<style name="OliveColors"/>\n    <style name="OliveColors.Olive">', 1)
s = s.replace('<item name="android:windowBackground">@color/m3_surface</item>',
              '<item name="android:windowBackground">@drawable/m3_fill_surface</item>')
styles.write_text(s)
# The splash screen is drawn by the system before the app's theme is applied.
for f in glob.glob('res/values*/styles_v31.xml'):
    sub_file(f, r'windowSplashScreenBackground">@color/m3_surface', 'windowSplashScreenBackground">@color/m3dyn_surface')

# 4. Colour backgrounds as drawables (a themed colour state list can only be a background on Android 10+).
fills = set()


def to_fill(m):
    fills.add(m.group(1))
    return f'android:background="@drawable/m3_fill_{m.group(1)}"'


for f in glob.glob('res/layout*/*.xml'):
    p = pathlib.Path(f)
    p.write_text(re.sub(r'android:background="@color/m3_' + ROLE + r'"', to_fill, p.read_text()))
for r in fills | {'surface'}:
    write(f'res/drawable/m3_fill_{r}.xml', f'{HDR}<shape {NS}>\n    <solid android:color="@color/m3_{r}"/>\n</shape>\n')

# 5. Home-screen widget: drawn by the launcher, outside the app's theme, so one concrete set per palette.
WIDGET_DRAWABLES = ['w_bg', 'w_play', 'w_pause', 'w_prev', 'w_next', 'w_art_placeholder', 'g_play', 'g_pause', 'g_album_placeholder']
for palette in ('dyn', 'olive'):
    write(f'res/color/w_ripple_{palette}.xml',
          f'{HDR}<selector {NS}>\n    <item android:color="@color/m3{palette}_on_surface" android:alpha="0.12"/>\n</selector>\n')
    write(f'res/drawable/w_btn_{palette}.xml', f'''{HDR}<ripple {NS} android:color="@color/w_ripple_{palette}">
    <item android:id="@android:id/mask"><shape><solid android:color="#ffffffff"/><corners android:radius="100dp"/></shape></item>
</ripple>
''')

    def concrete(text, palette=palette):
        text = re.sub(r'@color/m3_' + ROLE + r'\b', rf'@color/m3{palette}_\1', text)
        for name in WIDGET_DRAWABLES:
            text = re.sub(r'@drawable/' + name + r'\b', f'@drawable/{"w_" if name.startswith("g_") else ""}{name}_{palette}', text)
        return text.replace('@drawable/m3_btn_text', f'@drawable/w_btn_{palette}')

    for name in WIDGET_DRAWABLES:
        target = f'w_{name}_{palette}' if name.startswith('g_') else f'{name}_{palette}'
        write(f'res/drawable/{target}.xml', concrete(pathlib.Path(f'res/drawable/{name}.xml').read_text()))
    for layout in ('olive_widget', 'olive_widget_large'):
        write(f'res/layout/{layout}_{palette}.xml', concrete(pathlib.Path(f'res/layout/{layout}.xml').read_text()))
for layout in ('olive_widget', 'olive_widget_large'):
    os.remove(f'res/layout/{layout}.xml')
for name in ('w_bg', 'w_play', 'w_pause', 'w_prev', 'w_next', 'w_art_placeholder', 'w_btn'):
    if os.path.exists(f'res/drawable/{name}.xml'):
        os.remove(f'res/drawable/{name}.xml')
sub_file('res/xml/olive_widget_info.xml', r'@layout/olive_widget"', '@layout/olive_widget_dyn"')

# 6. Notification icon: the system only uses its shape, but it must not depend on the app's theme.
glyph = pathlib.Path('res/drawable/ic_m3_now_playing.xml').read_text()
write('res/drawable/ic_notif.xml', re.sub(r'android:fillColor="[^"]+"', 'android:fillColor="#ffffffff"', glyph))

# 7. Appearance section in Settings (shown on Android 12+, where both options apply).
settings = pathlib.Path('res/layout/settings.xml')
s = settings.read_text()
about = re.search(r'\n(\s*)<TextView[^\n]*android:text="About" />', s)
assert about, 'About header not found'
indent = about.group(1)
header = about.group(0).strip().replace('android:text="About"', 'android:text="Appearance"').replace(
    '<TextView ', '<TextView android:id="@+id/appearanceHeader" ')


def row(id_, title):
    return (f'{indent}<LinearLayout android:id="@+id/{id_}Row" android:orientation="vertical" android:gravity="center_vertical" '
            f'android:clickable="true" android:background="?android:attr/selectableItemBackground" android:minHeight="72dip" '
            f'android:paddingLeft="16dip" android:paddingRight="16dip" android:layout_width="fill_parent" android:layout_height="wrap_content">\n'
            f'{indent}    <TextView style="@style/Text.BodyLarge" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="{title}" />\n'
            f'{indent}    <TextView style="@style/Text.BodyMedium" android:id="@+id/{id_}Value" android:layout_width="wrap_content" android:layout_height="wrap_content" />\n'
            f'{indent}</LinearLayout>')


section = f'\n{indent}{header}\n{row("appearanceTheme", "Theme")}\n{row("appearanceColours", "Colours")}'
s = s[:about.start()] + section + s[about.start():]
settings.write_text(s)
ids = pathlib.Path('res/values/ids.xml')
ids.write_text(ids.read_text().replace('</resources>', '    <item type="id" name="olive_theme_tag" />\n</resources>'))

# 8. Themed vector colours need Android 7.
yml = pathlib.Path('apktool.yml')
yml.write_text(re.sub(r'  minSdkVersion: \d+\n', '  minSdkVersion: 24\n', yml.read_text()))

left = [f for f in glob.glob('res/**/*.xml', recursive=True)
        if re.search(r'android:background="@color/m3_', pathlib.Path(f).read_text())]
assert not left, left
print(f'appearance: {len(ROLES)} roles as theme attributes, {len(fills)} background fills, widget in 2 palettes')
