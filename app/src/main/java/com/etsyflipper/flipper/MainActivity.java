package com.etsyflipper.flipper;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.etsyflipper.estyflipper.R;

import java.util.ArrayList;

public class MainActivity extends Activity implements ItemInterface {

    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private ArrayList<FlipsyItem> mItems;
    private AsyncTask<Void, Void, ArrayList<FlipsyItem>> fetchItems;

    public void onNewIntent(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putString(ApiFetcher.SEARCH_QUERY, query)
                    .commit();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ItemThumbnailDownloader<ImageView> thumbnailDownloader = startImageThread();

        mPager = (ViewPager) findViewById(R.id.pager);
        mItems = new ArrayList<>();
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

