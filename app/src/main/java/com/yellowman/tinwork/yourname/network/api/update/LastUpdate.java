package com.yellowman.tinwork.yourname.network.api.update;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.Update;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.api.series.SingleSerie;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marc Intha-amnouay on 04/01/2018.
 * Created by Didier Youn on 04/01/2018.
 * Created by Abdel-Atif Mabrouck on 04/01/2018.
 * Created by Antoine Renault on 04/01/2018.
 */

public class LastUpdate extends Fetch {

    private GsonCallback UICallback;
    private Context ctx;
    private RequestQueueManager requestQueueManager;
    private GsonGetManager<Update[]> lastUpdated;
    private int retry;
    private List<Series> seriesList;

    protected int ITEM_SIZE = 10;
    protected Thread loadLastEpisodes;

    /**
     * Last Update
     *
     * @param ctx
     */
    public LastUpdate(Context ctx) {
        this.ctx   = ctx;
        this.retry = 0;
        this.seriesList = new ArrayList<>();
        this.requestQueueManager = RequestQueueManager.getInstance(ctx);
    }

    /**
     * Get
     *
     * @param payload
     * @param callback
     */
    @Override
    public void get(HashMap<String, String> payload, GsonCallback callback) {
        // Set the callback
        this.UICallback = callback;
        // Get the token
        String token = Utils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = Utils.makeHeaders(null, token);
        // Bind the GET request params
        String URL = Utils.buildGetUrl(Routes.UPDATE_SERIES, payload);

        // make the request toward the ids
        lastUpdated = new GsonGetManager<>(URL, Update[].class, headers, response -> {
            if (response == null) {
                callback.onError("Update list is empty");
                return;
            }

            // In case we have the list of update we might want to get the series from it
            populateSeries(Arrays.asList(response));
        }, error -> {
            this.handleVolleyError(error, lastUpdated, ctx, retry, callback);
            retry++;
        }, true);
    }

    /**
     * Get Series From List Series
     *
     * /!\ Caution this is a recursive Loop which does async request
     * which has been transformed into a Sync request method
     * Each request is add when the previous one is fullfill.
     * We want to avoid multiple return of success or error
     *
     * @param updates
     * @param idx
     */
    private void getSeriesFromListSeries(List<Update> updates, int idx) {
        final int ivx = idx + 1;
        HashMap<String, String> payload = new HashMap<>();
        payload.put("series_id", updates.get(idx).getId());

        SingleSerie series = new SingleSerie(ctx);
        series.get(payload, new GsonCallback() {
            @Override
            public void onSuccess(Object response) {
                if (idx < ITEM_SIZE && idx < updates.size()) {
                    getSeriesFromListSeries(updates, ivx);
                } else {
                    UICallback.onSuccess(seriesList);
                    loadLastEpisodes.interrupt();
                }
            }

            @Override
            public void onError(String err) {
                if (seriesList.size() != 0) {
                    UICallback.onSuccess(seriesList);
                } else {
                    UICallback.onError(err);
                }

                loadLastEpisodes.interrupt();
            }
        });

    }

    /**
     * Populate Series
     *
     * @param updates
     */
    private void populateSeries(List<Update> updates) {
        this.loadLastEpisodes = new Thread(new Runnable() {
            @Override
            public void run() {
                getSeriesFromListSeries(updates, 0);
            }
        });
    }
}
