package com.etsyflipper.flipper;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.etsyflipper.estyflipper.R;
import com.etsyflipper.flipper.interfaces.ItemInterface;
import com.etsyflipper.flipper.objects.FlipsyItem;
import com.etsyflipper.flipper.threads.ApiFetcher;
import com.etsyflipper.flipper.threads.FetchItemsTask;
import com.etsyflipper.flipper.ui.PageTransformer;
import com.etsyflipper.flipper.ui.ScreenSlidePagerAdapter;

import java.util.ArrayList;

public class EtsyViewerActivity extends Activity implements ItemInterface {

    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private ArrayList<FlipsyItem> mItems;
    private AsyncTask<Void, Void, ArrayList<FlipsyItem>> fetchItems;

    public static void startMe(Activity starter) {
        Intent itt = new Intent();
        itt.setClass(starter, EtsyViewerActivity.class);
        starter.startActivity(itt);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager) findViewById(R.id.pager);
        mItems = new ArrayList<>();

        mPagerAdapter = new ScreenSlidePagerAdapter(mItems, this);
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new PageTransformer());

        handleIntent(getIntent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_search) {

            onSearchRequested();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            PreferenceManager.getDefaultSharedPreferences(this)
                    .edit()
                    .putString(ApiFetcher.SEARCH_QUERY, query)
                    .commit();
        }
        fetchItems();
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    private void fetchItems() {
        fetchItems = new FetchItemsTask(this, this).execute();
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

