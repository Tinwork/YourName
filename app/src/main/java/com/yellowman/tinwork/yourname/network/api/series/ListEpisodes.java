package com.yellowman.tinwork.yourname.network.api.series;

import android.content.Context;
import android.util.Log;

import com.yellowman.tinwork.yourname.model.Serie.Episodes;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.HashMap;

/**
 * Class ListEpisodes
 *
 * @author                 Didier Youn <didier.youn@gmail.com>,
 *                         Marc Intha-amnouay <marc.inthaamnouay@gmail.com>,
 *                         Abdel-Latif Mabrouck <amatroud0@gmail.com>,
 *                         Antoine Renault <antoine.renault.mmi@gmail.com>.
 * @link                   https://github.com/Tinwork/YourName
 */
public class ListEpisodes extends Fetch
{
    private final RequestQueueManager queueManager;
    private Context ctx;
    private GsonGetManager<Episodes> episodes;
    private int retry;

    /**
     * Search Series
     *
     * @param context Context
     */
    public ListEpisodes(Context context) {
        this.ctx = context;
        this.queueManager = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
        this.retry = 0;
    }

    /**
     * Get episodes
     *
     * @param payload HashMap
     * @param callback GsonCallback
     */
    @Override
    public void get(HashMap<String, String> payload, final GsonCallback callback)
    {
        // Prepare token and headers
        String token = Utils.getSharedPreference(ctx, "yourname_token");
        HashMap<String, String> headers = Utils.makeHeaders(null, token);
        // Build API URL
        String[] foo = {payload.get("series_id")};
        String URL = Utils.buildPlaceholderUrl(Routes.PREFIX_SERIES, foo, Routes.SUFFIX_ROUTES_EPISODES_FROM_SERIES);
        // Call API
        episodes = new GsonGetManager<>(URL, Episodes.class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, episodes, ctx, retry, callback);
            retry++;
        }, false);
        // Add API Request to current queue
        queueManager.addToRequestQueue(episodes);
    }
}