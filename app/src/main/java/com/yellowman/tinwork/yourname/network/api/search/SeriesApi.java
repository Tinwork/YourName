package com.yellowman.tinwork.yourname.network.api.search;

import android.content.Context;
import android.util.Log;

import com.yellowman.tinwork.yourname.model.Search;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.network.helper.VolleyErrorHelper;
import com.yellowman.tinwork.yourname.network.helper.VolleyRetry;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 19/11/2017.
 * Created by Didier Youn on 19/11/2017.
 * Created by Abdel-Latif Mabrouck on 19/11/2017.
 * Created by Antoine Renault on 19/11/2017.
 */

public class SeriesApi {

    private Context ctx;
    private final RequestQueueManager queueManager;
    private GsonGetManager<Search> series;
    private int retry;


    public SeriesApi(Context context) {
        this.ctx = context;
        this.queueManager = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
        this.retry = 0;
    }

    /**
     * Get Series
     *
     * @TODO test if the retry thresold is working in the next 24h...
     * @param payload
     * @param callback
     */
    public void getSeries(HashMap<String, String> payload, final GsonCallback callback) {
        String token = Utils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = Utils.makeHeaders(null, token);
        // Bind the GET request params
        String URL = Utils.buildGetUrl(Routes.SEARCH_SERIES, payload);

        series = new GsonGetManager<>(URL, Search.class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            HashMap<String, String> errorPayload = VolleyErrorHelper.getNetworkErrorData(error);
            Log.d("Error", errorPayload.get("code"));

            if (new Integer(errorPayload.get("code")) == 401 && this.retry < 1) {
                VolleyRetry re = new VolleyRetry(ctx);
                re.retry(series);
                retry = 1;
            }
            else if (!VolleyErrorHelper.isBasicError(error)) {
                // Log the error
                Log.d("Error", errorPayload.get("message"));
            }
        });

        queueManager.addToRequestQueue(series);
    }
}
