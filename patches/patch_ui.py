"""Material 3 (Android 16 style) UI for the Olive app, built from framework widgets only.

- Colour: M3 colour roles as resources. Android 14+ maps them to the system's dynamic
  (wallpaper) roles, Android 12-13 to the dynamic tonal palettes, older versions to a
  static M3 scheme generated from the Olive green. Light and dark follow the system.
- Type: Google Sans Flex (OFL) on the M3 type scale.
- Shape/motion: M3 shapes, emphasized easing, tab fade-through, list item entrance,
  player slide-up, press feedback.

Every replaced layout keeps every view id of the original with the same view class,
because the original code finds views by id and casts them; the check at the bottom
enforces that against the untouched decoded app.

Usage: patch_ui.py <patched project dir> <original decoded dir> <m3 assets dir>
"""
import glob, json, os, pathlib, re, shutil, sys
import xml.etree.ElementTree as ET

root = pathlib.Path(sys.argv[1])
original = pathlib.Path(sys.argv[2]).resolve()
assets = pathlib.Path(sys.argv[3]).resolve()
os.chdir(root)
A = '{http://schemas.android.com/apk/res/android}'
HDR = '<?xml version="1.0" encoding="utf-8"?>\n'
NS = 'xmlns:android="http://schemas.android.com/apk/res/android"'


def write(path, content):
    p = pathlib.Path(path)
    p.parent.mkdir(parents=True, exist_ok=True)
    p.write_text(content)


# --- Colour roles ---------------------------------------------------------------------------
ROLES = ['primary', 'on_primary', 'primary_container', 'on_primary_container', 'secondary_container',
         'on_secondary_container', 'surface', 'surface_container_low', 'surface_container', 'surface_container_high',
         'surface_container_highest', 'on_surface', 'on_surface_variant', 'outline', 'outline_variant']
scheme = json.loads((assets / 'scheme.json').read_text())


def camel(role):
    head, *rest = role.split('_')
    return head + ''.join(w.title() for w in rest)


def colors_xml(mapping):
    body = ''.join(f'    <color name="m3_{r}">{mapping[r]}</color>\n' for r in ROLES)
    return f'{HDR}<resources>\n{body}</resources>\n'


# Static fallback (no dynamic colour): M3 tonal-spot scheme seeded from the Olive green.
write('res/values/colors_m3.xml', colors_xml({r: scheme['light'][camel(r)] for r in ROLES}))
write('res/values-night/colors_m3.xml', colors_xml({r: scheme['dark'][camel(r)] for r in ROLES}))
# Android 14+: the system's own dynamic colour roles.
for mode, q in (('light', 'values-v34'), ('dark', 'values-night-v34')):
    write(f'res/{q}/colors_m3.xml', colors_xml({r: f'@android:color/system_{r}_{mode}' for r in ROLES}))
# Android 12-13: dynamic tonal palettes (closest tones to each role).
PAL_LIGHT = {'primary': 'accent1_600', 'on_primary': 'accent1_0', 'primary_container': 'accent1_100',
             'on_primary_container': 'accent1_900', 'secondary_container': 'accent2_100',
             'on_secondary_container': 'accent2_900', 'surface': 'neutral1_10', 'surface_container_low': 'neutral1_10',
             'surface_container': 'neutral1_50', 'surface_container_high': 'neutral1_50',
             'surface_container_highest': 'neutral1_100', 'on_surface': 'neutral1_900',
             'on_surface_variant': 'neutral2_700', 'outline': 'neutral2_500', 'outline_variant': 'neutral2_200'}
PAL_DARK = {'primary': 'accent1_200', 'on_primary': 'accent1_800', 'primary_container': 'accent1_700',
            'on_primary_container': 'accent1_100', 'secondary_container': 'accent2_700',
            'on_secondary_container': 'accent2_100', 'surface': 'neutral1_900', 'surface_container_low': 'neutral1_900',
            'surface_container': 'neutral1_900', 'surface_container_high': 'neutral1_800',
            'surface_container_highest': 'neutral1_800', 'on_surface': 'neutral1_100',
            'on_surface_variant': 'neutral2_200', 'outline': 'neutral2_400', 'outline_variant': 'neutral2_700'}
write('res/values-v31/colors_m3.xml', colors_xml({r: f'@android:color/system_{v}' for r, v in PAL_LIGHT.items()}))
write('res/values-night-v31/colors_m3.xml', colors_xml({r: f'@android:color/system_{v}' for r, v in PAL_DARK.items()}))
write('res/values/bools_m3.xml', f'{HDR}<resources>\n    <bool name="m3_light_bars">true</bool>\n</resources>\n')
write('res/values-night/bools_m3.xml', f'{HDR}<resources>\n    <bool name="m3_light_bars">false</bool>\n</resources>\n')


def csl(name, items):
    body = ''.join(f'    <item{" " + st if st else ""} android:color="{c}"{" android:alpha=" + chr(34) + a + chr(34) if a else ""}/>\n'
                   for st, c, a in items)
    write(f'res/color/{name}.xml', f'{HDR}<selector {NS}>\n{body}</selector>\n')


csl('m3_ripple', [('', '@color/m3_on_surface', '0.12')])
csl('m3_ripple_primary', [('', '@color/m3_on_primary', '0.16')])
csl('m3_nav_icon', [('android:state_selected="true"', '@color/m3_on_secondary_container', ''), ('', '@color/m3_on_surface_variant', '')])
csl('m3_nav_label', [('android:state_selected="true"', '@color/m3_on_surface', ''), ('', '@color/m3_on_surface_variant', '')])
csl('m3_on_surface_disabled', [('android:state_enabled="false"', '@color/m3_on_surface', '0.38'), ('', '@color/m3_on_surface', '')])

# --- Fonts ------------------------------------------------------------------------------------
for w, n in ((400, 'regular'), (500, 'medium'), (600, 'semibold')):
    os.makedirs('res/font', exist_ok=True)
    shutil.copy(assets / 'fonts' / f'gsf_{w}.ttf', f'res/font/gsf_{n}.ttf')
write('res/font/google_sans_flex.xml', f'''{HDR}<font-family {NS}>
    <font android:fontStyle="normal" android:fontWeight="400" android:font="@font/gsf_regular"/>
    <font android:fontStyle="normal" android:fontWeight="500" android:font="@font/gsf_medium"/>
    <font android:fontStyle="normal" android:fontWeight="700" android:font="@font/gsf_semibold"/>
</font-family>
''')

# --- Icons: Material Symbols Rounded ----------------------------------------------------------


def sym(name, color='@color/m3_on_surface_variant', fill=False, size=24):
    svg = (assets / 'icons' / f'{name}{"_fill1" if fill else ""}_24px.svg').read_text()
    paths = ''.join(f'        <path android:fillColor="{color}" android:pathData="{d}"/>\n'
                    for d in re.findall(r' d="([^"]+)"', svg))
    return (f'{HDR}<vector {NS} android:width="{size}dp" android:height="{size}dp" '
            f'android:viewportWidth="960" android:viewportHeight="960">\n'
            f'    <group android:translateY="960">\n{paths}    </group>\n</vector>\n')


def shape(color, radius=None, oval=False, w=None, h=None):
    s = ' android:shape="oval"' if oval else ''
    corners = f'\n    <corners android:radius="{radius}dp"/>' if radius is not None else ''
    size = f'\n    <size android:width="{w}dp" android:height="{h}dp"/>' if w else ''
    return f'{HDR}<shape {NS}{s}>\n    <solid android:color="{color}"/>{corners}{size}\n</shape>\n'


def badge(bg, glyph, w, h, radius, inset_x, inset_y, oval=False):
    """A filled container with a glyph centred inside (layer-list, so code can swap it as one image)."""
    s = ' android:shape="oval"' if oval else ''
    corners = '' if oval else f'<corners android:radius="{radius}dp"/>'
    return f'''{HDR}<layer-list {NS}>
    <item><shape{s}><solid android:color="{bg}"/>{corners}<size android:width="{w}dp" android:height="{h}dp"/></shape></item>
    <item android:drawable="@drawable/{glyph}" android:left="{inset_x}dp" android:right="{inset_x}dp" android:top="{inset_y}dp" android:bottom="{inset_y}dp"/>
</layer-list>
'''


def replace_drawable(name, content):
    for f in glob.glob(f'res/drawable*/{name}.*'):
        os.remove(f)
    write(f'res/drawable/{name}.xml', content)


OSV = '@color/m3_on_surface_variant'
OS = '@color/m3_on_surface'
glyphs = {
    'g_play': sym('play_arrow', '@color/m3_on_primary', True), 'g_pause': sym('pause', '@color/m3_on_primary', True),
    'g_repeat_on': sym('repeat', '@color/m3_on_secondary_container'), 'g_repeat_one_on': sym('repeat_one', '@color/m3_on_secondary_container'),
    'g_shuffle_on': sym('shuffle', '@color/m3_on_secondary_container'),
    'g_repeat_off': sym('repeat', OSV), 'g_shuffle_off': sym('shuffle', OSV),
    'ic_m3_collapse': sym('keyboard_arrow_down', OS), 'ic_m3_search': sym('search', OSV), 'ic_m3_more': sym('more_vert', OSV),
    'ic_m3_now_playing': sym('graphic_eq', '@color/m3_on_primary_container'),
    'ic_m3_skip_prev': sym('skip_previous', '@color/m3_on_secondary_container', True, 28),
    'ic_m3_skip_next': sym('skip_next', '@color/m3_on_secondary_container', True, 28),
    'ic_m3_music_note': sym('music_note', OSV), 'g_album_placeholder': sym('album', OSV), 'ic_m3_check': sym('check', OSV),
}
for tab, icon in (('artists', 'artist'), ('albums', 'album'), ('tracks', 'music_note'), ('more', 'more_horiz')):
    # Nav icons are white and tinted by the tab colour state list; selected tabs use the filled glyph.
    glyphs[f'ic_nav_{tab}_on'] = sym(icon, '#ffffffff', True)
    glyphs[f'ic_nav_{tab}_off'] = sym(icon, '#ffffffff', False)
    glyphs[f'ic_nav_{tab}'] = f'''{HDR}<selector {NS}>
    <item android:state_selected="true" android:drawable="@drawable/ic_nav_{tab}_on"/>
    <item android:drawable="@drawable/ic_nav_{tab}_off"/>
</selector>
'''
for name, content in glyphs.items():
    write(f'res/drawable/{name}.xml', content)

# Drawables the original code and layouts reference by name, re-drawn as M3 under the same names.
replacements = {
    'back': sym('arrow_back', OS), 'home2': sym('home', OSV), 'add2playlist': sym('playlist_add', OSV),
    'repeat_off': badge('#00000000', 'g_repeat_off', 40, 40, 0, 8, 8, oval=True),
    'repeat_on': badge('@color/m3_secondary_container', 'g_repeat_on', 40, 40, 0, 8, 8, oval=True),
    'repeat_one': badge('@color/m3_secondary_container', 'g_repeat_one_on', 40, 40, 0, 8, 8, oval=True),
    'shuffle_off': badge('#00000000', 'g_shuffle_off', 40, 40, 0, 8, 8, oval=True),
    'shuffle_on': badge('@color/m3_secondary_container', 'g_shuffle_on', 40, 40, 0, 8, 8, oval=True),
    'volume_mute': sym('volume_off', OSV), 'volume_0': sym('volume_mute', OSV), 'volume_1': sym('volume_down', OSV),
    'volume_2': sym('volume_down', OSV), 'volume_3': sym('volume_up', OSV), 'volume_4': sym('volume_up', OSV),
    'volumedown2': sym('volume_down', OSV), 'volumeup2': sym('volume_up', OSV),
    'left_skip': sym('skip_previous', '@color/m3_on_secondary_container', True, 28),
    'right_skip': sym('skip_next', '@color/m3_on_secondary_container', True, 28),
    'star_set': sym('star', '@color/m3_primary', True, 32), 'star_unset': sym('star', '@color/m3_outline', False, 32),
    'micro': sym('mic', OSV), 'save': sym('delete', OSV), 'cancel': sym('close', OSV), 'player_img': sym('keyboard_arrow_up', OSV),
    'backgroundtitle2': shape('@color/m3_surface'),
    # Missing-artwork placeholder (lists and player): tonal tile with an album glyph, scales to any size.
    'artworknotfound': f'''{HDR}<layer-list {NS}>
    <item><shape><solid android:color="@color/m3_surface_container_highest"/><size android:width="96dp" android:height="96dp"/></shape></item>
    <item><inset android:inset="30%" android:drawable="@drawable/g_album_placeholder"/></item>
</layer-list>
''',
    # Play/pause show the action: 'playpause' is shown while playing, 'pause' while paused, 'stop' while stopped.
    # Expressive shape: a squarer container while playing, a fully round one while paused.
    'playpause': badge('@color/m3_primary', 'g_pause', 96, 72, 22, 30, 18),
    'pause': badge('@color/m3_primary', 'g_play', 96, 72, 36, 30, 18),
    'stop': badge('@color/m3_primary', 'g_play', 96, 72, 36, 30, 18),
}
for name, content in replacements.items():
    replace_drawable(name, content)


def ripple(content_xml, color='@color/m3_ripple', mask=None):
    m = f'\n    <item android:id="@android:id/mask">{mask}</item>' if mask else ''
    c = f'\n    <item>{content_xml}</item>' if content_xml else ''
    return f'{HDR}<ripple {NS} android:color="{color}">{c}{m}\n</ripple>\n'


def inline_shape(color, radius):
    return f'<shape><solid android:color="{color}"/><corners android:radius="{radius}dp"/></shape>'


components = {
    'm3_dialog_bg': f'''{HDR}<inset {NS} android:inset="16dp">
    <shape><solid android:color="@color/m3_surface_container_high"/><corners android:radius="28dp"/></shape>
</inset>
''',
    'm3_popup_bg': f'''{HDR}<shape {NS}><solid android:color="@color/m3_surface_container"/><corners android:radius="16dp"/></shape>\n''',
    'm3_btn_filled': ripple(inline_shape('@color/m3_primary', 100), '@color/m3_ripple_primary'),
    'm3_btn_tonal': ripple(inline_shape('@color/m3_secondary_container', 100)),
    'm3_btn_text': ripple(None, mask=inline_shape('#ffffffff', 100)),
    'm3_btn_toggle': f'''{HDR}<ripple {NS} android:color="@color/m3_ripple">
    <item><selector>
        <item android:state_checked="true">{inline_shape('@color/m3_primary', 100)}</item>
        <item>{inline_shape('@color/m3_secondary_container', 100)}</item>
    </selector></item>
</ripple>
''',
    'm3_card': ripple(inline_shape('@color/m3_surface_container_high', 16)),
    'm3_card_flat': shape('@color/m3_surface_container', 20),
    'm3_icon_tonal': ripple(inline_shape('@color/m3_secondary_container', 20)),
    'm3_artwork_bg': shape('@color/m3_surface_container_high', 28),
    'm3_thumb_bg': shape('@color/m3_surface_container_highest', 12),
    'm3_avatar_bg': shape('@color/m3_surface_container_highest', oval=True),
    'm3_leading_icon_bg': shape('@color/m3_secondary_container', oval=True),
    'm3_mini_art_bg': shape('@color/m3_primary_container', 12),
    'm3_nav_pill': f'''{HDR}<selector {NS} android:enterFadeDuration="150" android:exitFadeDuration="150">
    <item android:state_selected="true">{inline_shape('@color/m3_secondary_container', 16)}</item>
    <item>{inline_shape('#00000000', 16)}</item>
</selector>
''',
    'm3_field': f'''{HDR}<selector {NS}>
    <item android:state_focused="true"><layer-list>
        <item><shape><solid android:color="@color/m3_surface_container_highest"/><corners android:topLeftRadius="4dp" android:topRightRadius="4dp"/></shape></item>
        <item android:gravity="bottom" android:height="2dp"><shape><solid android:color="@color/m3_primary"/></shape></item>
    </layer-list></item>
    <item><layer-list>
        <item><shape><solid android:color="@color/m3_surface_container_highest"/><corners android:topLeftRadius="4dp" android:topRightRadius="4dp"/></shape></item>
        <item android:gravity="bottom" android:height="1dp"><shape><solid android:color="@color/m3_on_surface_variant"/></shape></item>
    </layer-list></item>
</selector>
''',
    # M3 Expressive slider: thick rounded track, split around a slim bar handle.
    'm3_slider_track': f'''{HDR}<layer-list {NS}>
    <item android:id="@android:id/background" android:gravity="center_vertical" android:height="12dp">
        <shape><solid android:color="@color/m3_secondary_container"/><corners android:radius="6dp"/></shape>
    </item>
    <item android:id="@android:id/progress" android:gravity="center_vertical" android:height="12dp">
        <scale android:scaleWidth="100%" android:scaleGravity="left"><shape><solid android:color="@color/m3_primary"/><corners android:radius="6dp"/></shape></scale>
    </item>
</layer-list>
''',
    'm3_slider_thumb': f'''{HDR}<layer-list {NS}>
    <item><shape><solid android:color="#00000000"/><size android:width="16dp" android:height="40dp"/></shape></item>
    <item android:left="6dp" android:right="6dp"><shape><solid android:color="@color/m3_primary"/><corners android:radius="2dp"/></shape></item>
</layer-list>
''',
    'm3_linear_progress': f'''{HDR}<layer-list {NS}>
    <item android:id="@android:id/background">
        <shape><solid android:color="@color/m3_secondary_container"/><corners android:radius="2dp"/></shape>
    </item>
    <item android:id="@android:id/progress">
        <scale android:scaleWidth="100%" android:scaleGravity="left"><shape><solid android:color="@color/m3_primary"/><corners android:radius="2dp"/></shape></scale>
    </item>
</layer-list>
''',
}
for name, content in components.items():
    write(f'res/drawable/{name}.xml', content)

# --- Motion -------------------------------------------------------------------------------
interps = {'m3_emphasized_decelerate': (0.05, 0.7, 0.1, 1.0), 'm3_emphasized_accelerate': (0.3, 0.0, 0.8, 0.15),
           'm3_standard': (0.2, 0.0, 0.0, 1.0)}
for name, (x1, y1, x2, y2) in interps.items():
    write(f'res/interpolator/{name}.xml', f'{HDR}<pathInterpolator {NS} android:controlX1="{x1}" android:controlY1="{y1}" '
                                          f'android:controlX2="{x2}" android:controlY2="{y2}"/>\n')
anims = {
    'm3_player_enter': '''<set xmlns:android="http://schemas.android.com/apk/res/android" android:shareInterpolator="false">
    <translate android:fromYDelta="100%" android:toYDelta="0" android:duration="450" android:interpolator="@interpolator/m3_emphasized_decelerate"/>
    <alpha android:fromAlpha="0.4" android:toAlpha="1" android:duration="250" android:interpolator="@interpolator/m3_standard"/>
</set>''',
    'm3_player_exit': '''<set xmlns:android="http://schemas.android.com/apk/res/android" android:shareInterpolator="false">
    <translate android:fromYDelta="0" android:toYDelta="100%" android:duration="250" android:interpolator="@interpolator/m3_emphasized_accelerate"/>
    <alpha android:fromAlpha="1" android:toAlpha="0.6" android:duration="250"/>
</set>''',
    'm3_hold': '<translate xmlns:android="http://schemas.android.com/apk/res/android" android:fromYDelta="0" android:toYDelta="0" android:duration="450"/>',
    'm3_list_item': '''<set xmlns:android="http://schemas.android.com/apk/res/android" android:interpolator="@interpolator/m3_emphasized_decelerate">
    <alpha android:fromAlpha="0" android:toAlpha="1" android:duration="300"/>
    <translate android:fromYDelta="24dp" android:toYDelta="0" android:duration="400"/>
</set>''',
    'm3_list_layout': '<layoutAnimation xmlns:android="http://schemas.android.com/apk/res/android" android:delay="0.08" android:animationOrder="normal" android:animation="@anim/m3_list_item"/>',
}
for name, content in anims.items():
    write(f'res/anim/{name}.xml', HDR + content + '\n')
# Press feedback: a quick squish with emphasized easing.
write('res/animator/m3_press.xml', f'''{HDR}<selector {NS}>
    <item android:state_pressed="true"><set>
        <objectAnimator android:propertyName="scaleX" android:valueTo="0.92" android:duration="100" android:interpolator="@interpolator/m3_standard"/>
        <objectAnimator android:propertyName="scaleY" android:valueTo="0.92" android:duration="100" android:interpolator="@interpolator/m3_standard"/>
    </set></item>
    <item><set>
        <objectAnimator android:propertyName="scaleX" android:valueTo="1" android:duration="350" android:interpolator="@android:interpolator/overshoot"/>
        <objectAnimator android:propertyName="scaleY" android:valueTo="1" android:duration="350" android:interpolator="@android:interpolator/overshoot"/>
    </set></item>
</selector>
''')

# --- Theme ------------------------------------------------------------------------------------
write('res/values/themes_platform.xml', f'''{HDR}<resources>
    <style name="Olive.Platform" parent="@android:style/Theme.Material.Light.NoActionBar"/>
    <style name="Olive.Platform.Dialog" parent="@android:style/Theme.Material.Light.Dialog"/>
    <style name="Olive.Platform.Alert" parent="@android:style/Theme.Material.Light.Dialog.Alert"/>
</resources>
''')
write('res/values-night/themes_platform.xml', f'''{HDR}<resources>
    <style name="Olive.Platform" parent="@android:style/Theme.Material.NoActionBar"/>
    <style name="Olive.Platform.Dialog" parent="@android:style/Theme.Material.Dialog"/>
    <style name="Olive.Platform.Alert" parent="@android:style/Theme.Material.Dialog.Alert"/>
</resources>
''')

COMMON = '''        <item name="android:colorPrimary">@color/m3_primary</item>
        <item name="android:colorAccent">@color/m3_primary</item>
        <item name="android:colorControlNormal">@color/m3_on_surface_variant</item>
        <item name="android:colorControlActivated">@color/m3_primary</item>
        <item name="android:colorControlHighlight">@color/m3_ripple</item>
        <item name="android:colorBackground">@color/m3_surface</item>
        <item name="android:textColorPrimary">@color/m3_on_surface</item>
        <item name="android:textColorSecondary">@color/m3_on_surface_variant</item>
        <item name="android:textColorTertiary">@color/m3_on_surface_variant</item>
        <item name="android:textColorHint">@color/m3_on_surface_variant</item>
        <item name="android:textColorLink">@color/m3_primary</item>
        <item name="android:fontFamily">@font/google_sans_flex</item>
        <item name="android:buttonStyle">@style/Olive.Button</item>
        <item name="android:borderlessButtonStyle">@style/Olive.Button.Text</item>
        <item name="android:buttonBarButtonStyle">@style/Olive.Button.Text</item>
        <item name="android:buttonStyleToggle">@style/Olive.Button.Toggle</item>
        <item name="android:editTextStyle">@style/Olive.EditText</item>
        <item name="android:seekBarStyle">@style/Olive.Slider</item>
        <item name="android:progressBarStyleHorizontal">@style/Olive.LinearProgress</item>
        <item name="android:listViewStyle">@style/Olive.ListView</item>
        <item name="android:textAppearanceListItem">@style/Text.BodyLarge</item>
        <item name="android:textAppearanceListItemSmall">@style/Text.BodyLarge</item>
        <item name="android:listPreferredItemHeight">56dp</item>
        <item name="android:listPreferredItemHeightSmall">56dp</item>
        <item name="android:listPreferredItemPaddingLeft">16dp</item>
        <item name="android:listPreferredItemPaddingRight">16dp</item>
        <item name="android:listPreferredItemPaddingStart">16dp</item>
        <item name="android:listPreferredItemPaddingEnd">16dp</item>
        <item name="android:popupMenuStyle">@style/Olive.PopupMenu</item>
        <item name="android:dropDownListViewStyle">@style/Olive.ListView.Popup</item>
        <item name="android:textAppearanceLargePopupMenu">@style/Text.BodyLarge</item>
        <item name="android:textAppearanceSmallPopupMenu">@style/Text.BodyLarge</item>
        <item name="android:windowTitleStyle">@style/Olive.DialogTitle</item>
        <item name="android:alertDialogTheme">@style/OliveTheme.Alert</item>
        <item name="android:dialogTheme">@style/OliveTheme.Alert</item>
'''
write('res/values/styles.xml', f'''{HDR}<resources>
    <style name="Olive.Base" parent="Olive.Platform">
{COMMON}        <item name="android:windowBackground">@color/m3_surface</item>
        <item name="android:statusBarColor">@color/m3_surface</item>
        <item name="android:navigationBarColor">@color/m3_surface</item>
        <item name="android:windowLightStatusBar">@bool/m3_light_bars</item>
    </style>
    <style name="OliveTheme" parent="Olive.Base"/>
    <style name="OliveTheme.Home">
        <item name="android:navigationBarColor">@color/m3_surface_container</item>
    </style>
    <style name="OliveTheme.Player">
        <item name="android:windowAnimationStyle">@style/Olive.Anim.Player</item>
    </style>

    <style name="Olive.DialogBase" parent="Olive.Platform.Dialog">
{COMMON}        <item name="android:windowBackground">@drawable/m3_dialog_bg</item>
        <item name="android:windowMinWidthMajor">80%</item>
        <item name="android:windowMinWidthMinor">90%</item>
        <item name="android:backgroundDimAmount">0.32</item>
    </style>
    <style name="OliveTheme.Dialog" parent="Olive.DialogBase"/>
    <style name="Olive.AlertBase" parent="Olive.Platform.Alert">
{COMMON}        <item name="android:windowBackground">@drawable/m3_dialog_bg</item>
        <item name="android:backgroundDimAmount">0.32</item>
    </style>
    <style name="OliveTheme.Alert" parent="Olive.AlertBase"/>

    <style name="Olive.Anim.Player" parent="@android:style/Animation.Activity">
        <item name="android:activityOpenEnterAnimation">@anim/m3_player_enter</item>
        <item name="android:activityOpenExitAnimation">@anim/m3_hold</item>
        <item name="android:activityCloseEnterAnimation">@anim/m3_hold</item>
        <item name="android:activityCloseExitAnimation">@anim/m3_player_exit</item>
    </style>

    <style name="Text"/>
    <style name="Text.HeadlineSmall"><item name="android:textSize">24sp</item><item name="android:textColor">@color/m3_on_surface</item><item name="android:fontFamily">@font/gsf_regular</item></style>
    <style name="Text.TitleLarge"><item name="android:textSize">22sp</item><item name="android:textColor">@color/m3_on_surface</item><item name="android:fontFamily">@font/gsf_regular</item></style>
    <style name="Text.TitleMedium"><item name="android:textSize">16sp</item><item name="android:textColor">@color/m3_on_surface</item><item name="android:fontFamily">@font/gsf_medium</item></style>
    <style name="Text.BodyLarge"><item name="android:textSize">16sp</item><item name="android:textColor">@color/m3_on_surface</item><item name="android:fontFamily">@font/gsf_regular</item></style>
    <style name="Text.BodyMedium"><item name="android:textSize">14sp</item><item name="android:textColor">@color/m3_on_surface_variant</item><item name="android:fontFamily">@font/gsf_regular</item></style>
    <style name="Text.LabelLarge"><item name="android:textSize">14sp</item><item name="android:textColor">@color/m3_on_surface_variant</item><item name="android:fontFamily">@font/gsf_medium</item></style>
    <style name="Text.LabelMedium"><item name="android:textSize">12sp</item><item name="android:textColor">@color/m3_on_surface_variant</item><item name="android:fontFamily">@font/gsf_medium</item></style>
    <style name="Text.LabelSmall"><item name="android:textSize">11sp</item><item name="android:textColor">@color/m3_on_surface_variant</item><item name="android:fontFamily">@font/gsf_medium</item><item name="android:letterSpacing">0.04</item></style>
    <style name="Olive.DialogTitle" parent="@android:style/TextAppearance.Material.DialogWindowTitle">
        <item name="android:textSize">24sp</item><item name="android:textColor">@color/m3_on_surface</item><item name="android:fontFamily">@font/gsf_regular</item>
    </style>

    <style name="Olive.Button" parent="@android:style/Widget.Material.Button">
        <item name="android:background">@drawable/m3_btn_filled</item>
        <item name="android:textColor">@color/m3_on_primary</item>
        <item name="android:textSize">14sp</item>
        <item name="android:fontFamily">@font/gsf_medium</item>
        <item name="android:textAllCaps">false</item>
        <item name="android:minHeight">40dp</item>
        <item name="android:minWidth">64dp</item>
        <item name="android:paddingLeft">24dp</item>
        <item name="android:paddingRight">24dp</item>
        <item name="android:stateListAnimator">@animator/m3_press</item>
        <item name="android:elevation">0dp</item>
    </style>
    <style name="Olive.Button.Text">
        <item name="android:background">@drawable/m3_btn_text</item>
        <item name="android:textColor">@color/m3_primary</item>
        <item name="android:paddingLeft">12dp</item>
        <item name="android:paddingRight">12dp</item>
    </style>
    <style name="Olive.Button.Toggle" parent="@android:style/Widget.Material.Button.Toggle">
        <item name="android:background">@drawable/m3_btn_toggle</item>
        <item name="android:textColor">@color/m3_on_secondary_container</item>
        <item name="android:textSize">14sp</item>
        <item name="android:fontFamily">@font/gsf_medium</item>
        <item name="android:textAllCaps">false</item>
        <item name="android:minHeight">40dp</item>
        <item name="android:paddingLeft">20dp</item>
        <item name="android:paddingRight">20dp</item>
        <item name="android:stateListAnimator">@animator/m3_press</item>
    </style>
    <style name="Olive.EditText" parent="@android:style/Widget.Material.EditText">
        <item name="android:background">@drawable/m3_field</item>
        <item name="android:paddingLeft">16dp</item>
        <item name="android:paddingRight">16dp</item>
        <item name="android:paddingTop">16dp</item>
        <item name="android:paddingBottom">16dp</item>
        <item name="android:textSize">16sp</item>
        <item name="android:textColor">@color/m3_on_surface</item>
        <item name="android:textColorHint">@color/m3_on_surface_variant</item>
    </style>
    <style name="Olive.Slider" parent="@android:style/Widget.Material.SeekBar">
        <item name="android:progressDrawable">@drawable/m3_slider_track</item>
        <item name="android:thumb">@drawable/m3_slider_thumb</item>
        <item name="android:splitTrack">true</item>
        <item name="android:minHeight">44dp</item>
        <item name="android:maxHeight">44dp</item>
        <item name="android:paddingLeft">12dp</item>
        <item name="android:paddingRight">12dp</item>
        <item name="android:background">@null</item>
    </style>
    <style name="Olive.LinearProgress" parent="@android:style/Widget.Material.ProgressBar.Horizontal">
        <item name="android:progressDrawable">@drawable/m3_linear_progress</item>
        <item name="android:minHeight">4dp</item>
        <item name="android:maxHeight">4dp</item>
    </style>
    <style name="Olive.ListView" parent="@android:style/Widget.Material.ListView">
        <item name="android:divider">@null</item>
        <item name="android:dividerHeight">0dp</item>
        <item name="android:layoutAnimation">@anim/m3_list_layout</item>
    </style>
    <style name="Olive.ListView.Popup" parent="@android:style/Widget.Material.ListView.DropDown">
        <item name="android:divider">@null</item>
    </style>
    <style name="Olive.PopupMenu" parent="@android:style/Widget.Material.PopupMenu">
        <item name="android:popupBackground">@drawable/m3_popup_bg</item>
        <item name="android:popupElevation">3dp</item>
    </style>
</resources>
''')
write('res/values-v27/styles_v27.xml', f'''{HDR}<resources>
    <style name="OliveTheme" parent="Olive.Base">
        <item name="android:windowLightNavigationBar">@bool/m3_light_bars</item>
    </style>
</resources>
''')
write('res/values-v31/styles_v31.xml', f'''{HDR}<resources>
    <style name="OliveTheme" parent="Olive.Base">
        <item name="android:windowLightNavigationBar">@bool/m3_light_bars</item>
        <item name="android:windowSplashScreenBackground">@color/m3_surface</item>
        <item name="android:windowSplashScreenAnimatedIcon">@drawable/ic_launcher_foreground</item>
    </style>
</resources>
''')

# --- Layouts ---------------------------------------------------------------------------------
ICON = ('android:layout_width="48dip" android:layout_height="48dip" android:padding="12dip" android:scaleType="fitCenter" '
        'android:background="?android:attr/selectableItemBackgroundBorderless"')


def top_bar(inner, id_attr=''):
    return f'''<LinearLayout{id_attr} android:orientation="horizontal" android:background="@color/m3_surface" android:gravity="center_vertical" android:paddingLeft="4dip" android:paddingRight="4dip" android:layout_width="fill_parent" android:layout_height="64dip">
        {inner}
    </LinearLayout>'''


def mini_player(wrapper_ids=False):
    """M3 floating now-playing card. The list screens' original bar had ids on its inner layouts and image."""
    l1 = ' android:id="@id/LinearLayout01"' if wrapper_ids else ''
    l2 = ' android:id="@id/LinearLayout02"' if wrapper_ids else ''
    img = ' android:id="@id/ImageView01"' if wrapper_ids else ''
    return f'''<LinearLayout android:orientation="horizontal" android:id="@id/playerbar" android:background="@drawable/m3_card" android:clickable="true" android:gravity="center_vertical" android:layout_marginLeft="12dip" android:layout_marginRight="12dip" android:layout_marginTop="4dip" android:layout_marginBottom="8dip" android:paddingLeft="8dip" android:layout_width="fill_parent" android:layout_height="64dip" android:elevation="1dip" android:stateListAnimator="@animator/m3_press">
            <ImageView android:background="@drawable/m3_mini_art_bg" android:padding="12dip" android:layout_width="48dip" android:layout_height="48dip" android:src="@drawable/ic_m3_now_playing" />
            <LinearLayout android:orientation="vertical"{l1} android:paddingLeft="12dip" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0">
                <TextView style="@style/Text.TitleMedium" android:id="@id/playerTitle" android:layout_width="fill_parent" android:layout_height="wrap_content" android:singleLine="true" android:ellipsize="end" android:hint="Nothing playing" />
                <TextView style="@style/Text.BodyMedium" android:id="@id/playerAlbum" android:layout_width="fill_parent" android:layout_height="wrap_content" android:singleLine="true" android:ellipsize="end" />
            </LinearLayout>
            <LinearLayout{l2} android:gravity="center" android:layout_width="56dip" android:layout_height="fill_parent">
                <ImageView{img} android:layout_width="24dip" android:layout_height="24dip" android:src="@drawable/player_img" />
            </LinearLayout>
        </LinearLayout>'''


layouts = {}

layouts['loadtabs'] = f'''{HDR}<LinearLayout android:orientation="vertical" android:background="@color/m3_surface" android:layout_width="fill_parent" android:layout_height="fill_parent" {NS}>
    {top_bar(f"""<TextView style="@style/Text.TitleLarge" android:id="@+id/screenTitle" android:paddingLeft="12dip" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0" android:singleLine="true" android:ellipsize="end" android:text="Olive" />
        <ImageView android:id="@+id/searchButton" {ICON} android:src="@drawable/ic_m3_search" android:contentDescription="Search" android:onClick="onSearchButton" />
        <ImageView android:id="@id/menuButton" {ICON} android:src="@drawable/ic_m3_more" android:contentDescription="More options" android:onClick="onMenuButton" />""", ' android:id="@+id/appBar"')}
    <TabHost android:id="@android:id/tabhost" android:layout_width="fill_parent" android:layout_height="0dip" android:layout_weight="1.0">
        <LinearLayout android:orientation="vertical" android:layout_width="fill_parent" android:layout_height="fill_parent">
            <FrameLayout android:id="@android:id/tabcontent" android:layout_width="fill_parent" android:layout_height="0dip" android:layout_weight="1.0" />
        {mini_player()}
            <TabWidget android:id="@android:id/tabs" android:background="@color/m3_surface_container" android:showDividers="none" android:tabStripEnabled="false" android:paddingTop="12dip" android:paddingBottom="16dip" android:layout_width="fill_parent" android:layout_height="80dip" />
        </LinearLayout>
    </TabHost>
</LinearLayout>
'''

layouts['olive_tab'] = f'''{HDR}<LinearLayout android:orientation="vertical" android:gravity="center_horizontal" android:layout_width="0dip" android:layout_height="fill_parent" android:layout_weight="1.0" {NS}>
    <FrameLayout android:id="@+id/tabPill" android:background="@drawable/m3_nav_pill" android:duplicateParentState="true" android:foreground="?android:attr/selectableItemBackground" android:layout_width="56dip" android:layout_height="32dip">
        <ImageView android:id="@+id/tabIcon" android:tint="@color/m3_nav_icon" android:duplicateParentState="true" android:layout_gravity="center" android:layout_width="24dip" android:layout_height="24dip" />
    </FrameLayout>
    <TextView android:id="@+id/tabLabel" style="@style/Text.LabelMedium" android:textColor="@color/m3_nav_label" android:duplicateParentState="true" android:paddingTop="4dip" android:singleLine="true" android:layout_width="wrap_content" android:layout_height="wrap_content" />
</LinearLayout>
'''


def original_id(name, tag, parent_id=None):
    t = ET.parse(original / 'res/layout' / f'{name}.xml')
    scope = t.getroot()
    if parent_id:
        scope = next(e for e in t.iter() if (e.get(A + 'id') or '').endswith('/' + parent_id))
    return next(e.get(A + 'id') for e in scope.iter(tag)).split('/')[1]


def list_screen(title_id, list_id):
    return f'''{HDR}<RelativeLayout android:background="@color/m3_surface" android:layout_width="fill_parent" android:layout_height="fill_parent" {NS}>
    <RelativeLayout android:id="@id/menuTop" android:background="@color/m3_surface" android:layout_width="fill_parent" android:layout_height="64dip" android:layout_alignParentTop="true">
        <LinearLayout android:orientation="horizontal" android:id="@id/topmenu" android:gravity="center_vertical" android:paddingLeft="4dip" android:paddingRight="4dip" android:layout_width="fill_parent" android:layout_height="fill_parent">
            <LinearLayout android:id="@id/back" android:clickable="true" android:layout_width="wrap_content" android:layout_height="wrap_content">
                <ImageView android:id="@id/backImg" android:clickable="true" {ICON} android:src="@drawable/back" android:contentDescription="Back" />
            </LinearLayout>
            <RelativeLayout android:id="@id/listTitle" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0">
                <TextView style="@style/Text.TitleLarge" android:id="@id/{title_id}" android:paddingLeft="8dip" android:paddingRight="8dip" android:layout_width="fill_parent" android:layout_height="wrap_content" android:singleLine="true" android:ellipsize="end" />
            </RelativeLayout>
            <LinearLayout android:id="@id/shuffleAll" android:layout_width="wrap_content" android:layout_height="wrap_content">
                <ImageView android:id="@id/shuffleImg" android:clickable="true" android:layout_width="48dip" android:layout_height="48dip" android:padding="4dip" android:scaleType="fitCenter" android:background="?android:attr/selectableItemBackgroundBorderless" android:src="@drawable/shuffle_off" android:contentDescription="Shuffle all" />
            </LinearLayout>
            <LinearLayout android:id="@id/home" android:layout_width="wrap_content" android:layout_height="wrap_content">
                <ImageView android:id="@id/homeImg" android:clickable="true" {ICON} android:src="@drawable/home2" android:contentDescription="Home" />
            </LinearLayout>
        </LinearLayout>
    </RelativeLayout>
    <RelativeLayout android:id="@id/listTracks" android:layout_width="fill_parent" android:layout_height="wrap_content" android:layout_above="@id/playerLayout" android:layout_below="@id/menuTop">
        <ListView android:id="@id/{list_id}" android:clipToPadding="false" android:paddingBottom="8dip" android:layout_width="fill_parent" android:layout_height="fill_parent" />
    </RelativeLayout>
    <RelativeLayout android:id="@id/playerLayout" android:layout_width="fill_parent" android:layout_height="wrap_content" android:layout_alignParentBottom="true">
        {mini_player(wrapper_ids=True)}
    </RelativeLayout>
</RelativeLayout>
'''


for name in ('listview', 'tracklisttab', 'searchresult'):
    layouts[name] = list_screen(original_id(name, 'TextView', 'listTitle'), original_id(name, 'ListView'))


def simple_list(name):
    lid = original_id(name, 'ListView')
    return f'''{HDR}<LinearLayout android:orientation="vertical" android:background="@color/m3_surface" android:layout_width="fill_parent" android:layout_height="fill_parent" {NS}>
    <ListView android:id="@id/{lid}" android:clipToPadding="false" android:paddingTop="4dip" android:paddingBottom="8dip" android:layout_width="fill_parent" android:layout_height="fill_parent" />
</LinearLayout>
'''


for name in ('artisttab', 'albumtab', 'tracktab', 'moretab'):
    layouts[name] = simple_list(name)


def row_item(image_id, top_id, bottom_id, image_bg):
    return f'''{HDR}<LinearLayout android:orientation="horizontal" android:gravity="center_vertical" android:minHeight="72dip" android:paddingLeft="16dip" android:paddingRight="24dip" android:paddingTop="8dip" android:paddingBottom="8dip" android:layout_width="fill_parent" android:layout_height="wrap_content" {NS}>
    <ImageView android:id="@id/{image_id}" android:background="@drawable/{image_bg}" android:clipToOutline="true" android:layout_width="56dip" android:layout_height="56dip" android:scaleType="centerCrop" />
    <LinearLayout android:orientation="vertical" android:paddingLeft="16dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
        <TextView style="@style/Text.BodyLarge" android:id="@id/{top_id}" android:layout_width="fill_parent" android:layout_height="wrap_content" android:singleLine="true" android:ellipsize="end" />
        <TextView style="@style/Text.BodyMedium" android:id="@id/{bottom_id}" android:layout_width="fill_parent" android:layout_height="wrap_content" android:singleLine="true" android:ellipsize="end" />
    </LinearLayout>
</LinearLayout>
'''


layouts['artist_item'] = row_item('image', 'artist', 'album', 'm3_avatar_bg')
layouts['album_item'] = row_item('image', 'album', 'artist', 'm3_thumb_bg')
layouts['track_item'] = f'''{HDR}<LinearLayout android:gravity="center_vertical" android:minHeight="56dip" android:paddingLeft="16dip" android:layout_width="fill_parent" android:layout_height="wrap_content" {NS}>
    <ImageView android:background="@drawable/m3_leading_icon_bg" android:padding="8dip" android:layout_width="40dip" android:layout_height="40dip" android:src="@drawable/ic_m3_music_note" />
    <TextView style="@style/Text.BodyLarge" android:id="@id/trackTitle" android:paddingLeft="16dip" android:paddingRight="24dip" android:paddingTop="12dip" android:paddingBottom="12dip" android:layout_width="fill_parent" android:layout_height="wrap_content" android:singleLine="true" android:ellipsize="end" />
</LinearLayout>
'''
layouts['radioitem'] = f'''{HDR}<LinearLayout android:gravity="center_vertical" android:minHeight="56dip" android:paddingLeft="16dip" android:layout_width="fill_parent" android:layout_height="wrap_content" {NS}>
    <ImageView android:id="@id/genreImage" android:layout_width="24dip" android:layout_height="24dip" android:scaleType="fitCenter" />
    <TextView style="@style/Text.BodyLarge" android:id="@id/stationTitle" android:paddingLeft="16dip" android:paddingRight="24dip" android:paddingTop="12dip" android:paddingBottom="12dip" android:layout_width="fill_parent" android:layout_height="wrap_content" android:singleLine="true" android:ellipsize="end" />
</LinearLayout>
'''

layouts['main'] = f'''{HDR}<LinearLayout android:orientation="vertical" android:paddingLeft="24dip" android:paddingTop="8dip" android:paddingRight="24dip" android:paddingBottom="16dip" android:layout_width="fill_parent" android:layout_height="wrap_content" {NS}>
    <TextView style="@style/Text.BodyMedium" android:id="@id/TextView03" android:layout_width="fill_parent" android:layout_height="wrap_content" android:text="Enter your Olive's IP address or hostname. Over Tailscale, use its Tailscale IP (100.x.x.x) or MagicDNS name." />
    <EditText android:id="@id/tf_DevIP" android:layout_width="fill_parent" android:layout_height="wrap_content" android:layout_marginTop="16dip" android:hint="@string/typeIP" android:inputType="textUri" android:singleLine="true" />
    <LinearLayout android:orientation="horizontal" android:gravity="center_vertical" android:layout_marginTop="16dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
        <Button android:id="@+id/scanNetwork" style="@style/Olive.Button.Text" android:visibility="gone" android:layout_marginLeft="-12dip" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Scan a network" />
        <View android:layout_width="0dip" android:layout_height="1dip" android:layout_weight="1.0" />
        <Button android:id="@id/connect" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="@string/connect" />
    </LinearLayout>
    <LinearLayout android:gravity="center_vertical" android:orientation="horizontal" android:paddingTop="16dip" android:paddingBottom="4dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
        <ProgressBar android:id="@id/searchLoadingIcon" android:layout_width="20dip" android:layout_height="20dip" android:layout_marginRight="12dip" />
        <TextView style="@style/Text.BodyMedium" android:id="@id/searchDevicesText" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0" android:text="@string/selectDevs" />
    </LinearLayout>
    <ListView android:id="@id/ListViewDevices" android:layout_width="fill_parent" android:layout_height="wrap_content" />
</LinearLayout>
'''


def info_row(lab, lab_text, val, visible=True):
    vis = '' if visible else ' android:visibility="gone"'
    # Hidden pairs collapse with their row because the row wraps its content.
    return f'''<LinearLayout android:orientation="horizontal" android:gravity="center_vertical" android:paddingTop="2dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
                    <TextView style="@style/Text.LabelSmall" android:id="@id/{lab}" android:text="{lab_text}" android:minWidth="64dip" android:layout_width="wrap_content" android:layout_height="wrap_content"{vis} />
                    <TextView style="@style/Text.BodyLarge" android:id="@id/{val}" android:layout_width="wrap_content" android:layout_height="wrap_content" android:singleLine="true" android:ellipsize="end"{vis} />
                </LinearLayout>'''


def meta_column(side):
    rows = []
    for i in (1, 2, 3):
        rows.append(f'<TextView style="@style/Text.LabelSmall" android:id="@id/{side}{i}title" android:paddingTop="{4 if i == 1 else 12}dip" android:layout_width="wrap_content" android:layout_height="wrap_content" />')
        rows.append(f'<TextView style="@style/Text.BodyMedium" android:textColor="@color/m3_on_surface" android:id="@id/{side}{i}content" android:layout_width="wrap_content" android:layout_height="wrap_content" android:singleLine="true" android:ellipsize="end" />')
    return '\n                        '.join(rows)


PRESS = 'android:stateListAnimator="@animator/m3_press"'
layouts['playscreenlarge'] = f'''{HDR}<LinearLayout android:orientation="vertical" android:background="@color/m3_surface" android:layout_width="fill_parent" android:layout_height="fill_parent" {NS}>
    {top_bar(f"""<ImageView android:id="@id/back" android:clickable="true" {ICON} android:src="@drawable/ic_m3_collapse" android:contentDescription="Close player" />
        <TextView style="@style/Text.LabelLarge" android:gravity="center" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0" android:text="Now playing" />
        <ImageView android:id="@id/volume" android:clickable="true" {ICON} android:src="@drawable/volume_0" android:contentDescription="Volume" />
        <ImageView android:id="@id/playlist" android:clickable="true" {ICON} android:src="@drawable/add2playlist" android:contentDescription="Add to playlist" />""")}
    <ImageView android:id="@id/major_genre" android:layout_width="fill_parent" android:layout_height="0dip" />
    <ScrollView android:fillViewport="true" android:layout_width="fill_parent" android:layout_height="0dip" android:layout_weight="1.0">
        <LinearLayout android:orientation="vertical" android:paddingLeft="24dip" android:paddingRight="24dip" android:paddingTop="8dip" android:paddingBottom="8dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
            <LinearLayout android:orientation="vertical" android:id="@id/LinearLayout02" android:layout_width="fill_parent" android:layout_height="wrap_content">
                <ImageView android:id="@id/artwork" android:layout_gravity="center_horizontal" android:background="@drawable/m3_artwork_bg" android:clipToOutline="true" android:elevation="2dip" android:layout_width="296dip" android:layout_height="296dip" android:scaleType="centerCrop" android:src="@drawable/artworknotfound" />
                <TextView style="@style/Text.HeadlineSmall" android:fontFamily="@font/gsf_medium" android:id="@id/dctitle" android:paddingTop="24dip" android:layout_width="fill_parent" android:layout_height="wrap_content" android:singleLine="true" android:ellipsize="end" />
                <LinearLayout android:orientation="vertical" android:id="@id/layoutMainData" android:paddingTop="4dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
                {info_row('artistTitle', 'ARTIST', 'artist')}
                {info_row('albumTitle', 'ALBUM', 'album')}
                {info_row('trackNoTitle', 'TRACK', 'track')}
                {info_row('stationName', 'STATION', 'stationNameTitle', visible=False)}
                </LinearLayout>
            </LinearLayout>
            <LinearLayout android:orientation="horizontal" android:id="@id/ratingLayout" android:gravity="center_vertical" android:paddingTop="8dip" android:layout_marginLeft="-6dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
                <ImageView android:id="@id/star1" android:clickable="true" {PRESS} android:padding="6dip" android:layout_width="44dip" android:layout_height="44dip" android:src="@drawable/star_unset" />
                <ImageView android:id="@id/star2" android:clickable="true" {PRESS} android:padding="6dip" android:layout_width="44dip" android:layout_height="44dip" android:src="@drawable/star_unset" />
                <ImageView android:id="@id/star3" android:clickable="true" {PRESS} android:padding="6dip" android:layout_width="44dip" android:layout_height="44dip" android:src="@drawable/star_unset" />
                <ImageView android:id="@id/star4" android:clickable="true" {PRESS} android:padding="6dip" android:layout_width="44dip" android:layout_height="44dip" android:src="@drawable/star_unset" />
                <ImageView android:id="@id/star5" android:clickable="true" {PRESS} android:padding="6dip" android:layout_width="44dip" android:layout_height="44dip" android:src="@drawable/star_unset" />
            </LinearLayout>
            <LinearLayout android:orientation="horizontal" android:id="@id/metadataLayout" android:background="@drawable/m3_card_flat" android:padding="16dip" android:layout_marginTop="12dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
                <LinearLayout android:orientation="vertical" android:id="@id/LinearLayout02" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0">
                        {meta_column('left')}
                </LinearLayout>
                <LinearLayout android:orientation="vertical" android:id="@id/LinearLayout02" android:paddingLeft="16dip" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0">
                        {meta_column('right')}
                </LinearLayout>
            </LinearLayout>
        </LinearLayout>
    </ScrollView>
    <LinearLayout android:orientation="horizontal" android:id="@id/TimeLayout" android:gravity="center_vertical" android:paddingLeft="24dip" android:paddingTop="12dip" android:paddingRight="24dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
        <TextView style="@style/Text.LabelMedium" android:id="@id/time1" android:layout_width="wrap_content" android:layout_height="wrap_content" android:singleLine="true" />
        <ProgressBar android:id="@id/progressBar" style="@style/Olive.LinearProgress" android:layout_marginLeft="12dip" android:layout_marginRight="12dip" android:layout_width="0dip" android:layout_height="4dip" android:layout_weight="1.0" />
        <TextView style="@style/Text.LabelMedium" android:id="@id/time2" android:layout_width="wrap_content" android:layout_height="wrap_content" android:singleLine="true" />
    </LinearLayout>
    <LinearLayout android:orientation="horizontal" android:id="@id/playerLayout" android:gravity="center" android:paddingTop="16dip" android:paddingBottom="8dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
        <ImageView android:id="@id/shuffle" android:tag="shuffle_off" android:clickable="true" {PRESS} android:padding="4dip" android:scaleType="fitCenter" android:background="?android:attr/selectableItemBackgroundBorderless" android:layout_width="48dip" android:layout_height="48dip" android:src="@drawable/shuffle_off" android:contentDescription="Shuffle" />
        <ImageButton android:id="@id/left_skip" android:background="@drawable/m3_icon_tonal" {PRESS} android:clickable="true" android:longClickable="true" android:layout_marginLeft="8dip" android:layout_width="64dip" android:layout_height="56dip" android:src="@drawable/left_skip" android:contentDescription="Previous" />
        <ImageButton android:id="@id/playpause" android:tag="PLAYING" android:background="@null" {PRESS} android:clickable="true" android:layout_marginLeft="8dip" android:layout_marginRight="8dip" android:layout_width="96dip" android:layout_height="72dip" android:scaleType="fitCenter" android:src="@drawable/playpause" android:contentDescription="Play or pause" />
        <ImageButton android:id="@id/right_skip" android:background="@drawable/m3_icon_tonal" {PRESS} android:clickable="true" android:longClickable="true" android:layout_marginRight="8dip" android:layout_width="64dip" android:layout_height="56dip" android:src="@drawable/right_skip" android:contentDescription="Next" />
        <ImageView android:id="@id/repeat" android:tag="repeat_off" android:clickable="true" {PRESS} android:padding="4dip" android:scaleType="fitCenter" android:background="?android:attr/selectableItemBackgroundBorderless" android:layout_width="48dip" android:layout_height="48dip" android:src="@drawable/repeat_off" android:contentDescription="Repeat" />
    </LinearLayout>
    <LinearLayout android:orientation="horizontal" android:id="@id/VolumeLayout" android:gravity="center_vertical" android:paddingLeft="12dip" android:paddingRight="12dip" android:paddingBottom="16dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
        <ImageView android:id="@id/volumeDown" android:clickable="true" {ICON} android:src="@drawable/volumedown2" android:contentDescription="Volume down" />
        <SeekBar android:id="@id/volumeBar" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0" />
        <ImageView android:id="@id/volumeUp" android:clickable="true" {ICON} android:src="@drawable/volumeup2" android:contentDescription="Volume up" />
    </LinearLayout>
</LinearLayout>
'''
layouts['playscreen'] = layouts['playscreenlarge']

layouts['settings'] = f'''{HDR}<LinearLayout android:orientation="vertical" android:background="@color/m3_surface" android:layout_width="fill_parent" android:layout_height="fill_parent" {NS}>
    <LinearLayout android:id="@id/listTitle" android:background="@color/m3_surface" android:gravity="center_vertical" android:layout_width="fill_parent" android:layout_height="64dip">
        <TextView style="@style/Text.TitleLarge" android:id="@id/ListViewSearchTitle" android:paddingLeft="16dip" android:layout_width="fill_parent" android:layout_height="wrap_content" android:text="Settings" android:singleLine="true" />
    </LinearLayout>
    <LinearLayout android:id="@id/listTitle" android:layout_width="fill_parent" android:layout_height="wrap_content" />
    <LinearLayout android:gravity="center_vertical" android:orientation="horizontal" android:id="@id/LinearLayout01" android:minHeight="72dip" android:paddingLeft="16dip" android:paddingRight="12dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
        <LinearLayout android:orientation="vertical" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0">
            <TextView style="@style/Text.BodyLarge" android:id="@id/TextView02" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Clear artwork cache" />
            <TextView style="@style/Text.BodyMedium" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Re-download album art from the Olive" />
        </LinearLayout>
        <ImageButton android:id="@id/clearArtwork" {ICON} android:src="@drawable/save" android:contentDescription="Clear artwork cache" />
    </LinearLayout>
    <LinearLayout android:gravity="center_vertical" android:orientation="horizontal" android:minHeight="72dip" android:paddingLeft="16dip" android:paddingRight="16dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
        <LinearLayout android:orientation="vertical" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0">
            <TextView style="@style/Text.BodyLarge" android:id="@id/TextView01" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Olive power" />
            <TextView style="@style/Text.BodyMedium" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Switch between on and standby" />
        </LinearLayout>
        <ProgressBar android:id="@id/waiting" android:layout_width="24dip" android:layout_height="24dip" />
        <ToggleButton android:id="@id/toggleStandby" android:textOn="On" android:textOff="Off" android:visibility="gone" android:layout_width="wrap_content" android:layout_height="wrap_content" />
    </LinearLayout>
</LinearLayout>
'''

layouts['add2playlist'] = f'''{HDR}<LinearLayout android:orientation="vertical" android:background="@color/m3_surface" android:paddingTop="16dip" android:layout_width="fill_parent" android:layout_height="fill_parent" {NS}>
    <LinearLayout android:orientation="vertical" android:paddingLeft="16dip" android:paddingRight="16dip" android:layout_width="fill_parent" android:layout_height="wrap_content" android:layout_weight="0.0">
        <TextView style="@style/Text.TitleMedium" android:layout_width="fill_parent" android:layout_height="wrap_content" android:text="New playlist" />
        <LinearLayout android:gravity="center_vertical" android:orientation="horizontal" android:paddingTop="8dip" android:layout_width="fill_parent" android:layout_height="wrap_content" android:layout_weight="0.0">
            <EditText android:id="@id/newPLname" android:hint="Playlist name" android:singleLine="true" android:focusable="true" android:focusableInTouchMode="true" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0" />
            <ImageButton android:id="@id/btnNewPL" android:background="@drawable/m3_icon_tonal" {PRESS} android:layout_marginLeft="8dip" android:padding="14dip" android:scaleType="fitCenter" android:layout_width="56dip" android:layout_height="56dip" android:src="@drawable/add2playlist" android:contentDescription="Create playlist" android:layout_weight="0.0" />
        </LinearLayout>
        <TextView style="@style/Text.TitleMedium" android:paddingTop="24dip" android:paddingBottom="4dip" android:layout_width="fill_parent" android:layout_height="wrap_content" android:text="Add to playlist" />
    </LinearLayout>
    <LinearLayout android:orientation="vertical" android:layout_width="fill_parent" android:layout_height="fill_parent" android:layout_weight="1.0">
        <ListView android:id="@id/ListViewPLs" android:layout_width="fill_parent" android:layout_height="wrap_content" />
    </LinearLayout>
</LinearLayout>
'''

layouts['searchentry'] = f'''{HDR}<LinearLayout android:orientation="vertical" android:paddingLeft="24dip" android:paddingTop="8dip" android:paddingRight="24dip" android:paddingBottom="16dip" android:layout_width="fill_parent" android:layout_height="fill_parent" {NS}>
    <LinearLayout android:gravity="center_vertical" android:layout_width="fill_parent" android:layout_height="wrap_content">
        <EditText android:id="@id/searchText" android:layout_width="0dip" android:layout_height="wrap_content" android:hint="Artists, albums, tracks" android:singleLine="true" android:imeOptions="actionSearch" android:layout_weight="1.0" />
        <ImageButton android:id="@id/voiceControl" android:clickable="true" android:layout_marginLeft="8dip" android:background="@drawable/m3_icon_tonal" {PRESS} android:padding="14dip" android:scaleType="fitCenter" android:layout_width="56dip" android:layout_height="56dip" android:src="@drawable/micro" android:contentDescription="Voice search" />
    </LinearLayout>
    <Button android:layout_gravity="right" android:id="@id/btnSearch" android:layout_marginTop="16dip" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Search" />
    <LinearLayout android:layout_width="fill_parent" android:layout_height="wrap_content">
        <ListView android:id="@id/searchList" android:layout_width="fill_parent" android:layout_height="wrap_content" />
    </LinearLayout>
</LinearLayout>
'''

layouts['volume_setup'] = f'''{HDR}<LinearLayout android:orientation="vertical" android:paddingLeft="24dip" android:paddingRight="12dip" android:paddingTop="12dip" android:paddingBottom="20dip" android:layout_width="312dip" android:layout_height="wrap_content" {NS}>
    <LinearLayout android:orientation="horizontal" android:gravity="center_vertical" android:layout_width="fill_parent" android:layout_height="wrap_content">
        <TextView style="@style/Text.HeadlineSmall" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Volume" android:singleLine="true" />
        <TextView style="@style/Text.HeadlineSmall" android:textColor="@color/m3_primary" android:id="@id/volumeVal" android:paddingLeft="12dip" android:layout_width="wrap_content" android:layout_height="wrap_content" android:singleLine="true" />
        <ProgressBar android:id="@id/volumeProgress" android:layout_width="20dip" android:layout_height="20dip" android:layout_marginLeft="12dip" />
        <View android:layout_width="0dip" android:layout_height="1dip" android:layout_weight="1.0" />
        <ImageView android:id="@id/cancelVolume" {ICON} android:src="@drawable/cancel" android:contentDescription="Close" />
    </LinearLayout>
    <SeekBar android:id="@id/volumeSlider" android:layout_marginTop="12dip" android:layout_marginRight="12dip" android:layout_width="fill_parent" android:layout_height="wrap_content" />
    <LinearLayout android:orientation="horizontal" android:paddingLeft="12dip" android:paddingRight="24dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
        <TextView style="@style/Text.LabelMedium" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0" android:text="0" />
        <TextView style="@style/Text.LabelMedium" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="100" />
    </LinearLayout>
</LinearLayout>
'''


def radio(i, text, checked=False):
    c = ' android:checked="true"' if checked else ''
    return f'<RadioButton style="@style/Text.BodyLarge" android:id="@id/btn{i}" android:minHeight="48dip" android:paddingLeft="8dip" android:layout_width="fill_parent" android:layout_height="wrap_content"{c} android:text="{text}" />'


FIELD_LABEL = 'style="@style/Text.LabelLarge" android:paddingTop="20dip" android:paddingBottom="8dip"'
layouts['addurl'] = f'''{HDR}<LinearLayout android:orientation="vertical" android:background="@color/m3_surface" android:layout_width="fill_parent" android:layout_height="fill_parent" {NS}>
    <TextView style="@style/Text.TitleLarge" android:id="@id/add2favTitle" android:gravity="center_vertical" android:paddingLeft="16dip" android:paddingRight="16dip" android:layout_width="fill_parent" android:layout_height="64dip" android:singleLine="true" android:ellipsize="end" />
    <ScrollView android:layout_width="fill_parent" android:layout_height="0dip" android:layout_weight="1.0">
        <LinearLayout android:orientation="vertical" android:paddingLeft="16dip" android:paddingRight="16dip" android:paddingBottom="16dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
            <TextView android:id="@id/TextView01" {FIELD_LABEL.replace('paddingTop="20dip"', 'paddingTop="4dip"')} android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Station name" />
            <EditText android:id="@id/name" android:singleLine="true" android:layout_width="fill_parent" android:layout_height="wrap_content" android:hint="@string/typeFavName" />
            <TextView android:id="@id/TextView02" {FIELD_LABEL} android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Stream URL" />
            <EditText android:id="@id/url" android:singleLine="true" android:inputType="textUri" android:layout_width="fill_parent" android:layout_height="wrap_content" android:hint="@string/typeFavURL" />
            <TextView android:id="@id/TextView03" {FIELD_LABEL} android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Genre" />
            <RadioGroup android:orientation="horizontal" android:id="@id/ir_genre" android:layout_width="fill_parent" android:layout_height="wrap_content">
                <LinearLayout android:orientation="vertical" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0">
                    {radio(1, 'Classical', True)}
                    {radio(2, 'Jazz')}
                    {radio(3, 'Blues')}
                </LinearLayout>
                <LinearLayout android:orientation="vertical" android:layout_width="0dip" android:layout_height="wrap_content" android:layout_weight="1.0">
                    {radio(4, 'Rock/Pop')}
                    {radio(5, 'Spoken word')}
                    {radio(6, 'Other')}
                </LinearLayout>
            </RadioGroup>
        </LinearLayout>
    </ScrollView>
    <LinearLayout android:gravity="right" android:paddingLeft="16dip" android:paddingRight="16dip" android:paddingTop="8dip" android:paddingBottom="16dip" android:layout_width="fill_parent" android:layout_height="wrap_content">
        <Button android:id="@id/btnAddURL" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="Add to favourites" />
    </LinearLayout>
</LinearLayout>
'''

for name, content in layouts.items():
    write(f'res/layout/{name}.xml', content)

# Tag keys used by the Compat helpers.
p = pathlib.Path('res/values/ids.xml'); s = p.read_text()
p.write_text(s.replace('</resources>', '    <item type="id" name="olive_play_icon" />\n    <item type="id" name="olive_list_watch" />\n</resources>'))

# --- Strings, manifest, sdk -------------------------------------------------------------------
p = pathlib.Path('res/values/strings.xml'); s = p.read_text()
s = s.replace('>Enter IP-address here<', '>e.g. 192.168.1.20 or 100.64.0.5<')
p.write_text(s)

p = pathlib.Path('AndroidManifest.xml'); s = p.read_text()
s = s.replace('<activity android:name=".LoadDevices" ', '<activity android:name=".LoadDevices" android:label="Connect to your Olive" ')
s = s.replace('android:label="Search" ', 'android:label="Search music" ')
s = s.replace('<activity android:name=".LoadTabs" ', '<activity android:name=".LoadTabs" android:theme="@style/OliveTheme.Home" ')
s = s.replace('<activity android:name=".Playscreen" ', '<activity android:name=".Playscreen" android:theme="@style/OliveTheme.Player" ')
p.write_text(s)

# M3 ripples use colour state lists with alpha (API 23+).
p = pathlib.Path('apktool.yml'); s = p.read_text()
p.write_text(s.replace('  minSdkVersion: 21\n', '  minSdkVersion: 23\n'))

# --- Safety check: every original id keeps its view class -------------------------------------
def ids(path):
    out = set()
    for e in ET.parse(path).iter():
        i = e.get(A + 'id')
        if i:
            out.add((i.split('/')[-1], e.tag))
    return out


problems = []
for name in layouts:
    orig = original / 'res/layout' / f'{name}.xml'
    if orig.exists():
        missing = ids(orig) - ids(f'res/layout/{name}.xml')
        if missing:
            problems.append(f'{name}: {sorted(missing)}')
if problems:
    sys.exit('UI patch dropped or retyped view ids:\n' + '\n'.join(problems))
print('M3 UI patched:', len(layouts), 'layouts,', len(replacements) + len(glyphs) + len(components), 'drawables; ids verified')
