package com.yellowman.tinwork.yourname.network.helper;

import android.content.Context;

import com.android.volley.Request;
import com.yellowman.tinwork.yourname.model.Token;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.user.UserToken;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.Utils;

/**
 * Created by Marc Intha-amnouay on 23/11/2017.
 * Created by Didier Youn on 23/11/2017.
 * Created by Abdel-Latif Mabrouck on 23/11/2017.
 * Created by Antoine Renault on 23/11/2017.
 */

public class VolleyRetry<T> {

    private final Context ctx;
    private final UserToken userToken;
    private final RequestQueueManager queue;

    /**
     * Volley Retry::Class
     * @param ctx
     */
    public VolleyRetry(Context ctx) {
        this.userToken = new UserToken(ctx);
        this.ctx  = ctx;
        this.queue = RequestQueueManager.getInstance(ctx.getApplicationContext());
    }

    /**
     * Retry
     *      We retry once the request in case IF the Token is expired
     *      This allow us to make sure that the request has failed
     * @param req
     */
    public void retry(Request<T> req) {
        String token = Utils.getSharedPreference(ctx, "yourname_token");
        // Call the refresh token
        userToken.refreshToken(token, new GsonCallback<Token>() {
            @Override
            public void onSuccess(Token response) {
                Utils.saveSharedPreference(ctx, "yourname_token", response.getToken());
                queue.addToRequestQueue(req);
            }
        });
    }
}
