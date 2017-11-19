package com.yellowman.tinwork.yourname.network.fetch;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.entity.TokenEntity;


import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 18/11/2017.
 * Created by Didier Youn on 18/11/2017.
 * Created by Abdel-Latif Mabrouck on 18/11/2017.
 * Created by Antoine Renault on 18/11/2017.
 */

public class FetchToken {

    // Information regarding the APIs secret
    private final String API_KEY  = "4EA2639295D9AFEB";
    private final String USER_KEY = "103BDD2CB28301F3";
    private final String USR_NAME = "mintha";

    private static FetchToken fetch;
    private TokenEntity bearerToken;

    private RequestQueueManager queueManager;

    /**
     * FetchToken::Constructor
     *
     * @param ctx
     */
    public FetchToken(Context ctx) {
        queueManager = RequestQueueManager.getInstance(ctx.getApplicationContext());
    }

    /**
     * Get Instance
     *      Singleton of the Token manager
     *      We want the token available threw the whole app lifetime
     * @param ctx
     * @return
     */
    public static synchronized FetchToken getInstance(Context ctx) {
        if (fetch == null)
            return new FetchToken(ctx);

        return fetch;
    }

    /**
     * Make Token
     *      Get the token or Refresh the token
     */
    public void makeToken() {
        // Construct a JSON Object to pass to the /login API
        JsonObject credentials = new JsonObject();
        credentials.addProperty("apiKey", API_KEY);

        GsonPostManager<TokenEntity> req = new GsonPostManager<>(Routes.LOGIN, credentials.toString(), TokenEntity.class, new Response.Listener<TokenEntity>() {
            @Override
            public void onResponse(TokenEntity token) {
                bearerToken = token;
                System.out.println(bearerToken.getToken());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the queue
        queueManager.addToRequestQueue(req);
    }

    /**
     * Refresh TokenEntity
     * @param token
     */
    public void refreshToken(String token) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("Authorisation", "Bearer "+token);

        GsonGetManager<TokenEntity> req = new GsonGetManager<>(Routes.REFRESH_TOKEN, TokenEntity.class, headers, new Response.Listener<TokenEntity>() {
            @Override
            public void onResponse(TokenEntity response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queueManager.addToRequestQueue(req);
    }

    /**
     * Get Tokenm
     *
     * @return
     */
    public String getToken() {
        if (bearerToken.getToken() == null) {
            return "null";
        }

        return bearerToken.getToken();
    }
}

