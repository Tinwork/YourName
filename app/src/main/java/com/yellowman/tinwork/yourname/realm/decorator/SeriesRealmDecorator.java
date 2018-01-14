package com.yellowman.tinwork.yourname.realm.decorator;

import android.content.Context;
import android.util.Log;

import com.yellowman.tinwork.yourname.entity.User;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.search.SearchSeries;
import com.yellowman.tinwork.yourname.network.api.update.LastUpdate;
import com.yellowman.tinwork.yourname.network.api.user.AddFavorites;
import com.yellowman.tinwork.yourname.network.helper.ConnectivityHelper;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Case;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

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
     * @param ctx Context
     */
    public SeriesRealmDecorator(Context ctx) {
        this.ctx = ctx;
        this.conHelper = new ConnectivityHelper(ctx);
    }


    /**
     * Get
     *
     * @param query HashMap of query
     * @param callback A UICallback
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

    /**
     * Get Latest Update Series
     *
     * @param payload Hashmap of payload
     * @param callback UICallback
     */
    public void getLatestUpdateSeries(HashMap<String, String> payload, GsonCallback callback) {

        if (conHelper.getConnectivityStatus()) {
            LastUpdate updateServices = new LastUpdate(ctx);
            updateServices.get(payload, callback);

            return;
        }

        // First try to retrieve the last films
        RealmResults<Series> realmSeries = this.sortEntitiesByCriterion(
                Series.class,
                "lastUpdated",
                Sort.DESCENDING,
                AppUtils.getYesterdayTimestamp()
        );

        if (realmSeries != null) {
            List<Series> listSeries = this.getRealmInstance().copyFromRealm(realmSeries);
            callback.onSuccess(listSeries);
        } else {
            callback.onError("Unable to retrieve last series");
        }
    }
}
