### New
- **Lock-screen and quick-settings controls.** While your Olive is playing, Android's media player (on the lock screen, in quick settings and in notifications) shows the artwork, track, artist and album, with play/pause, previous and next. Tap it to open the player screen. Your phone's **volume keys** control the Olive's volume, even with the screen locked.
- **Find your Olive on another subnet or VLAN.** The device picker now scans networks directly as well as using the original automatic search. That search uses multicast, which routers don't pass between subnets, so it only ever found players on the phone's own network. The picker automatically scans the phone's network and any network you've connected to before, and **Scan a network** lets you enter one, e.g. `10.50.10.0/24`. Networks where an Olive was found are remembered.

### Fixed
- The play/pause button no longer pulses every few seconds while playing (from 1.0-modern.5).

### Notes
- The media controls appear after you open the app with an Olive configured, and stay while music is playing or paused. They disappear 10 minutes after playback stops, or straight away if the Olive can't be reached and the app isn't open.
- Installs as an update over 1.0-modern.4 and later (same signing key), keeping your settings.

### Verify your download
- SHA-256 of the APK: `91966fd16d8137b89ad615e9140b1e1940a2ccfa5bb8bb88a5a6581826aa2ce8`
- Signing certificate SHA-256: `64:E6:76:8E:FC:65:11:18:C4:B4:2F:FC:D0:71:21:91:1C:8B:D8:7C:00:0C:E3:DA:AA:24:A2:3E:B9:70:45:F3` (same key as 1.0-modern.4)

```sh
sha256sum -c OliveApp-Modern-1.0-modern.6.apk.sha256
apksigner verify --print-certs OliveApp-Modern-1.0-modern.6.apk
```

Requires Android 6.0+; targets Android 14 (API 34).
