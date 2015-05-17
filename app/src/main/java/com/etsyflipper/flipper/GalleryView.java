package com.etsyflipper.flipper;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.etsyflipper.estyflipper.R;

import java.util.ArrayList;

/**
 * Created by patriciaestridge on 4/17/14.
 */
public class GalleryView extends ScrollView {

    public GalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryView(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void bindData(View v, FlipsyItem item, ItemThumbnailDownloader<ImageView> mThumbnailThread) {

        TextView titleTextView = (TextView) v.findViewById(R.id.title);
        String title = item.getTitle();
        titleTextView.setText(title);

        ImageView imageView = (ImageView) v.findViewById(R.id.item_imageView);
        mThumbnailThread.queueThumbnail(imageView, item.getImageUrl());

        TextView priceTextView = (TextView) v.findViewById(R.id.price);
        String price = item.getPrice();
        priceTextView.setText(price);

        TextView descriptionTextView = (TextView) v.findViewById(R.id.description);
        String description = item.getDescription();
        descriptionTextView.setText(description);
    }
}
