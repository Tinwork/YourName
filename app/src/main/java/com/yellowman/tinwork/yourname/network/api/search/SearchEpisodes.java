package com.yellowman.tinwork.yourname.network.api.search;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.Episode;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;

/**
 * Created by aktane on 14/12/17.
 */

public class SearchEpisodes extends Fetch {
    private final RequestQueueManager queueManager;
    private Context ctx;
    private GsonGetManager<Episode> series;
    private int retry;

    /**
     *
     * @param context Context
     */
    public SearchEpisodes(Context context) {
        this.ctx = context;
        this.queueManager = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
        this.retry = 0;
    }

    /**
     * Get
     *
     * @param payload payload
     * @param callback callback
     */
    @Override
    public void get(HashMap<String, String> payload, final GsonCallback callback) {
        String[] data = {payload.get("series_id")};

        String token = AppUtils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = AppUtils.makeHeaders(null, token);
        // Bind the GET request params
        String URL = AppUtils.buildPlaceholderUrl(Routes.SEARCH_EPISODES, data , null);

        series = new GsonGetManager<>(URL, Episode.class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, series, ctx, retry, callback);
            retry++;
        }, true);

        queueManager.addToRequestQueue(series);
    }
}
