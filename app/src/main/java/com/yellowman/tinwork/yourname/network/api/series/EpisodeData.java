package com.yellowman.tinwork.yourname.network.api.series;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.Episode;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 29/12/2017.
 * Created by Didier Youn on 29/12/2017.
 * Created by Abdel-Atif Mabrouck on 29/12/2017.
 * Created by Antoine Renault on 29/12/2017.
 */

public class EpisodeData extends Fetch {

    private Context ctx;
    private RequestQueueManager queueManager;
    private int retry;
    protected GsonGetManager<Episode> request;

    /**
     * Episode Data::Constructor
     *
     * @param ctx
     */
    public EpisodeData(Context ctx) {
        this.ctx = ctx;
        this.queueManager = RequestQueueManager.getInstance(ctx);
        this.retry = 0;
    }

    /**
     * Get
     *
     * @param payload
     * @param callback
     */
    @Override
    public void get(HashMap<String, String> payload, GsonCallback callback) {
        // @TODO though we can make an util which can does that for us...
        String token = Utils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = Utils.makeHeaders(null, token);
        String[] data = {payload.get("episode_id")};

        String URL = Utils.buildPlaceholderUrl(Routes.SEARCH_EPISODES, data, null);

        request = new GsonGetManager<>(URL, Episode.class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, request, ctx, retry, callback);
            retry++;
        }, true);

        queueManager.addToRequestQueue(request);
    }
}
