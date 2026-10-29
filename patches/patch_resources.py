"""Resource, manifest and icon changes applied to the apktool-decoded original app."""
import os, pathlib, sys
from PIL import Image

root = pathlib.Path(sys.argv[1])
os.chdir(root)

# Device picker: px -> density independent units.
p = pathlib.Path('res/layout/main.xml'); s = p.read_text()
s = s.replace('android:textSize="27.0px"', 'android:textSize="18.0sp"').replace('android:layout_marginTop="6.0px"', 'android:layout_marginTop="4.0dip"')
p.write_text(s)

# Player bar: on-screen menu button replacing the hardware MENU key.
p = pathlib.Path('res/layout/loadtabs.xml'); s = p.read_text()
old = '''            <ImageView android:layout_width="wrap_content" android:layout_height="wrap_content" android:src="@drawable/player_img" />
        </LinearLayout>
    </LinearLayout>'''
new = '''            <ImageView android:layout_width="wrap_content" android:layout_height="wrap_content" android:src="@drawable/player_img" />
        </LinearLayout>
        <TextView android:textSize="26.0sp" android:textColor="#ffffffff" android:gravity="center" android:layout_gravity="center_vertical" android:id="@id/menuButton" android:background="?android:attr/selectableItemBackgroundBorderless" android:clickable="true" android:paddingLeft="14.0dip" android:paddingRight="14.0dip" android:layout_width="wrap_content" android:layout_height="fill_parent" android:minHeight="48.0dip" android:text="⋮" android:contentDescription="Options" android:onClick="onMenuButton" android:layout_weight="0.0" />
    </LinearLayout>'''
assert s.count(old) == 1
p.write_text(s.replace(old, new))

p = pathlib.Path('res/values/ids.xml'); s = p.read_text()
p.write_text(s.replace('</resources>', '    <item type="id" name="menuButton" />\n</resources>'))

p = pathlib.Path('res/values/colors.xml'); s = p.read_text()
p.write_text(s.replace('</resources>', '    <color name="olive_green">#ffacc937</color>\n    <color name="icon_background">#ff221e1f</color>\n</resources>'))

pathlib.Path('res/values/styles.xml').write_text('''<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="OliveTheme" parent="@android:style/Theme.Material.NoActionBar">
        <item name="android:colorPrimary">#ff404040</item>
        <item name="android:colorPrimaryDark">#ff1a1a1a</item>
        <item name="android:colorAccent">@color/olive_green</item>
        <item name="android:navigationBarColor">#ff000000</item>
        <item name="android:windowBackground">@android:color/black</item>
    </style>
    <style name="OliveTheme.Dialog" parent="@android:style/Theme.Material.Dialog">
        <item name="android:colorAccent">@color/olive_green</item>
    </style>
</resources>
''')

# Adaptive launcher icon built from the original artwork.
src = Image.open('res/drawable-hdpi-v4/icon.png').convert('RGBA')
inner = src.crop((30, 140, 720, 520))
px = inner.load()
for y in range(inner.height):
    for x in range(inner.width):
        r, g, b, a = px[x, y]
        t = max(0, min(255, (g - 40) * 255 // 140))
        px[x, y] = (r, g, b, t) if t > 0 else (0, 0, 0, 0)
inner = inner.crop(inner.getbbox())
for name, size in [('mdpi', 108), ('hdpi', 162), ('xhdpi', 216), ('xxhdpi', 324), ('xxxhdpi', 432)]:
    canvas = Image.new('RGBA', (size, size), (0, 0, 0, 0))
    w = int(size * 0.56); h = int(inner.height * w / inner.width)
    logo = inner.resize((w, h), Image.LANCZOS)
    canvas.paste(logo, ((size - w) // 2, (size - h) // 2), logo)
    os.makedirs('res/drawable-%s-v4' % name, exist_ok=True)
    canvas.save('res/drawable-%s-v4/ic_launcher_foreground.png' % name)
os.makedirs('res/drawable-anydpi-v26', exist_ok=True)
pathlib.Path('res/drawable-anydpi-v26/icon.xml').write_text('''<?xml version="1.0" encoding="utf-8"?>
<adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android">
    <background android:drawable="@color/icon_background" />
    <foreground android:drawable="@drawable/ic_launcher_foreground" />
    <monochrome android:drawable="@drawable/ic_launcher_foreground" />
</adaptive-icon>
''')

activities = [
    ('LoadDevices', ' android:theme="@style/OliveTheme.Dialog"'),
    ('ArtistTab', ''), ('AlbumTab', ''), ('TrackTab', ''), ('MoreTab', ''), ('TrackListTab', ''),
    ('Playscreen', ''), ('ShowPlaylists', ' android:windowSoftInputMode="stateHidden"'), ('SearchListTab', ''),
    ('PlaylistTab', ''), ('SourcesTab', ''), ('IRadioTab', ''), ('AddURLTab', ''), ('ZonesTab', ''), ('SettingsTab', ''),
]
cfg = 'android:configChanges="keyboardHidden|orientation|screenSize" android:screenOrientation="portrait"'
acts = '\n'.join(f'        <activity android:name=".{n}" {cfg}{extra} android:exported="false"/>' for n, extra in activities)
pathlib.Path('AndroidManifest.xml').write_text(f'''<?xml version="1.0" encoding="utf-8" standalone="no"?><manifest xmlns:android="http://schemas.android.com/apk/res/android" package="olive.app">
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE"/>
    <uses-feature android:name="android.hardware.wifi" android:required="false"/>
    <queries>
        <intent>
            <action android:name="android.speech.action.RECOGNIZE_SPEECH"/>
        </intent>
    </queries>
    <application android:name=".OliveApplication" android:allowBackup="true" android:description="@string/app_desc" android:icon="@drawable/icon" android:label="@string/app_name" android:theme="@style/OliveTheme" android:usesCleartextTraffic="true">
        <uses-library android:name="org.apache.http.legacy" android:required="false"/>
        <activity android:name=".LoadTabs" {cfg} android:launchMode="singleTask" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
        <activity android:name=".SearchEntry" android:label="Search" android:theme="@style/OliveTheme.Dialog" android:windowSoftInputMode="stateVisible" android:exported="false"/>
{acts}
    </application>
</manifest>
''')

p = pathlib.Path('apktool.yml'); s = p.read_text()
s = s.replace("  minSdkVersion: 6\n", "  minSdkVersion: 21\n  targetSdkVersion: 34\n")
s = s.replace("  versionCode: 1\n  versionName: 1.0\n", "  versionCode: 6\n  versionName: 1.0-modern.5\n")
p.write_text(s)
print("resources patched")
