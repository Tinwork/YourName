package com.yellowman.tinwork.yourname.realm.decorator;

import android.content.Context;

import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.search.SearchSeries;
import com.yellowman.tinwork.yourname.network.helper.ConnectivityHelper;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;

import java.util.HashMap;
import java.util.Map;

import io.realm.Case;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Marc Intha-amnouay on 03/01/2018.
 * Created by Didier Youn on 03/01/2018.
 * Created by Abdel-Atif Mabrouck on 03/01/2018.
 * Created by Antoine Renault on 03/01/2018.
 */

public class SeriesRealmDecorator extends CommonManager {

    private Context ctx;
    private ConnectivityHelper conHelper;

    /**
     * Series Realm Manager
     *
     * @param ctx
     */
    public SeriesRealmDecorator(Context ctx) {
        this.ctx = ctx;
        this.conHelper = new ConnectivityHelper(ctx);
    }


    /**
     * Get
     *
     * @param query
     * @param callback
     */
    public void get(HashMap<String, String> query, GsonCallback callback) {
        // Update the datas
        if (conHelper.getConnectivityStatus()) {
            SearchSeries series = new SearchSeries(ctx);
            series.get(query, callback);

            return;
        }

        int idx = 0;
        RealmQuery<Series> realmQuery = getRealmInstance().where(Series.class);

        for (Map.Entry<String, String> pair: query.entrySet()) {
            if (pair.getKey() == "name") {
                realmQuery.contains("seriesName", pair.getValue(), Case.INSENSITIVE);
            }

            if (idx < query.size()) {
                realmQuery.and();
            }

            idx++;
        }

        RealmResults<Series> res = realmQuery.findAll();

        if (res == null) {
            callback.onError("404, Unavailable to retrieve data");
            return;
        }

        if (res.size() == 0) {
            callback.onError("404, Unavailable to retrieve data");
            return;
        }

        callback.onSuccess(res);
    }
}
