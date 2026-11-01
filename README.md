# OliveApp-Modern-Android

The official remote-control app for **Olive** network music players (Olive 4HD, O2M, Melody and others),
rescued from 2011 and rebuilt to install and run on modern Android, with a Material 3 / Android 16 look.

Olive's app was pulled from Google Play long ago. It targeted Android 1.6, so Android 14 and later refuse to install
it, and even where it installed it no longer worked. This project patches the original app rather than rewriting it,
so it sends the player exactly the same commands the original did.

![Light and dark screenshots](docs/screenshots/overview.png)

## Download

Get **`OliveApp-modern-<version>.apk`** from the [latest release](../../releases/latest), copy it to your phone and
open it. Allow "Install unknown apps" when prompted; if Play Protect warns about an unknown developer, choose
**More details → Install anyway**.

After that the app keeps itself up to date: it checks for a new release once a day (or from **Settings → Check for
updates**), shows what's new, and installs it once you confirm. Before installing, it verifies the download
against its published SHA-256 and checks that it's signed with the same key as the installed app.

Each release lists the APK's SHA-256 (also attached as a `.sha256` file) and the signing certificate fingerprint,
`64:E6:76:8E:…:45:F3`, so you can check a download by hand. Every release from 1.0-modern.4 on uses the same key, so
newer versions install as updates.

Requires Android 6.0 or newer; designed for Android 14-16.

## Connecting to your Olive

- **On the Olive's network:** the app finds the player automatically.
- **Olive on a different subnet or VLAN** (e.g. phone on `10.50.1.x`, Olive on `10.50.10.x`): tap **Scan a network**
  in the device picker and enter the Olive's network, e.g. `10.50.10.0/24`. The original automatic search uses
  multicast, which routers don't forward between subnets; the scan uses ordinary connections that they do. Networks
  where an Olive was found, and the network of any address you've connected to, are scanned automatically next time.
- **Anywhere else** (mobile data with Tailscale or another VPN): type the player's address into the connect box,
  e.g. its Tailscale IP (`100.x.x.x`), MagicDNS name, or LAN IP if your tailnet routes that subnet.

## Lock-screen controls

While your Olive plays, Android's media player (lock screen, quick settings, notifications) shows the artwork,
track and artist, with play/pause and skip controls, and your phone's volume keys control the Olive's volume, like
Spotify Connect. This uses a standard Android media session, so it also works with car displays, watches and
headset buttons.

## What was changed

| Problem on modern Android | Fix |
|---|---|
| Install blocked: no target SDK (Android 14+ requires 23/24+), v1-only signature | Targets API 34; signed with v1+v2+v3 |
| Crash on first launch: device search forced a Wi-Fi disconnect/reconnect, now refused with a SecurityException | Removed; any search error falls back to typing the address |
| Only worked on Wi-Fi: "no Wi-Fi IP" was treated as "no network" | Any active network (mobile data, Tailscale, ...) is accepted |
| Crashes on buttons (`NetworkOnMainThreadException`) | Restores the permissive thread policy the app was written for |
| Plain `http://` to the player blocked; Apache HttpClient removed from Android | Cleartext allowed; `org.apache.http.legacy` declared |
| `AsyncTask` became serial: one slow request froze every command and the now-playing poll | All 85 call sites run on the parallel executor, as on Android 1.x/2.x |
| Search / Refresh / Shuffle / Quit only reachable via the hardware MENU key | App bar search and shuffle buttons, pull to refresh |
| Artwork cache in shared storage (blocked by scoped storage); manifest invalid for API 31+ | Private cache; manifest updated |
| Voice search disabled on Android 11+ (package visibility) | `<queries>` entry for the speech recogniser |
| No way to see why it closed | Built-in crash reporter: the next launch shows the error with a Copy button |
| Automatic search couldn't find an Olive on another subnet/VLAN (multicast isn't routed) | Unicast network scan: automatic for known networks, plus **Scan a network** |
| 2011 Android 1.x UI | Material 3 (below) |

### Material 3 / Android 16 design

- **Dynamic colour** from your wallpaper: the exact Material You roles on Android 14+, tonal palettes on 12-13, and
  an Olive-green M3 scheme on older versions. Light and dark follow the system.
- **Google Sans Flex** on the M3 type scale; **Material Symbols Rounded** icons.
- M3 navigation bar with animated indicator pill, floating now-playing card, rounded artwork, pill buttons, filled
  text fields, 28dp dialogs, M3 Expressive split-track slider and a shape-morphing play button.
- Emphasized-easing motion: tab fade-through, list items animating in, the player sliding up, press feedback.
- The Android 12+ splash screen and an adaptive, themed launcher icon.
- **Tablets and foldables:** a navigation rail instead of the bottom bar, a player that shows artwork and controls
  side by side when wide and stacked when tall (adapting on rotation), and wider margins and narrower dialogs on
  big screens. Phones stay in portrait.
- Pull to refresh on the library lists, with an inline loading indicator in place of the old modal progress
  dialogs.

The Material Components library isn't used (it's only on Google's Maven); all of this is built from framework
widgets, resources and a few helper classes.

## How it works

`build.sh` decodes [`original/OliveApp_1.0.apk`](original/) with apktool and applies three patch stages:

1. [`patches/patch_resources.py`](patches/patch_resources.py): manifest, permissions, SDK levels, adaptive icon.
2. [`patches/patch_ui.py`](patches/patch_ui.py): the Material 3 UI (colours, type, shapes, motion, layouts). The
   original code finds views by id and casts them, so this refuses to build if a redesigned layout drops or retypes
   any original view id.
3. [`patches/patch_smali.py`](patches/patch_smali.py): bytecode fixes, plus the helper classes in
   [`helpers/`](helpers/) (Java source in `helpers/src`, compiled smali in `helpers/smali`; regenerate with
   `helpers/build-helpers.sh`).

[`docs/original-decompiled/`](docs/original-decompiled/) has the original app's decompiled Java for reference.

### Building

Requires Java 17+, Python 3 with Pillow, [apktool 2.10.0](https://github.com/iBotPeaches/Apktool/releases) and
[uber-apk-signer 1.3.0](https://github.com/patrickfav/uber-apk-signer/releases).

```sh
APKTOOL=apktool_2.10.0.jar UBER_SIGNER=uber-apk-signer-1.3.0.jar \
OLIVE_KEYSTORE=/path/to/release.p12 OLIVE_KEYSTORE_PASS=... \
./build.sh out
```

The release key is never committed (see `.gitignore`); without `OLIVE_KEYSTORE` the APK is left unsigned in
`out/olive-unsigned.apk`. Builds are reproducible: the app code and resources match the release byte for byte.

### Releasing

1. Bump `versionCode` (always increasing) and `versionName` (`x.y.z`) in `patches/patch_resources.py`.
2. Build and sign with the release key (above), then add `releases/<x.y.z>/` with `OliveApp-modern-<x.y.z>.apk`,
   its `.sha256` (`sha256sum OliveApp-modern-<x.y.z>.apk > OliveApp-modern-<x.y.z>.apk.sha256`) and
   `RELEASE_NOTES.md`.
3. Merge to `main`. The [Publish release](.github/workflows/publish-release.yml) workflow creates the GitHub release
   `v<x.y.z>` with the APK and checksum, and installed apps offer it as an update.

`releases/1.0-modern.*` are the builds from before the 1.0.0 numbering, kept for reference.

### Testing

There's no emulator involved: [`test-harness/`](test-harness/) runs the app's real code under
[Robolectric](https://robolectric.org) (Android 14), against [`olive_server.py`](test-harness/olive_server.py), a
fake Olive serving sample data. It checks launch, the device picker without Wi-Fi, the crash reporter and the
artwork's rounded clipping, and renders every screen.

```sh
D2J=dex-tools-v2.4/d2j-dex2jar.sh test-harness/run-tests.sh out light   # or: dark
```

Screenshots of every screen are in [`docs/screenshots/`](docs/screenshots/). The simulator uses a default blue
wallpaper palette and can't draw rounded clipping or the real tab bar; on a phone, colours follow your wallpaper.

## Credits and licences

The original Olive app is © its original authors at Olive. Third-party assets bundled in the APK (Google Sans Flex, OFL 1.1;
Material Symbols, Apache 2.0) are listed in [`m3-assets/NOTICE.md`](m3-assets/NOTICE.md) with their licence texts.
