package com.example.neytro.test10;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by Neytro on 2015-08-16.
 */
public class LoadingImageClass {
    class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private String url;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView _imageView) {
            imageViewReference = new WeakReference<ImageView>(_imageView);
        }

        @Override
        // Actual download method, run in the task thread
        protected Bitmap doInBackground(String... params) {
            // params comes from the execute() call: params[0] is the url.
            return loadBitmap(params[0], 100, 100);
        }

        @Override
        // Once the image is downloaded, associates it to the imageView
        protected void onPostExecute(Bitmap _bitmap) {
            if (isCancelled()) {
                _bitmap = null;
            }
            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
                // Change bitmap only if this process is still associated with it
                if (this == bitmapDownloaderTask) {
                    imageView.setImageBitmap(_bitmap);
                }
            }
        }
    }

    private static BitmapDownloaderTask getBitmapDownloaderTask(ImageView _imageView) {
        if (_imageView != null) {
            Drawable drawable = _imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
                return downloadedDrawable.getBitmapDownloaderTask();
            }
        }
        return null;
    }

    static class DownloadedDrawable extends ColorDrawable {
        private final WeakReference<BitmapDownloaderTask> bitmapDownloaderTaskReference;

        public DownloadedDrawable(BitmapDownloaderTask _bitmapDownloaderTask) {
            super(Color.BLUE);
            bitmapDownloaderTaskReference =
                    new WeakReference<BitmapDownloaderTask>(_bitmapDownloaderTask);
        }

        public BitmapDownloaderTask getBitmapDownloaderTask() {
            return bitmapDownloaderTaskReference.get();
        }
    }

    public void loadImage(String _url, ImageView _imageView) {
        if (cancelPotentialDownload(_url, _imageView)) {
            BitmapDownloaderTask task = new BitmapDownloaderTask(_imageView);
            DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
            _imageView.setImageDrawable(downloadedDrawable);
            task.execute(_url);
        }
    }

    private static boolean cancelPotentialDownload(String _url, ImageView _imageView) {
        BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(_imageView);
        if (bitmapDownloaderTask != null) {
            String bitmapUrl = bitmapDownloaderTask.url;
            if ((bitmapUrl == null) || (!bitmapUrl.equals(_url))) {
                bitmapDownloaderTask.cancel(true);
            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }
        return true;
    }

    //optimize image
    public static int calculateInSampleSize(BitmapFactory.Options _options, int _reqWidth, int _reqHeight) {
        // Raw height and width of image
        final int height = _options.outHeight;
        final int width = _options.outWidth;
        int inSampleSize = 1;
        if (height > _reqHeight || width > _reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > _reqHeight
                    && (halfWidth / inSampleSize) > _reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    //load image
    public static Bitmap loadBitmap(String _res, int _reqWidth, int _reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(_res, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, _reqWidth, _reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(_res, options);
    }

    //set Path for saving file
    public static File pathForImage() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/Android/data/MyFiles");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}
