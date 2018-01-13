package com.yellowman.tinwork.yourname.network.api.series;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.User;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonDeleteManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 10/01/2018.
 * Created by Didier Youn on 10/01/2018.
 * Created by Abdel-Atif Mabrouck on 10/01/2018.
 * Created by Antoine Renault on 10/01/2018.
 */

public class DeleteFavoriteSerie extends Fetch {

    private final RequestQueueManager queueManager;
    private Context ctx;
    private GsonDeleteManager<User> series;
    private int retry;

    /**
     * Delete Favorite Series::Constructor
     *
     * @param context Context
     */
    public DeleteFavoriteSerie(Context context) {
        this.ctx = context;
        this.queueManager = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
        this.retry = 0;
    }

    /**
     *
     *
     * @param payload HashMap
     * @param callback GsonCallback
     */
    @Override
    public void get(HashMap<String, String> payload, final GsonCallback callback) {
        String[] data = {payload.get("series_id")};

        String token = AppUtils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = AppUtils.makeHeaders(null, token);
        // Bind the GET request params
        String URL = AppUtils.buildPlaceholderUrl(Routes.FAVORITES, data , null);

        series = new GsonDeleteManager<>(URL, User.class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, series, ctx, retry, callback);
            retry++;
        }, true);

        queueManager.addToRequestQueue(series);
    }

}
