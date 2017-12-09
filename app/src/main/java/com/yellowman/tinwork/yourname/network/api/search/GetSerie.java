package com.yellowman.tinwork.yourname.network.api.search;

import android.content.Context;

import com.yellowman.tinwork.yourname.model.Search;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.HashMap;

/**
 * Created by almabrouck on 07/12/2017.
 */

public class GetSerie extends Fetch {

    private final RequestQueueManager queueManager;
    private Context ctx;
    private GsonGetManager<Search> series;
    private int retry;

    /**
     * Search Series
     *
     * @param context
     */
    public GetSerie(Context context) {
        this.ctx = context;
        this.queueManager = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
        this.retry = 0;
    }

    /**
     * Get
     *
     * @param payload
     * @param callback
     */
    @Override
    public void get(HashMap<String, String> payload, final GsonCallback callback) {
        String token = Utils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = Utils.makeHeaders(null, token);
        // Bind the GET request params

        String[] foo = {payload.get("series_id")};
        String URL = Utils.buildPlaceholderUrl(Routes.SERIES, foo, null);

        series = new GsonGetManager<>(URL, Search.class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, series, ctx, retry, callback);
            retry++;
        });

        queueManager.addToRequestQueue(series);
    }
}
