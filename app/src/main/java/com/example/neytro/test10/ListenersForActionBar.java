package com.example.neytro.test10;
import android.view.View;
import android.widget.ImageView;
/**
 * Created by Neytro on 2015-10-25.
 */
public class ListenersForActionBar implements View.OnClickListener {
    private ImageView imageViewPosition;
    private ImageView imageViewOverflow;
    private ImageView imageViewMap;

    public void activateListners(View view) {
        addReferences(view);
        imageViewMap.setOnClickListener(this);
        imageViewOverflow.setOnClickListener(this);
        imageViewPosition.setOnClickListener(this);
    }

    private void addReferences(View view) {
        imageViewMap = (ImageView) view.findViewById(R.id.imageView_map);
        imageViewOverflow = (ImageView) view.findViewById(R.id.imageView_overflow);
        imageViewPosition = (ImageView) view.findViewById(R.id.imageView_position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_position:
                // setMapFragment();
                //  hidePositionImageAndShowMapImage();
                showMapImage();
                break;
            case R.id.imageView_overflow:
                // setOnPupMenu(v);
                break;
            case R.id.imageView_map:
                // saveLastViewOfFragment();
                showPositionImage();
                break;
        }
    }

    private void showMapImage() {
        imageViewMap.setVisibility(View.VISIBLE);
        imageViewPosition.setVisibility(View.INVISIBLE);
    }

    private void showPositionImage() {
        imageViewMap.setVisibility(View.INVISIBLE);
        imageViewPosition.setVisibility(View.VISIBLE);
    }
}