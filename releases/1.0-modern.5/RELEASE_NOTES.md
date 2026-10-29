### Fixed
- The play/pause button no longer pulses every few seconds while music plays. The player refreshes the now-playing info regularly and re-sets the button each time; the icon is now only animated when it actually changes (for example when you tap pause).

### Install
Download `OliveApp-Modern-1.0-modern.5.apk` and open it on your phone. It installs as an update over 1.0-modern.4 (same signing key), keeping your saved Olive address.

### Verify your download
- SHA-256 of the APK: `3750a6a770e3984025c8d5f1776a7f70212befdd1d7e4013b042b34375a96fa0`
- Signing certificate SHA-256: `64:E6:76:8E:FC:65:11:18:C4:B4:2F:FC:D0:71:21:91:1C:8B:D8:7C:00:0C:E3:DA:AA:24:A2:3E:B9:70:45:F3` (same key as 1.0-modern.4)

```sh
sha256sum -c OliveApp-Modern-1.0-modern.5.apk.sha256
apksigner verify --print-certs OliveApp-Modern-1.0-modern.5.apk
```

Requires Android 6.0+; targets Android 14 (API 34). For everything else in this version, see the [1.0-modern.4 notes](../1.0-modern.4/RELEASE_NOTES.md).
