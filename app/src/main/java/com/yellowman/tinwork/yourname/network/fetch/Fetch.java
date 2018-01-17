package com.yellowman.tinwork.yourname.network.fetch;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.helper.VolleyErrorHelper;
import com.yellowman.tinwork.yourname.network.helper.VolleyRetry;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 25/11/2017.
 * Created by Didier Youn on 25/11/2017.
 * Created by Abdel-Atif Mabrouck on 25/11/2017.
 * Created by Antoine Renault on 25/11/2017.
 */

public abstract class Fetch {

    /**
     * Handle Volley Error
     * @param error VolleyError
     * @param req <T extends Request>
     * @param ctx Context
     * @param retry int
     * @param callback GsonCallback
     */
    public static void handleVolleyError(VolleyError error, Request req, Context ctx, int retry, final GsonCallback callback) {
        HashMap<String, String> errorPayload = VolleyErrorHelper.getNetworkErrorData(error);

        if (errorPayload.containsKey("code") && retry < 1) {
            if (new Integer(errorPayload.get("code")) == 401) {
                VolleyRetry re = new VolleyRetry(ctx);
                re.retry(req);
            } else {
                callback.onError(errorPayload.get("code"));
            }
        } else if (!VolleyErrorHelper.isBasicError(error) && errorPayload.get("message") != null) {
            // Log the error
            callback.onError(errorPayload.get("message"));
        } else {
            // Log the error with basic VolleyError code
            callback.onError("404, an unknown error occured");
        }
    }

    /**
     * Get
     *
     * @param payload payload
     * @param callback callback
     */
    public abstract void get(HashMap<String, String> payload, final GsonCallback callback);
}
