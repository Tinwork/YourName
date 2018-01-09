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
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * As someone said 'I always feel good !'. Then I should either do the same
 * Night thinking.
 *
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
    private List<Series> queue;

    protected int ITEM_SIZE = 10;
    protected Thread loadLastEpisodes;

    /**
     * Last Update
     *
     * @param ctx Context
     */
    public LastUpdate(Context ctx) {
        this.ctx   = ctx;
        this.retry = 0;
        this.queue = new ArrayList<>();
        this.requestQueueManager = RequestQueueManager.getInstance(ctx);
    }

    /**
     * Get
     *
     * @param payload Empty Hashmap !
     * @param callback GsonCallback use by main thread
     */
    @Override
    public void get(HashMap<String, String> payload, GsonCallback callback) {
        payload.put("fromTime", String.valueOf(AppUtils.getYesterdayTimestamp()));
        // Set the callback
        this.UICallback = callback;
        // Get the token
        String token = AppUtils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = AppUtils.makeHeaders(null, token);
        // Bind the GET request params
        String URL = AppUtils.buildGetUrl(Routes.UPDATE_SERIES, payload);

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

        requestQueueManager.addToRequestQueue(lastUpdated);
    }

    /**
     *
     * Get Series From List Series
     *
     * Did's idea for the queue system thanks ! better than recursive the datas
     * @param update Update entity
     */
    private void getSeriesFromListSeries(Update update) {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("series_id", update.getId());
        // just dummy data for a flag system
        payload.put("full", null);

        SingleSerie series = new SingleSerie(ctx);
        series.get(payload, new GsonCallback<Series>() {
            @Override
            public void onSuccess(Series response) {
                if (queue.size() < ITEM_SIZE) {
                    queue.add(response);
                } else {
                    parseRequestQueue();
                }
            }

            @Override
            public void onError(String err) {
                if (queue.size() < ITEM_SIZE) {
                    queue.add(null);
                } else {
                    parseRequestQueue();
                }
            }
        });

    }

    /**
     * Populate Series
     *
     * @param updates List of Update entities
     */
    private void populateSeries(List<Update> updates) {
        this.loadLastEpisodes = new Thread(new Runnable() {
            @Override
            public void run() {
                // Loop threw the datas
                for (int idx = 0; idx < 11; idx++) {
                    getSeriesFromListSeries(updates.get(idx));
                }
            }
        });

        loadLastEpisodes.start();
    }

    /**
     * Parse Request Queue
     *
     */
    private void parseRequestQueue() {
        List<Series> seriesList = new ArrayList<>();
        for (Series data: queue) {
            if (data != null) {
                seriesList.add(data);
            }
        }

        if (seriesList.size() == 0) {
            UICallback.onError("Can not retrieve last update Series");
            return;
        }

        UICallback.onSuccess(seriesList);
        loadLastEpisodes.interrupt();
    }
}
