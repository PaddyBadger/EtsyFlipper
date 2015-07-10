package com.etsyflipper.flipper.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.etsyflipper.estyflipper.R;
import com.etsyflipper.flipper.objects.FlipsyItem;
import com.squareup.picasso.Picasso;

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

    public void bindData(View v, FlipsyItem item) {

        TextView titleTextView = (TextView) v.findViewById(R.id.title);
        String title = item.getTitle();
        titleTextView.setText(title);

        ImageView imageView = (ImageView) v.findViewById(R.id.item_imageView);
        Picasso.with(getContext()).load(item.getImageUrl()).into(imageView);

        TextView priceTextView = (TextView) v.findViewById(R.id.price);
        String price = item.getPrice();
        priceTextView.setText(price);

        TextView descriptionTextView = (TextView) v.findViewById(R.id.description);
        String description = item.getDescription();
        descriptionTextView.setText(description);
    }
}
