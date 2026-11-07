#!/bin/sh
# Builds the modernised Olive APK from the original 2011 APK in original/.
#
# Usage:
#   APKTOOL=/path/apktool_2.10.0.jar UBER_SIGNER=/path/uber-apk-signer-1.3.0.jar ./build.sh <empty output dir>
#
# Requires Java 17+, Python 3 with Pillow, apktool 2.10.x and uber-apk-signer 1.3.x.
#
# Signing (optional). The release key is never committed; pass it through the environment:
#   OLIVE_KEYSTORE        path to the keystore (.p12 or .jks)
#   OLIVE_KEYSTORE_PASS   keystore/key password
#   OLIVE_KEY_ALIAS       key alias (default: olive)
# Without OLIVE_KEYSTORE the APK is left unsigned in <output dir>/olive-unsigned.apk.
set -eu

OUT=${1:?usage: build.sh <empty output dir>}
HERE=$(cd "$(dirname "$0")" && pwd)
: "${APKTOOL:?set APKTOOL to the apktool 2.10.x jar}"

mkdir -p "$OUT"
OUT=$(cd "$OUT" && pwd)
if [ -e "$OUT/patched" ] || [ -e "$OUT/original-decoded" ]; then
    echo "$OUT already contains a build; use an empty output directory" >&2
    exit 1
fi

java -jar "$APKTOOL" d -o "$OUT/original-decoded" "$HERE/original/OliveApp_1.0.apk"
cp -r "$OUT/original-decoded" "$OUT/patched"

# 1. Manifest, permissions, SDK levels, adaptive icon.
python3 -I "$HERE/patches/patch_resources.py" "$OUT/patched"
# 2. Material 3 UI: colours, type, shapes, motion, layouts (verifies every original view id survives).
python3 -I "$HERE/patches/patch_ui.py" "$OUT/patched" "$OUT/original-decoded" "$HERE/m3-assets"
# 2b. Appearance: colour roles as theme attributes, so the palette (wallpaper or Olive green) can be chosen.
python3 -I "$HERE/patches/patch_appearance.py" "$OUT/patched" "$HERE/m3-assets"
# 3. Bytecode fixes plus the helper classes in helpers/smali.
python3 -I "$HERE/patches/patch_smali.py" "$OUT/patched" "$HERE/helpers/smali"

java -jar "$APKTOOL" b "$OUT/patched" -o "$OUT/olive-unsigned.apk"

if [ -z "${OLIVE_KEYSTORE:-}" ]; then
    echo "OLIVE_KEYSTORE not set: unsigned APK at $OUT/olive-unsigned.apk"
    exit 0
fi
: "${UBER_SIGNER:?set UBER_SIGNER to the uber-apk-signer 1.3.x jar}"
: "${OLIVE_KEYSTORE_PASS:?set OLIVE_KEYSTORE_PASS}"
java -jar "$UBER_SIGNER" -a "$OUT/olive-unsigned.apk" -o "$OUT/signed" \
    --ks "$OLIVE_KEYSTORE" --ksAlias "${OLIVE_KEY_ALIAS:-olive}" \
    --ksPass "$OLIVE_KEYSTORE_PASS" --ksKeyPass "$OLIVE_KEYSTORE_PASS"
echo "Signed APK: $(ls "$OUT"/signed/*.apk)"
