package com.yellowman.tinwork.yourname.network.api.series;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.IdSeries;
import com.yellowman.tinwork.yourname.model.Serie.Favorites;
import com.yellowman.tinwork.yourname.model.Serie.SerieWrapper;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.GsonPutManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.HashMap;

/**
 * Created by abdel-latifmabrouck on 19/12/2017.
 */

public class AddFavorites extends Fetch {

    private final RequestQueueManager queueManager;
    private Context ctx;
    private GsonPutManager<IdSeries[]> favorites;
    private int retry;

    /**
     * Search Series
     *
     * @param context
     */
    public AddFavorites(Context context) {
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
        String URL = Utils.buildPlaceholderUrl(Routes.FAVORITES, foo, null);

        favorites = new GsonPutManager<IdSeries[]>(URL, IdSeries[].class, headers, response -> {
            System.out.println("test 1 "+response);
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, favorites, ctx, retry, callback);
            System.out.println("test 2 "+token);
            System.out.println("test 2 "+favorites.toString());
            System.out.println("test 2 "+ctx.toString());
            retry++;
        }, true);

        queueManager.addToRequestQueue(favorites);
    }
}
