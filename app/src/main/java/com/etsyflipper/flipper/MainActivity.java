package com.etsyflipper.flipper;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.etsyflipper.estyflipper.R;
import com.etsyflipper.flipper.interfaces.ItemInterface;
import com.etsyflipper.flipper.objects.FlipsyItem;
import com.etsyflipper.flipper.threads.FetchItemsTask;
import com.etsyflipper.flipper.threads.ItemThumbnailDownloader;
import com.etsyflipper.flipper.ui.PageTransformer;
import com.etsyflipper.flipper.ui.ScreenSlidePagerAdapter;

import java.util.ArrayList;

public class MainActivity extends Activity implements ItemInterface {

    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private ArrayList<FlipsyItem> mItems;
    private AsyncTask<Void, Void, ArrayList<FlipsyItem>> fetchItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        mItems = new ArrayList<>();

        ItemThumbnailDownloader<ImageView> thumbnailDownloader = startImageThread();

        mPagerAdapter = new ScreenSlidePagerAdapter(mItems, this, thumbnailDownloader);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new PageTransformer());

        fetchItems();


    }

    private void fetchItems() {
        fetchItems = new FetchItemsTask(this, this).execute();
    }

    private ItemThumbnailDownloader<ImageView> startImageThread() {
        ItemThumbnailDownloader<ImageView> mThumbnailThread;
        mThumbnailThread = new ItemThumbnailDownloader<ImageView>(new Handler());
        mThumbnailThread.setListener(new ItemThumbnailDownloader.Listener<ImageView>() {
            public void onThumbnailDownloaded(ImageView imageView, Bitmap thumbnail) {
                imageView.setImageBitmap(thumbnail);
            }
        });
        mThumbnailThread.start();
        mThumbnailThread.getLooper();

        return mThumbnailThread;
    }

    @Override
    public void itemsFetched(ArrayList<FlipsyItem> mItems) {
        mPagerAdapter.dataUpdated(mItems);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
}

