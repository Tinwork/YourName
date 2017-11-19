package com.yellowman.tinwork.yourname.network.fetch;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.yellowman.tinwork.yourname.network.entity.Token;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 18/11/2017.
 * Created by Didier Youn on 18/11/2017.
 * Created by Abdel-Latif Mabrouck on 18/11/2017.
 * Created by Antoine Renault on 18/11/2017.
 */

public class Fetch {

    private RequestQueueManager queueManager;
    private final GsonBuilder gsonBuilder = new GsonBuilder();
    private Gson gson;

    /**
     * Fetch::Constructor
     *
     * @param ctx
     */
    public Fetch(Context ctx) {
        queueManager = RequestQueueManager.getInstance(ctx.getApplicationContext());
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    /**
     * Post
     * /!\ Just for test purposes
     *
     * @param payload
     */
    public void Post(HashMap<String, String> payload) {


        // Parse the payload
        String URL = payload.get("route");
        String apiKey = payload.get("apiKey");
        String username = payload.get("username");
        String userKey = payload.get("userKey");

        JsonObject json = new JsonObject();
        json.addProperty("apikey", apiKey);
        json.addProperty("userkey", userKey);
        json.addProperty("username", username);


        // create a gson post request
        GsonPostManager<Token> post = new GsonPostManager<>(URL, json.toString(), Token.class, new Response.Listener<Token>() {
            @Override
            public void onResponse(Token response) {
                System.out.println(response.getToken());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queueManager.addToRequestQueue(post);
    }
}

