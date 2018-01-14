package com.yellowman.tinwork.yourname.network.api.user;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.User;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonPutManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 27/12/2017.
 * Created by Didier Youn on 27/12/2017.
 * Created by Abdel-Atif Mabrouck on 27/12/2017.
 * Created by Antoine Renault on 27/12/2017.
 */

public class AddFavorites extends Fetch {

    private final RequestQueueManager queueManager;
    private Context ctx;
    private GsonPutManager<User> favorites;
    private int retry;

    /**
     * Search Series
     *
     * @param context Context
     */
    public AddFavorites(Context context) {
        this.ctx = context;
        this.queueManager = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
        this.retry = 0;
    }

    /**
     * Get (should had make a set method)
     *
     * @param payload HashMap
     * @param callback GsonCallback
     */
    public void set(HashMap<String, String> payload, final GsonCallback callback) {

        String token = AppUtils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = AppUtils.makeHeaders(null, token);
        // Bind the GET request params

        String[] foo = {payload.get("series_id")};
        String URL = AppUtils.buildPlaceholderUrl(Routes.FAVORITES, foo, null);

        favorites = new GsonPutManager<User>(URL, User.class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, favorites, ctx, retry, callback);
            retry++;
        }, true);

        queueManager.addToRequestQueue(favorites);
    }

    /**
     * Get
     *
     * /!\ Not used in this context
     * @param payload payload
     * @param callback callback
     */
    @Override
    public void get(HashMap<String, String> payload, GsonCallback callback) {}
}
