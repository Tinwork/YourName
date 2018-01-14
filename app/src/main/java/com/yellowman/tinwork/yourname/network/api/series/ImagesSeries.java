package com.yellowman.tinwork.yourname.network.api.series;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.Image;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Marc Intha-amnouay on 26/12/2017.
 * Created by Didier Youn on 26/12/2017.
 * Created by Abdel-Atif Mabrouck on 26/12/2017.
 * Created by Antoine Renault on 26/12/2017.
 */

public class ImagesSeries extends Fetch {

    private Context ctx;
    private final RequestQueueManager queueManager;
    private GsonGetManager<Image[]> imgRequest;
    private List<Image> img;
    private int retry;

    /**
     * Image Series::Constructor
     *
     * @param ctx Context
     */
    public ImagesSeries(Context ctx) {
        this.ctx = ctx;
        this.queueManager = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
        this.retry = 0;
    }

    /**
     * Get
     *
     * @param payload HashMap
     * @param callback GsonCallback
     */
    @Override
    public void get(HashMap<String, String> payload, GsonCallback callback) {
        String token = AppUtils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = AppUtils.makeHeaders(null, token);
        // Bind the GET request params
        // test purposes
        String[] placeholder = {payload.get("series_id")};
        String URL = AppUtils.buildPlaceholderUrl(Routes.PREFIX_SERIES, placeholder, "images/query?keyType=poster");

        imgRequest = new GsonGetManager<>(URL, Image[].class, headers, response -> {
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, imgRequest, ctx, retry, callback);
            retry++;
        }, true);


        queueManager.addToRequestQueue(imgRequest);
    }
}
