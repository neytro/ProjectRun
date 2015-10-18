package com.example.neytro.test10;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
/**
 * Created by Neytro on 2015-10-10.
 */
public class ActivityZoomImage extends Activity {
    private final int IMAGE_WIDTH = 400;
    private final int IMAGE_HEIGHT = 400;
    private String pathForImage;
    private ClassLoadingImage loader;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        loadZoomImage();
    }

    private void loadZoomImage() {
        imageView = (ImageView) findViewById(R.id.imageViewZoom);
        loader = new ClassLoadingImage();
        pathForImage = getIntent().getStringExtra(AdapterHistory.TAG);
        loader.loadImage(pathForImage, imageView, IMAGE_WIDTH, IMAGE_HEIGHT);
    }
}
