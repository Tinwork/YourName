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
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.GsonPostManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;


import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 18/11/2017.
 * Created by Didier Youn on 18/11/2017.
 * Created by Abdel-Latif Mabrouck on 18/11/2017.
 * Created by Antoine Renault on 18/11/2017.
 */

public class UserToken {
    // Information regarding the APIs secret
    private final String API_KEY  = "4EA2639295D9AFEB";

    // Only use this when using the /user apis
    private final String USER_KEY = "<account_number>";
    private final String USR_NAME = "<username>";
    private final RequestQueueManager queueManager;

    /**
     * UserToken::Constructor
     *
     * @param ctx
     */
    public UserToken(Context ctx) {
        queueManager = RequestQueueManager.getInstance(ctx.getApplicationContext());
    }

    /**
     * Make Token
     *      Get the token or Refresh the token
     */
    public void makeToken(final GsonCallback callback) {
        // Construct a JSON Object to pass to the /login API
        JsonObject credentials = new JsonObject();
        credentials.addProperty("apiKey", API_KEY);

        GsonPostManager<Token> req = new GsonPostManager<>(Routes.LOGIN, credentials.toString(), Token.class, token -> {
            // Call the callback
            callback.onSuccess(token);
        }, error -> {
            // we should thrown an error here
        });

        // Add the request to the queue
        queueManager.addToRequestQueue(req);
    }

    /**
     * Refresh Token
     * @param token
     */
    public void refreshToken(String token, final GsonCallback callback) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("Authorisation", "Bearer "+token);

        GsonGetManager<Token> req = new GsonGetManager<>(Routes.REFRESH_TOKEN, Token.class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            // we should thrown an error
        });

        queueManager.addToRequestQueue(req);
    }
}

