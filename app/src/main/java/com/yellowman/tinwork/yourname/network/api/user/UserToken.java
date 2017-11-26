package com.yellowman.tinwork.yourname.network.api.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.yellowman.tinwork.yourname.home.HomeActivity;
import com.yellowman.tinwork.yourname.model.Token;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.GsonPostManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.network.helper.VolleyErrorHelper;
import com.yellowman.tinwork.yourname.utils.Utils;


import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 18/11/2017.
 * Created by Didier Youn on 18/11/2017.
 * Created by Abdel-Latif Mabrouck on 18/11/2017.
 * Created by Antoine Renault on 18/11/2017.
 */

public class UserToken extends Fetch {
    // Information regarding the APIs secret
    private final String API_KEY  = "4EA2639295D9AFEB";
    private final Context ctx;
    private final RequestQueueManager queueManager;

    // Retry
    private int retry;
    private GsonPostManager<Token> post;
    private GsonGetManager<Token> reqGet;


    /**
     * UserToken::Constructor
     *
     * @param ctx
     */
    public UserToken(Context ctx) {
        queueManager  = RequestQueueManager.getInstance(ctx.getApplicationContext());
        this.ctx = ctx;
        this.retry = 0;
    }

    /**
     * Get
     *
     * @param callback
     */
    @Override
    public void get(HashMap<String, String> payload, final GsonCallback callback) {
        // Construct a JSON Object to pass to the /login API
        JsonObject credentials = new JsonObject();
        credentials.addProperty("apiKey", API_KEY);

        // Ok nested if is not good
        if (payload != null) {
            if (payload.containsKey("username") && payload.containsKey("account_id")) {
                credentials.addProperty("username", payload.get("username"));
                credentials.addProperty("userkey", payload.get("account_id"));
            }
        }

        post = new GsonPostManager<>(Routes.LOGIN, credentials.toString(), Token.class, token -> {
            // Call the callback
            callback.onSuccess(token);
        }, error -> {
            this.handleVolleyError(error, post, ctx, retry, callback);
            retry++;
        });

        // Add the request to the queue
        queueManager.addToRequestQueue(post);
    }

    /**
     * Refresh Token
     * @param token
     */
    public void refreshToken(String token, final GsonCallback callback) {
        HashMap<String, String> headers = Utils.makeHeaders(null, token);;

        reqGet = new GsonGetManager<>(Routes.REFRESH_TOKEN, Token.class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, reqGet, ctx, 1, callback);
        });

        queueManager.addToRequestQueue(reqGet);
    }
}

