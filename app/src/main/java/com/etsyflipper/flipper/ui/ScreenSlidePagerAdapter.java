package com.etsyflipper.flipper.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsyflipper.estyflipper.R;
import com.etsyflipper.flipper.objects.FlipsyItem;

import java.util.ArrayList;

/**
 * Created by patriciaestridge on 5/17/15.
 */
public class ScreenSlidePagerAdapter extends PagerAdapter {

    private ArrayList<FlipsyItem> items;
    private LayoutInflater inflater;
 //   private ItemThumbnailDownloader thumbnailDownloader;

    public ScreenSlidePagerAdapter(ArrayList<FlipsyItem> mItems, Context context) {
        items = mItems;
        inflater = LayoutInflater.from(context);
  //      this.thumbnailDownloader = thumbnailDownloader;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {


        FlipsyItem flipsyItem = items.get(position);
        GalleryView view = (GalleryView) inflater.inflate(R.layout.gallery_fragment, container, false);
        view.bindData(view, flipsyItem);
        container.addView(view, 0);
        return view;
    }

    public void dataUpdated(ArrayList<FlipsyItem> mItems) {
        items = mItems;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }
}
