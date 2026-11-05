The remote-control app for Olive network music players, rebuilt for modern Android (6.0 to 16) with a Material 3 design. First release under the new 1.0.0 numbering.

### New in 1.0.0
- **In-app updates.** Olive checks for a new release once a day (or from **Settings → Check for updates**), shows what's new, and installs it after checking it's genuine: the download must match its published SHA-256 and be signed with the same key as the installed app. The first time, Android asks you to allow Olive to install apps.

### Everything in the app
- **Material 3 design:** dynamic colour from your wallpaper, light and dark themes, Google Sans Flex, and Material Symbols.
- **Tablets and foldables:** a navigation rail, and a player that shows artwork and controls side by side when the screen is wide. Phones stay in portrait.
- **Library:** pull to refresh, and search and shuffle buttons in the top bar.
- **Lock-screen and quick-settings controls:** artwork and track details with play/pause and skip; the volume keys control the Olive's volume.
- **Finding your Olive:** works on the same Wi-Fi, across subnets and VLANs (**Scan a network**), and over Tailscale or another VPN by address.
- **Reliability:** a built-in crash reporter, and all of the original app's modern-Android fixes.

### Install
Download `OliveApp-modern-1.0.0.apk` and open it on your phone (allow "Install unknown apps" if asked). It installs as an update over 1.0-modern.4 and later, keeping your settings.

### Verify your download
- SHA-256 of the APK: `5f14e89d4a15ff74b77422c344a6b2fef19d0ef14f29a9481c343625e9742d7c`
- Signing certificate SHA-256: `64:E6:76:8E:FC:65:11:18:C4:B4:2F:FC:D0:71:21:91:1C:8B:D8:7C:00:0C:E3:DA:AA:24:A2:3E:B9:70:45:F3`

Requires Android 6.0+; targets Android 14 (API 34).
