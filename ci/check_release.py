"""Checks that the committed release APK for the version being built comes from this source.

Every entry in releases/<version>/OliveApp-modern-<version>.apk except the signature (META-INF/) must match a
fresh unsigned build byte for byte, and the .sha256 file must match the APK. Exits 0 with a note when the
version being built has no committed release yet (work in progress).

Usage: python3 -I ci/check_release.py <build output dir>
"""
import hashlib
import pathlib
import re
import sys
import zipfile

build = pathlib.Path(sys.argv[1]).resolve()
repo = pathlib.Path(__file__).resolve().parent.parent
version = re.search(r"^  versionName: *(\S+)", (build / "patched/apktool.yml").read_text(), re.M).group(1)
release = repo / "releases" / version
apk = release / f"OliveApp-modern-{version}.apk"
if not apk.exists():
    print(f"No committed release for {version} yet; nothing to compare.")
    sys.exit(0)


def entries(path):
    with zipfile.ZipFile(path) as z:
        return {i.filename: hashlib.sha256(z.read(i)).hexdigest() for i in z.infolist()
                if not i.filename.startswith("META-INF/") and not i.is_dir()}


fresh = entries(build / "olive-unsigned.apk")
committed = entries(apk)
problems = [f"only in the fresh build: {n}" for n in sorted(fresh.keys() - committed.keys())]
problems += [f"only in the committed APK: {n}" for n in sorted(committed.keys() - fresh.keys())]
problems += [f"differs: {n}" for n in sorted(fresh.keys() & committed.keys()) if fresh[n] != committed[n]]

expected = (release / f"{apk.name}.sha256").read_text().split()[0].lower()
actual = hashlib.sha256(apk.read_bytes()).hexdigest()
if expected != actual:
    problems.append(f"{apk.name}.sha256 says {expected}, the APK is {actual}")

if problems:
    print(f"releases/{version} doesn't match this source:")
    print("\n".join("  " + p for p in problems))
    sys.exit(1)
print(f"releases/{version}: all {len(fresh)} entries match a fresh build of this source; checksum OK.")
