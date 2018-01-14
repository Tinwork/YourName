package com.yellowman.tinwork.yourname.network.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.yellowman.tinwork.yourname.model.Token;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.user.UserToken;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;

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
     * Volley Retry
     *
     * @param ctx Context
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
     * @param req Request
     */
    public void retry(Request<T> req) {
        HashMap<String, String> usrPayload = new HashMap<>();
        usrPayload.put("username", AppUtils.getSharedPreference(ctx, "username"));
        usrPayload.put("accountID", AppUtils.getSharedPreference(ctx, "accountID"));

        // /!\ Refresh token timestamp is not implemented yet. Use the /login route in the meantime
        // Though the refresh token is pretty strange... on the api side..
        userToken.get(usrPayload, new GsonCallback<Token>() {
            @Override
            public void onSuccess(Token response) {
                try {
                    if (req.getHeaders().containsKey("Authorization")) {
                        req.getHeaders().put("Authorization", "Bearer "+response.getToken());
                    }
                } catch (AuthFailureError e) {
                    Log.d("Error", "can not replace Headers");
                }

                Log.d("Debug", "Retry with new token");
                AppUtils.saveSharedPreference(ctx, "yourname_token", response.getToken());
                queue.addToRequestQueue(req);
            }

            @Override
            public void onError(String err) {

            }
        });
    }


}
