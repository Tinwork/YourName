package com.yellowman.tinwork.yourname.network.api.user;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.Rating;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 23/01/2018.
 * Created by Didier Youn on 23/01/2018.
 * Created by Abdel-Atif Mabrouck on 23/01/2018.
 * Created by Antoine Renault on 23/01/2018.
 */

public class UserRatingServices extends Fetch {

    private RequestQueueManager queueManager;
    private GsonGetManager<Rating[]> rating;
    private int retry;
    private Context ctx;

    /**
     *
     * @param ctx Context
     */
    public UserRatingServices(Context ctx) {
        this.ctx = ctx;
        this.queueManager = RequestQueueManager.getInstance(ctx);
        this.retry = 0;
    }

    /**
     *
     *
     * @param payload payload
     * @param callback callback
     */
    @Override
    public void get(HashMap<String, String> payload, GsonCallback callback) {

        String token = AppUtils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = AppUtils.makeHeaders(null, token);

        rating = new GsonGetManager<>(Routes.RATING_SERIES, Rating[].class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, rating, ctx, retry, callback);
            retry++;
        }, true);

        queueManager.addToRequestQueue(rating);
    }
}
