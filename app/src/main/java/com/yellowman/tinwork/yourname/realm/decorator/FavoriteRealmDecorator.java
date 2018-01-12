package com.yellowman.tinwork.yourname.realm.decorator;

import android.content.Context;
import android.util.Log;

import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.user.GetFavorite;
import com.yellowman.tinwork.yourname.network.helper.ConnectivityHelper;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.realm.RealmResults;

/**
 * Looking at 2 constellations, hopefully one day these 2 constellation will be together
 * and forming one ~. Developers's night thinking...
 *
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
     * @param callback GsonCallback
     */
    public void get(GsonCallback callback) {
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
                    compare(serie, response);
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
     * @param a List<Series> Realm List<Series>
     * @param b List<Series> TVDB List<Series>
     * @return
     */
    private void compare(List<Series> a, List<String> b) {

        List<String> m = a.stream().map(serie -> serie.getId()).collect(Collectors.toList());
        for (String l: m) {
            m = m.stream().filter(s -> s != l).collect(Collectors.toList());
        }

        Log.d("M SIZE", String.valueOf(m.size()));
        Log.d("A SIZE", String.valueOf(a.size()));
        Log.d("B SIZE", String.valueOf(b.size()));

    }
}
