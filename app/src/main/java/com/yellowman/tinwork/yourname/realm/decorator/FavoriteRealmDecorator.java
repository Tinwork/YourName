package com.yellowman.tinwork.yourname.realm.decorator;

import android.content.Context;
import android.util.Log;

import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.search.SearchSeries;
import com.yellowman.tinwork.yourname.network.api.series.SingleSerie;
import com.yellowman.tinwork.yourname.network.api.user.GetFavorite;
import com.yellowman.tinwork.yourname.network.helper.ConnectivityHelper;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import io.realm.RealmResults;

/**
 * This need a refactor
 *
 * Created by Marc Intha-amnouay on 10/01/2018.
 * Created by Didier Youn on 10/01/2018.
 * Created by Abdel-Atif Mabrouck on 10/01/2018.
 * Created by Antoine Renault on 10/01/2018.
 */

public class FavoriteRealmDecorator extends CommonManager {

    private Context ctx;
    private ConnectivityHelper conHelper;
    private List<Series> serie;
    private GsonCallback callback;

    /**
     * Favorite Realm Decorator::Constructor
     *
     * @param ctx
     */
    public FavoriteRealmDecorator(Context ctx) {
        this.ctx    = ctx;
        this.conHelper = new ConnectivityHelper(ctx);
        this.serie  = new ArrayList<>();
    }

    /**
     * Get
     *
     * @param cb GsonCallback
     */
    public void get(GsonCallback cb) {
        this.callback = cb;
        // Get the series from realm first then aggregate if needed..
        RealmResults<Series> realmSeries = this.getRealmInstance()
                .where(Series.class)
                .equalTo("favorite", true)
                .findAll();

        if (realmSeries.size() != 0) {
            serie = this.getRealmInstance().copyFromRealm(realmSeries);
        }

        if (conHelper.getConnectivityStatus()) {
            GetFavorite favorite = new GetFavorite(ctx);
            favorite.get(null, new GsonCallback<List<String>>() {
                @Override
                public void onSuccess(List<String> response) {
                    List<String> v = compare(serie, response);

                    if (v.size() == 0) {
                        callback.onSuccess(serie);
                        return;
                    }
                    // make a bulk of series request
                    bulkSeriesRequest(v);
                }

                @Override
                public void onError(String err) {
                    // handle error
                    callback.onError(err);
                }
            });

            return;
        }

        callback.onSuccess(serie);
    }

    /**
     * Compare
     *
     * @param m List<Series> Realm List<Series>
     * @param i List<String> TVDB List<String>
     * @return List<String>
     */
    private List<String> compare(List<Series> m, List<String> i) {
        // Create a copy of RealmSeries
        List<String> update = new ArrayList<>();

        for (String tvdbIndexes: i) {
            Boolean exist = false;
            int indexes = 0;

            for (int idx = 0; idx < m.size(); idx++) {
                if (m.get(idx).getId().equals(tvdbIndexes)) {
                    exist = true;
                    indexes = idx;
                }
            }

            if (!exist) {
                update.add(tvdbIndexes);
            }
        }

        return update;
    }

    /**
     * Bulk Series Request
     *
     * @param updates List<String>
     */
    private void bulkSeriesRequest(List<String> updates) {
        List<Series> stack = new ArrayList<>();
        HashMap<String, String> payload = new HashMap<>();
        payload.put("notsave", null);

        for (String update: updates) {
            payload.put("series_id", update);
            SingleSerie series = new SingleSerie(ctx);
            series.getWithoutRealm(payload, new GsonCallback<Series>() {
                @Override
                public void onSuccess(Series response) {
                    response.setFavorite(true);
                    stack.add(response);

                    if (stack.size() >= updates.size()) {
                        dispatchUpdate(stack);
                    }
                }

                @Override
                public void onError(String err) {
                    callback.onError(err);
                }
            });
        }
    }

    /**
     * Dispatch Update
     *
     * @param stack List<Series>
     */
    private void dispatchUpdate(List<Series> stack) {
        this.commitMultipleEntities(stack);
        // send the data to the callback
        // Merge series and stack data
        List<Series> merge = new ArrayList<>();
        merge.addAll(serie);
        merge.addAll(stack);

        callback.onSuccess(merge);
    }
}
