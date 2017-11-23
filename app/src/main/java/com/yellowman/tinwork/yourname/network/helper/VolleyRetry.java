package com.yellowman.tinwork.yourname.network.helper;

import android.content.Context;

import com.android.volley.Request;
import com.yellowman.tinwork.yourname.model.Token;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.user.UserToken;
import com.yellowman.tinwork.yourname.utils.Utils;

/**
 * Created by Marc Intha-amnouay on 23/11/2017.
 * Created by Didier Youn on 23/11/2017.
 * Created by Abdel-Latif Mabrouck on 23/11/2017.
 * Created by Antoine Renault on 23/11/2017.
 */

public class VolleyRetry<T> {

    private final UserToken userToken;
    private final VolleyRetry self;

    public VolleyRetry(Context ctx) {
        this.userToken = new UserToken(ctx);
        this.self = this;
    }

    /**
     * @TODO should use the Observer pattern instead
     * @param req
     * @param token
     */
    public void retry(Request<T> req, String token) {
        // Call the refresh token
        userToken.refreshToken(token, new GsonCallback<Token>() {
            @Override
            public void onSuccess(Token response) {
                //VolleyRetry.super.retryRequest();
            }
        });
    }

    public void retryRequest(Request<T> req, Token token) {

    }
}
