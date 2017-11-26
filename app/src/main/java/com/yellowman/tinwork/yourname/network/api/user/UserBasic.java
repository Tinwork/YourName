package com.yellowman.tinwork.yourname.network.api.user;

import android.content.Context;

import com.yellowman.tinwork.yourname.model.User.User;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 25/11/2017.
 * Created by Didier Youn on 25/11/2017.
 * Created by Abdel-Atif Mabrouck on 25/11/2017.
 * Created by Antoine Renault on 25/11/2017.
 */

public class UserBasic extends Fetch {

    private Context ctx;
    private RequestQueueManager queue;
    private GsonGetManager<User> userRequest;
    private int retry;

    /**
     * UserBasic Constructor
     * @param ctx
     */
    public UserBasic(Context ctx) {
        this.ctx = ctx;
        this.queue = RequestQueueManager.getInstance(ctx.getApplicationContext());
        this.retry = 0;
    }

    /**
     * Get
     * @param payload
     * @param callback
     */
    @Override
    public void get(HashMap<String, String> payload, final GsonCallback callback) {
        // Retrieve the Token
        String token = Utils.getSharedPreference(ctx, "yourname_token");

        // Make the Header for the request
        HashMap<String, String> headers = Utils.makeHeaders(null, token);

        // Make the actual request
        userRequest = new GsonGetManager<User>(Routes.USER, User.class, headers ,response -> {
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, userRequest, ctx, retry, callback);
            retry++;
        });
    }

}
