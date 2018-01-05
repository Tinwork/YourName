package com.yellowman.tinwork.yourname.network.api.series;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.EpisodeMisc;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 27/12/2017.
 * Created by Didier Youn on 27/12/2017.
 * Created by Abdel-Atif Mabrouck on 27/12/2017.
 * Created by Antoine Renault on 27/12/2017.
 */

public class EpisodeSummary extends Fetch {

    private Context ctx;
    private GsonGetManager<EpisodeMisc> request;
    private RequestQueueManager queueManager;
    private int retry;

    /**
     * Episode Summary::Constructor
     *
     * @param ctx
     */
    public EpisodeSummary(Context ctx) {
        this.ctx   = ctx;
        this.queueManager = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
        this.retry = 0;
    }

    /**
     * Get
     *
     * @param payload
     * @param callback
     */
    public void get(HashMap<String, String> payload, GsonCallback callback) {
        String token = AppUtils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = AppUtils.makeHeaders(null, token);
        String[] data = {payload.get("series_id")};

        String URL = AppUtils.buildPlaceholderUrl(Routes.SERIES, data, Routes.SUFFIX_ROUTES_EPISODES_SUMMARY);
        request = new GsonGetManager<>(URL, EpisodeMisc.class, headers, response -> {
            callback.onSuccess(response);
        }, error ->  {
            this.handleVolleyError(error, request, ctx, retry, callback);
            retry++;
        }, true);

        queueManager.addToRequestQueue(request);
    }
}
