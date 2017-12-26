package com.yellowman.tinwork.yourname.network.api.series;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.Actor;
import com.yellowman.tinwork.yourname.model.Serie.Actors;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 11/12/2017.
 * Created by Didier Youn on 11/12/2017.
 * Created by Abdel-Latif Mabrouck on 11/12/2017.
 * Created by Antoine Renault on 11/12/2017.
 */

public class ListActors extends Fetch {

    private final RequestQueueManager queueManager;
    private Context ctx;
    private GsonGetManager<Actor[]> actors;
    private int retry;


    /**
     * List Actor
     *
     * @param context
     */
    public ListActors(Context context) {
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

       // buildPlaceholderUrl(AUTHOR, {"ID"}, "actors");
        String[] foo = {payload.get("series_id")};
        String URL = Utils.buildPlaceholderUrl(Routes.PREFIX_SERIES, foo, "actors");

        actors = new GsonGetManager<>(URL, Actor[].class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, actors, ctx, retry, callback);
            retry++;
        }, true);
        queueManager.addToRequestQueue(actors);
    }
}
