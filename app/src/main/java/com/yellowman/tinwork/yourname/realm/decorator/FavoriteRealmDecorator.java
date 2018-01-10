package com.yellowman.tinwork.yourname.realm.decorator;

import android.content.Context;

import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.user.GetFavorite;
import com.yellowman.tinwork.yourname.network.helper.ConnectivityHelper;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
                    // @TODO compare collection
                    Collection<Series> seriesCollection = new ArrayList(response);
                    // here we merge the data of the request
                    seriesCollection.removeAll(serie);

                }

                @Override
                public void onError(String err) {
                    // handle error
                }
            });

            return;
        }

        callback.onSuccess(serie);
    }
}
