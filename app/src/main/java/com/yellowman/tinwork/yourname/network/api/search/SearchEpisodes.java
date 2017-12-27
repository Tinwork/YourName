package com.yellowman.tinwork.yourname.network.api.search;

import android.content.Context;
import android.util.Log;

import com.yellowman.tinwork.yourname.model.Serie.SingleEpisodeWrapper;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.HashMap;

/**
 * Created by aktane on 14/12/17.
 */

public class SearchEpisodes extends Fetch {
    private final RequestQueueManager queueManager;
    private Context ctx;
    private GsonGetManager<SingleEpisodeWrapper> series;
    private int retry;

    /**
     *
     * @param context
     */
    public SearchEpisodes(Context context) {
        this.ctx = context;
        this.queueManager = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
        this.retry = 0;
    }

    @Override
    public void get(HashMap<String, String> payload, final GsonCallback callback) {
        String[] data = {payload.get("series_id")};

        String token = Utils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = Utils.makeHeaders(null, token);
        // Bind the GET request params
        String URL = Utils.buildPlaceholderUrl(Routes.SEARCH_EPISODES, data , null);

        Log.d("Debug", "URL : "+URL);

        series = new GsonGetManager<>(URL, SingleEpisodeWrapper.class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, series, ctx, retry, callback);
            retry++;
        }, false);

        queueManager.addToRequestQueue(series);
    }
}
