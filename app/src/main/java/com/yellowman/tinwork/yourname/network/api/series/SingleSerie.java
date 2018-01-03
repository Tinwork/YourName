package com.yellowman.tinwork.yourname.network.api.series;

import android.content.Context;

import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 27/12/2017.
 * Created by Didier Youn on 27/12/2017.
 * Created by Abdel-Atif Mabrouck on 27/12/2017.
 * Created by Antoine Renault on 27/12/2017.
 */

public class SingleSerie extends Fetch {

    private Context ctx;
    protected GsonGetManager<Series> request;
    protected RequestQueueManager queueManager;
    protected CommonManager realmManager;
    protected int retry;

    /**
     * Single Serie::Constructor
     *
     * @param ctx
     */
    public SingleSerie(Context ctx) {
        this.ctx   = ctx;
        this.retry = 0;
        this.queueManager = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
        this.realmManager = new CommonManager();
    }

    /**
     * Get
     *
     * @param payload
     * @param callback
     */
    public void get(HashMap<String, String> payload, GsonCallback callback) {
        String[] data = {payload.get("series_id")};
        String token = Utils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = Utils.makeHeaders(null, token);

        String URL = Utils.buildPlaceholderUrl(Routes.PREFIX_SERIES, data, null);
        request = new GsonGetManager<>(URL, Series.class, headers, response -> {
            realmManager.updateSeriesMisc(response, payload.get("series_id"));
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, request, ctx, retry, callback);
            retry++;
        }, true);

        queueManager.addToRequestQueue(request);
    }
}
