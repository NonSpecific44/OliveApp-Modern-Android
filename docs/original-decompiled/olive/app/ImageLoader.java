package olive.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread;
import java.net.URL;
import java.util.HashMap;
import java.util.Stack;

/* loaded from: classes.dex */
public class ImageLoader {
    private File cacheDir;
    private HashMap<String, Bitmap> cache = new HashMap<>();
    final int tmp_img = R.drawable.artworknotfound;
    PhotosQueue photosQueue = new PhotosQueue();
    PhotosLoader photoLoaderThread = new PhotosLoader();

    public ImageLoader(Context context) {
        this.photoLoaderThread.setPriority(4);
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.cacheDir = new File(Environment.getExternalStorageDirectory(), "ArtistList");
        } else {
            this.cacheDir = context.getCacheDir();
        }
        if (!this.cacheDir.exists()) {
            this.cacheDir.mkdirs();
        }
    }

    public void DisplayImage(String url, Activity activity, ImageView imageView) {
        if (this.cache.containsKey(url)) {
            imageView.setImageBitmap(this.cache.get(url));
        } else {
            queuePhoto(url, activity, imageView);
        }
    }

    private void queuePhoto(String url, Activity activity, ImageView imageView) {
        this.photosQueue.Clean(imageView);
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        synchronized (this.photosQueue.photosToLoad) {
            this.photosQueue.photosToLoad.push(p);
            this.photosQueue.photosToLoad.notifyAll();
        }
        if (this.photoLoaderThread.getState() == Thread.State.NEW) {
            this.photoLoaderThread.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getBitmap(String url) {
        String filename = String.valueOf(url.hashCode());
        File f = new File(this.cacheDir, filename);
        Bitmap b = decodeFile(f);
        if (b != null) {
            return b;
        }
        try {
            InputStream is = new URL(url).openStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            Bitmap bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Bitmap decodeFile(File f) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            int width_tmp = o.outWidth;
            int height_tmp = o.outHeight;
            int scale = 1;
            while (width_tmp / 2 >= 70 && height_tmp / 2 >= 70) {
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private class PhotoToLoad {
        public ImageView imageView;
        public String url;

        public PhotoToLoad(String u, ImageView i) {
            this.url = u;
            this.imageView = i;
        }
    }

    public void stopThread() {
        this.photoLoaderThread.interrupt();
    }

    class PhotosQueue {
        private Stack<PhotoToLoad> photosToLoad = new Stack<>();

        PhotosQueue() {
        }

        public void Clean(ImageView image) {
            int j = 0;
            while (j < this.photosToLoad.size()) {
                try {
                    if (this.photosToLoad.size() >= j && this.photosToLoad.get(j).imageView == image) {
                        this.photosToLoad.remove(j);
                    } else {
                        j++;
                    }
                } catch (Exception e) {
                    return;
                }
            }
        }
    }

    class PhotosLoader extends Thread {
        PhotosLoader() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            PhotoToLoad photoToLoad;
            do {
                try {
                    if (ImageLoader.this.photosQueue.photosToLoad.size() == 0) {
                        synchronized (ImageLoader.this.photosQueue.photosToLoad) {
                            ImageLoader.this.photosQueue.photosToLoad.wait();
                        }
                    }
                    if (ImageLoader.this.photosQueue.photosToLoad.size() != 0) {
                        synchronized (ImageLoader.this.photosQueue.photosToLoad) {
                            photoToLoad = (PhotoToLoad) ImageLoader.this.photosQueue.photosToLoad.pop();
                        }
                        Bitmap bmp = ImageLoader.this.getBitmap(photoToLoad.url);
                        ImageLoader.this.cache.put(photoToLoad.url, bmp);
                        Object tag = photoToLoad.imageView.getTag();
                        if (tag != null && ((String) tag).equals(photoToLoad.url)) {
                            BitmapDisplayer bd = ImageLoader.this.new BitmapDisplayer(bmp, photoToLoad.imageView);
                            Activity a = (Activity) photoToLoad.imageView.getContext();
                            a.runOnUiThread(bd);
                        }
                    }
                } catch (InterruptedException e) {
                    return;
                }
            } while (!Thread.interrupted());
        }
    }

    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        ImageView imageView;

        public BitmapDisplayer(Bitmap b, ImageView i) {
            this.bitmap = b;
            this.imageView = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.bitmap != null) {
                this.imageView.setImageBitmap(this.bitmap);
            } else {
                this.imageView.setImageResource(R.drawable.artworknotfound);
            }
        }
    }

    public void clearCache() {
        this.cache.clear();
        File[] files = this.cacheDir.listFiles();
        for (File f : files) {
            f.delete();
        }
    }
}
