package com.etsyflipper.flipper;

import android.app.Activity;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.util.ArrayList;

/**
 * Created by patriciaestridge on 5/17/15.
 */
public class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<FlipsyItem>> {
    private Activity activity;
    private ItemInterface itemInterface;

    public FetchItemsTask(Activity activity, ItemInterface itemInterface) {
        this.activity = activity;
        this.itemInterface = itemInterface;
    }

    @Override
    protected ArrayList<FlipsyItem> doInBackground(Void... params) {

        String query = PreferenceManager.getDefaultSharedPreferences(activity)
                .getString(ApiFetcher.SEARCH_QUERY, null);
        if (query != null) {
            return new ApiFetcher().search(query);
        } else {
            return new ApiFetcher().getItems();
        }
    }

    @Override
    protected void onPostExecute(ArrayList<FlipsyItem> items) {
        itemInterface.itemsFetched(items);
    }
}
