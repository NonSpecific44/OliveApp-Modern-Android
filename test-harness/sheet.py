import sys, glob, os
from PIL import Image, ImageDraw
d = sys.argv[1]; out = sys.argv[2]
files = sorted(glob.glob(d + "/*.png"))
W = 360
ims = []
for f in files:
    im = Image.open(f).convert("RGB"); h = int(im.height * W / im.width); ims.append((os.path.basename(f), im.resize((W, h))))
H = max(i.height for _, i in ims) + 30
cols = min(4, len(ims)); rows = (len(ims) + cols - 1) // cols
sheet = Image.new("RGB", (cols * (W + 10), rows * H), (255, 255, 255))
dr = ImageDraw.Draw(sheet)
for k, (n, im) in enumerate(ims):
    x = (k % cols) * (W + 10); y = (k // cols) * H
    dr.text((x + 4, y + 6), n, fill=(0, 0, 0)); sheet.paste(im, (x, y + 24))
sheet.save(out)
