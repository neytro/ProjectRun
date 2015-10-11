package com.example.neytro.test10;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
/**
 * Created by Neytro on 2015-10-10.
 */
public class ActivityZoomImage extends Activity {
    String view;
    ClassLoadingImage loader;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        loadZoomImage();
    }

    private void loadZoomImage() {
        imageView = (ImageView) findViewById(R.id.imageViewZoom);
        loader = new ClassLoadingImage();
        view = getIntent().getStringExtra(AdapterHistory.TAG);
        loader.loadImage(view, imageView, 400, 400);
    }
}
