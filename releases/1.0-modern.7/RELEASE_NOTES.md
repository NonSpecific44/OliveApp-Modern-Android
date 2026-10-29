### New
- **Pull to refresh** on the Artists, Albums and Tracks lists, replacing the Refresh menu item.
- **Shuffle button** in the top bar on the Albums and Tracks tabs, replacing the "Shuffle all" menu item.
- **Tablet layouts, portrait and landscape:** a navigation rail down the side instead of the bottom bar; the player shows artwork and controls side by side when the screen is wide and stacked when it's tall, adapting as you rotate; dialogs and lists sized for big screens. Phones stay in portrait as before.
- **Loading without pop-ups:** lists show an inline spinner instead of the old "Working... Loading" dialog.

### Removed
- The ⋮ menu. Its items moved to the search and shuffle buttons and pull to refresh; Quit is gone (back from the Artists tab still offers to quit). The More tab no longer shows a ⋮ that did nothing.

### Also included
- Lock-screen media controls and network scanning (1.0-modern.6), and the play-button pulse fix (1.0-modern.5).

### Install
Installs as an update over 1.0-modern.4 and later (same signing key), keeping your settings.

### Verify your download
- SHA-256 of the APK: `2cc50201a9cfe3f21f0368567bbca44fd34fc891126e8db6dd23966ee7142208`
- Signing certificate SHA-256: `64:E6:76:8E:FC:65:11:18:C4:B4:2F:FC:D0:71:21:91:1C:8B:D8:7C:00:0C:E3:DA:AA:24:A2:3E:B9:70:45:F3` (same key as 1.0-modern.4)

```sh
sha256sum -c OliveApp-Modern-1.0-modern.7.apk.sha256
apksigner verify --print-certs OliveApp-Modern-1.0-modern.7.apk
```

Requires Android 6.0+; targets Android 14 (API 34).
