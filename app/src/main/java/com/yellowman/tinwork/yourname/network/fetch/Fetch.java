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
     * @param error
     */
    public static void handleVolleyError(VolleyError error, Request req, Context ctx, int retry) {
        HashMap<String, String> errorPayload = VolleyErrorHelper.getNetworkErrorData(error);

        if (new Integer(errorPayload.get("code")) == 401 && retry < 1) {
            VolleyRetry re = new VolleyRetry(ctx);
            re.retry(req);
        } else if (!VolleyErrorHelper.isBasicError(error) && errorPayload.get("message") != null) {
            // Log the error
            Log.d("Error", errorPayload.get("message"));
        } else {
            Log.d("Error", errorPayload.get("code"));
        }
    }

    /**
     * Get
     * @param payload
     * @param callback
     */
    public abstract void get(HashMap<String, String> payload, final GsonCallback callback);
}
