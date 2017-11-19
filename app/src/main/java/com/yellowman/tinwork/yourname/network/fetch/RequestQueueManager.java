package com.yellowman.tinwork.yourname.network.fetch;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by lookitsmarc on 17/11/2017.
 * Created by Didier Youn on 17/11/2017.
 * Created by Abdel-Latif Mabrouck on 17/11/2017.
 * Created by Antoine Renault on 17/11/2017.
 */

public class RequestQueueManager {

    private static RequestQueueManager mInstance;
    private RequestQueue rQueue;
    private static Context ctx;

    /**
     * Request Queue Manager
     * @param appCtx
     */
    private RequestQueueManager(Context appCtx) {
        ctx = appCtx;
        // Get the queue
        rQueue = getRequestQueue();
    }

    /**
     * Request Queue Manager
     * @param ctx
     * @return
     */
    public static synchronized RequestQueueManager getInstance(Context ctx) {
        if (mInstance == null)
            return new RequestQueueManager(ctx);

        return mInstance;
    }

    /**
     * Get Request Queue
     * @return
     */
    public RequestQueue getRequestQueue() {
        // In case the Queue is null we create a new Queue with the Application context
        if (rQueue == null)
            rQueue = Volley.newRequestQueue(ctx.getApplicationContext());

        return rQueue;
    }

    /**
     * Add To Request Queue
     * @param req
     * @param <T> any
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
