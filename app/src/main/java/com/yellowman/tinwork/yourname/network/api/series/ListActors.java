package com.yellowman.tinwork.yourname.network.api.series;

import android.content.Context;
import android.util.Log;

import com.yellowman.tinwork.yourname.entity.Actor;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.Arrays;
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
    private CommonManager realmManager;
    private int retry;


    /**
     * List Actor
     *
     * @param context
     */
    public ListActors(Context context) {
        this.ctx = context;
        this.queueManager = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
        this.realmManager = new CommonManager();
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
        String token   = AppUtils.getSharedPreference(ctx, "yourname_token");
        String seriesID = payload.get("series_id");
        // Headers
        HashMap<String, String> headers = AppUtils.makeHeaders(null, token);

        String[] foo = {seriesID};
        String URL = AppUtils.buildPlaceholderUrl(Routes.PREFIX_SERIES, foo, "actors");
        Log.d("Debug", "SERIE ID "+seriesID);
        actors = new GsonGetManager<>(URL, Actor[].class, headers, response -> {
            if (response.length == 0) {
                callback.onError("Actor is empty");
                return;
            }

            realmManager.setActors(response);
            realmManager.updateSeriesActor(seriesID, response);
            callback.onSuccess(Arrays.asList(response));
        }, error -> {
            this.handleVolleyError(error, actors, ctx, retry, callback);
            retry++;
        }, true);
        queueManager.addToRequestQueue(actors);
    }
}
