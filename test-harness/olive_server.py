"""Minimal fake Olive music server, enough to render the app's screens with data."""
import http.server, io, sys, urllib.parse
from xml.sax.saxutils import escape
from PIL import Image, ImageDraw

PORT = int(sys.argv[1])
BASE = f"http://127.0.0.1:{PORT}"
ARTISTS = ["Air", "Bonobo", "Daft Punk", "Fleetwood Mac", "Massive Attack", "Nils Frahm", "Portishead",
           "Radiohead", "Röyksopp", "The Cinematic Orchestra", "Tycho", "Zero 7"]
ALBUMS = [("Moon Safari", "Air"), ("Black Sands", "Bonobo"), ("Discovery", "Daft Punk"), ("Rumours", "Fleetwood Mac"),
          ("Mezzanine", "Massive Attack"), ("Says", "Nils Frahm"), ("Dummy", "Portishead"), ("In Rainbows", "Radiohead"),
          ("Melody A.M.", "Röyksopp"), ("Ma Fleur", "The Cinematic Orchestra"), ("Dive", "Tycho"), ("Simple Things", "Zero 7")]
TRACKS = ["La Femme d'Argent", "Sexy Boy", "All I Need", "Kelly Watch the Stars", "Talisman", "Remember",
          "You Make It Easy", "Ce Matin-Là", "New Star in the Sky", "Le Voyage de Pénélope"]
COLORS = [(196, 87, 60), (60, 120, 170), (120, 160, 70), (170, 70, 140), (220, 170, 50), (70, 160, 150)]

def result(entries):
    body = "".join(f'<container id="obj{i}" restricted="1"><title>{escape(t)}</title><artist>{escape(a)}</artist>'
                   f'<albumArtURI>http://127.0.0.1/art/{i}.png</albumArtURI></container>' for i, (t, a) in enumerate(entries))
    return f'<?xml version="1.0"?><Result><TotalMatches>{len(entries)}</TotalMatches>{body}</Result>'

def tree(titles, isdir="1", action=""):
    body = "".join(f'<item><title>{escape(t)}</title><objid>id{i}</objid><action>{action}</action><isDir>{isdir}</isDir>'
                   f'<genre>Electronic</genre><uuid>u{i}</uuid><ip>127.0.0.1</ip></item>' for i, t in enumerate(titles))
    return f'<?xml version="1.0"?><tree><itemsReturned>{len(titles)}</itemsReturned><totalItems>{len(titles)}</totalItems>{body}</tree>'

METADATA = f'''<?xml version="1.0"?><playmetadata><volume>1</volume><volVal>45</volVal><transportState>PLAYING</transportState>
<progress>38</progress><currentPlayMode>NORMAL</currentPlayMode><PlayType>LOCAL</PlayType><numberOfTracks>10</numberOfTracks>
<currentTrack>2</currentTrack><currentTrackDuration>0:04:58</currentTrackDuration><relativeTimePosition>0:01:53</relativeTimePosition>
<objId>t2</objId><dctitle>Sexy Boy</dctitle><album>Moon Safari</album><artist>Air</artist><albumart>http://127.0.0.1/art/0.png</albumart>
<genre>Electronic</genre><releasedDate>1998</releasedDate><bitrate>1411</bitrate><stationTitle></stationTitle><myRating>4</myRating></playmetadata>'''

def art(i):
    img = Image.new("RGB", (300, 300), COLORS[i % len(COLORS)])
    d = ImageDraw.Draw(img)
    for k in range(0, 300, 30):
        d.ellipse((150 - k, 150 - k, 150 + k, 150 + k), outline=(255, 255, 255))
    buf = io.BytesIO(); img.save(buf, "PNG"); return buf.getvalue()

class H(http.server.BaseHTTPRequestHandler):
    def log_message(self, fmt, *a):
        sys.stderr.write("FAKE " + (fmt % a) + "\n")
    def do_GET(self):
        u = urllib.parse.urlparse(self.path); q = urllib.parse.parse_qs(u.query)
        ctype = "text/xml"
        if u.path.startswith("/art/"):
            body, ctype = art(int(u.path[5:].split(".")[0])), "application/octet-stream"
        elif "getMetadata" in u.path:
            body = METADATA
        elif "artworkZapp" in u.path:
            crit = q.get("searchCriteria", [""])[0]
            body = result([(a, a) for a in ARTISTS] if crit == "artist" else ALBUMS)
        elif "iGetCompilationsAndTracks" in u.path:
            idv = q.get("id", [""])[0]
            body = tree(["Favourites", "Dinner party", "Sunday morning"]) if idv == "ROOT_ALLPL" else tree(TRACKS, isdir="0")
        elif "iGetRadio" in u.path:
            body = tree(["Favourites", "Genres", "Countries", "Search", "Add URL"])
        elif "getMusicSources" in u.path:
            body = tree(["Olive internal HDD", "NAS: music share", "USB stick"])
        elif "getSearch" in u.path:
            body = result([(a, a) for a in ARTISTS[:3]])
        elif "getVolume" in u.path:
            body = "45"
        else:
            body = "OK"
        data = body if isinstance(body, bytes) else body.encode()
        self.send_response(200); self.send_header("Content-Type", ctype); self.send_header("Content-Length", str(len(data)))
        self.end_headers(); self.wfile.write(data)
    do_POST = do_GET

http.server.ThreadingHTTPServer(("127.0.0.1", PORT), H).serve_forever()
