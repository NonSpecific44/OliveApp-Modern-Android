#!/bin/sh
# Recompiles helpers/src (the Java helper classes patched into the app) into helpers/smali.
#
# Usage:
#   ANDROID_JAR=android-all-14-robolectric-10818077.jar DX_JAR=dalvik-dx-14.0.0_r21.jar APKTOOL=apktool_2.10.0.jar ./build-helpers.sh
#
# ANDROID_JAR: any Android 14 framework jar to compile against (org.robolectric:android-all:14-robolectric-10818077
#              from Maven Central works). DX_JAR: com.jakewharton.android.repackaged:dalvik-dx:14.0.0_r21.
# The old dx only reads Java 6/7 class files, so the Java 8 output (no lambdas used) is relabelled as version 50.
set -eu
HERE=$(cd "$(dirname "$0")" && pwd)
: "${ANDROID_JAR:?}" "${DX_JAR:?}" "${APKTOOL:?}"
WORK=$(mktemp -d)
trap 'rm -rf "$WORK"' EXIT

javac -nowarn --release 8 -cp "$ANDROID_JAR" -d "$WORK/classes" "$HERE"/src/olive/app/*.java
python3 -I - "$WORK/classes" <<'EOF'
import pathlib, sys
for f in pathlib.Path(sys.argv[1]).rglob("*.class"):
    b = bytearray(f.read_bytes()); b[6], b[7] = 0, 50; f.write_bytes(b)
EOF
(cd "$WORK/classes" && java -cp "$DX_JAR" com.android.dx.command.Main --dex --output="$WORK/classes.dex" .)
(cd "$WORK" && zip -q helpers.apk classes.dex)
java -jar "$APKTOOL" d -r -o "$WORK/decoded" "$WORK/helpers.apk" >/dev/null
rm -f "$HERE"/smali/*.smali
cp "$WORK"/decoded/smali/olive/app/*.smali "$HERE/smali/"
ls "$HERE/smali"
