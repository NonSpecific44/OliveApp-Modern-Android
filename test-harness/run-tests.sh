#!/bin/sh
# Runs the Robolectric tests (launch, crash reporter, no-Wi-Fi, light/dark screenshots) against a build.
#
# Usage:
#   D2J=/path/dex-tools-v2.4/d2j-dex2jar.sh ./run-tests.sh <build output dir> [light|dark]
#
# <build output dir> is the directory given to ../build.sh. Requires Java 17+, Maven, git, Python 3 and
# dex2jar 2.4. Screenshots are written to <build output dir>/screenshots-<mode>/.
#
# Robolectric needs androidx.test:monitor, which is only published on Google's Maven repository; when that
# isn't reachable this builds the needed classes from the android/android-test sources (stubs/ fills in
# the annotation and tracing classes it references).
set -eu
HERE=$(cd "$(dirname "$0")" && pwd)
BUILD=$(cd "${1:?usage: run-tests.sh <build output dir> [light|dark]}" && pwd)
MODE=${2:-light}
: "${D2J:?set D2J to d2j-dex2jar.sh from dex2jar 2.4}"
APK="$BUILD/olive-unsigned.apk"
mkdir -p "$HERE/libs" "$HERE/src/test/resources/com/android/tools"

# App classes as JVM bytecode.
sh "$D2J" -f -o "$HERE/libs/olive.jar" "$APK"

# Android 14 framework jar (also needed to compile androidx.test:monitor).
mvn -q -B dependency:get -Dartifact=org.robolectric:android-all:14-robolectric-10818077 >/dev/null
ANDROID_ALL=$(find "${HOME}/.m2/repository/org/robolectric/android-all" -name 'android-all-14*.jar' | head -1)

if [ ! -f "$HERE/libs/androidx-test-monitor.jar" ]; then
    T=$(mktemp -d)
    git clone -q --depth 1 --filter=blob:none --sparse https://github.com/android/android-test.git "$T/at"
    git -C "$T/at" sparse-checkout set runner/monitor/java espresso/idling_resource/java
    find "$T/at" "$HERE/stubs" -name '*.java' | grep -v 'MonitoringInstrumentation.java\|hidden/ExposedInstrumentationApi' > "$T/srcs"
    # Drop sources Robolectric doesn't use that depend on unavailable libraries, until the rest compiles.
    for _ in 1 2 3 4 5 6; do
        rm -rf "$T/out" && mkdir "$T/out"
        if javac -nowarn -proc:none -d "$T/out" -cp "$ANDROID_ALL" @"$T/srcs" 2>"$T/err"; then break; fi
        grep -oE '^[^ ]+\.java' "$T/err" | sort -u > "$T/bad"
        grep -vxF -f "$T/bad" "$T/srcs" > "$T/srcs2" && mv "$T/srcs2" "$T/srcs"
    done
    (cd "$T/out" && jar cf "$HERE/libs/androidx-test-monitor.jar" .)
    rm -rf "$T"
fi

# Robolectric reads the merged manifest and resource APK from these paths.
sed 's#<uses-permission#<uses-sdk android:minSdkVersion="23" android:targetSdkVersion="34"/>\n    <uses-permission#' \
    "$BUILD/patched/AndroidManifest.xml" > "$HERE/AndroidManifest.xml"
cat > "$HERE/src/test/resources/com/android/tools/test_config.properties" <<EOF
android_merged_manifest=$HERE/AndroidManifest.xml
android_resource_apk=$APK
android_custom_package=olive.app
EOF

# Fake Olive player with sample data on localhost:8765, answering UPnP searches on UDP 1900.
python3 -I "$HERE/olive_server.py" 8765 1900 > "$BUILD/fake-olive.log" 2>&1 &
SERVER=$!
trap 'kill $SERVER 2>/dev/null' EXIT
sleep 1

NIGHT=false
[ "$MODE" = dark ] && NIGHT=true
mvn -B -q -f "$HERE/pom.xml" test -Dshots.night=$NIGHT -Dshots.dir="$BUILD/screenshots-$MODE"
grep -h 'Tests run' "$HERE"/target/surefire-reports/*.txt
