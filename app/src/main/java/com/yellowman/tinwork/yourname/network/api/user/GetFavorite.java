package com.yellowman.tinwork.yourname.network.api.user;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.User;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Marc Intha-amnouay on 10/01/2018.
 * Created by Didier Youn on 10/01/2018.
 * Created by Abdel-Atif Mabrouck on 10/01/2018.
 * Created by Antoine Renault on 10/01/2018.
 */

public class GetFavorite extends Fetch {

    private Context ctx;
    private RequestQueueManager requestQueueManager;
    private int retry;
    private GsonGetManager<User> request;

    /**
     * Get Favorite::Constructor
     *
     * @param ctx
     */
    public GetFavorite(Context ctx) {
        this.ctx   = ctx;
        this.retry = 0;
        this.requestQueueManager = RequestQueueManager.getInstance(ctx);
    }

    /**
     * Get
     *
     * @param payload null
     * @param callback GsonCallback<List<User>
     */
    @Override
    public void get(HashMap<String, String> payload, GsonCallback callback) {
        String token = AppUtils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = AppUtils.makeHeaders(null, token);
        // Build URL
        String URL = Routes.FAVORITES_FULL;

        // Make the request
        request = new GsonGetManager<>(URL, User.class, headers, response -> {
            List<String> favorites = response.getFavorites();
            callback.onSuccess(favorites);
        }, error -> {
            this.handleVolleyError(error, request, ctx, retry, callback);
            retry++;
        }, true);

        requestQueueManager.addToRequestQueue(request);
    }
}
