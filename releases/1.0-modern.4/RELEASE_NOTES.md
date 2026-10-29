The Olive network music player's remote-control app, rebuilt to install and run on modern Android (6.0 to 16), with a Material 3 / Android 16 design.

### Highlights
- Installs on Android 14, 15 and 16 (the original was blocked).
- Works over mobile data and VPNs such as **Tailscale**: type the Olive's Tailscale IP or MagicDNS name into the connect box. Automatic discovery still needs the Olive's own Wi-Fi.
- Fixed crashes on launch and on button presses, blocked HTTP, frozen commands and the unreachable Search/Refresh/Shuffle menu.
- **Material 3 design:** dynamic colour from your wallpaper, light and dark themes, Google Sans Flex, Material Symbols, an M3 navigation bar, a floating now-playing card, expressive player controls and M3 motion.
- Built-in crash reporter: if it ever closes unexpectedly, the next launch shows the error with a Copy button.

### Install
Download `OliveApp-Modern-1.0-modern.4.apk`, open it on your phone and allow "Install unknown apps". If Play Protect warns about an unknown developer, choose **More details → Install anyway**.

> **If you installed a test build before this release**, uninstall it first. This release is signed with the project's permanent release key, and Android won't update across signing keys. Only the saved Olive address is lost. Future releases will install as updates.

### Verify your download
- SHA-256 of the APK: `598d3c42de8561f460de6ebc7a0b95eb2b62a4ce9ea02b3ad56bdcbe111b47da`
- Signing certificate SHA-256: `64:E6:76:8E:FC:65:11:18:C4:B4:2F:FC:D0:71:21:91:1C:8B:D8:7C:00:0C:E3:DA:AA:24:A2:3E:B9:70:45:F3` (`CN=OliveApp Modern Android, O=NonSpecific44`, RSA 4096)

```sh
sha256sum -c OliveApp-Modern-1.0-modern.4.apk.sha256
apksigner verify --print-certs OliveApp-Modern-1.0-modern.4.apk
```

Requires Android 6.0+; targets Android 14 (API 34). Bundles Google Sans Flex (OFL 1.1) and Material Symbols (Apache 2.0).
