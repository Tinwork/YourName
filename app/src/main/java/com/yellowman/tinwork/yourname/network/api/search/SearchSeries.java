package com.yellowman.tinwork.yourname.network.api.search;

import android.content.Context;

import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marc Intha-amnouay on 19/11/2017.
 * Created by Didier Youn on 19/11/2017.
 * Created by Abdel-Latif Mabrouck on 19/11/2017.
 * Created by Antoine Renault on 19/11/2017.
 */

public class SearchSeries extends Fetch {

    private final RequestQueueManager queueManager;
    private CommonManager realmManager;
    private Context ctx;
    private GsonGetManager<Series[]> series;
    private int retry;


    /**
     * Search Series
     *
     * @param context
     */
    public SearchSeries(Context context) {
        this.ctx = context;
        this.queueManager = RequestQueueManager.getInstance(this.ctx.getApplicationContext());
        this.realmManager = new CommonManager();
        this.retry = 0;
    }

    /**
     * Get
     *
     * @param payload
     * @param callback
     */
    @Override
    public void get(HashMap<String, String> payload, final GsonCallback callback) {
        Boolean notSave = payload.containsKey("notsave");
        String token = AppUtils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = AppUtils.makeHeaders(null, token);
        // Bind the GET request params
        if (notSave) {
            payload.remove("notsave");
        }

        String URL = AppUtils.buildGetUrl(Routes.SEARCH_SERIES, payload);

        series = new GsonGetManager<>(URL, Series[].class, headers, response -> {
            if (response.length == 0) {
                return;
            }

            List<Series> seriesList = Arrays.asList(response);

            if (!notSave) {
                realmManager.commitMultipleEntities(seriesList);
                realmManager.closeRealm();
            }

            callback.onSuccess(seriesList);
        }, error -> {
            this.handleVolleyError(error, series, ctx, retry, callback);
            retry++;
        }, true);

        queueManager.addToRequestQueue(series);
    }
}
