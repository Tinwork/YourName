package com.yellowman.tinwork.yourname.network.api.series;

import android.content.Context;

import com.yellowman.tinwork.yourname.model.Serie.SerieWrapper;
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

public class FilterSeries extends Fetch {
    private final RequestQueueManager queueManager;
    private Context ctx;
    private GsonGetManager<SerieWrapper> series;
    private int retry;

    /**
     * Filter Series
     *
     * @param context Context
     */
    public FilterSeries(Context context) {
        this.ctx = context;
        this.queueManager = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
        this.retry = 0;
    }

    /**
     * Get
     *
     * @param payload HashMap
     * @param callback GsonCallback
     */
    @Override
    public void get(HashMap<String, String> payload, final GsonCallback callback) {
        String token = AppUtils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = AppUtils.makeHeaders(null, token);
        // Bind the GET request params

        // @TODO pass by the other utils
        String[] foo = {payload.get("series_id") + "/filter?keys=" + payload.get("key?")};

        String URL = AppUtils.buildPlaceholderUrl(Routes.SERIES, foo, null);

        series = new GsonGetManager<>(URL, SerieWrapper.class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, series, ctx, retry, callback);
            retry++;
        }, false);

        queueManager.addToRequestQueue(series);
    }
}
