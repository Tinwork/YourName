package com.yellowman.tinwork.yourname.realm.decorator;

import android.content.Context;
import android.util.Log;

import com.yellowman.tinwork.yourname.entity.User;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.user.AddFavorites;
import com.yellowman.tinwork.yourname.network.api.user.DeleteFavoriteSerie;
import com.yellowman.tinwork.yourname.network.api.series.SingleSerie;
import com.yellowman.tinwork.yourname.network.api.user.GetFavorite;
import com.yellowman.tinwork.yourname.network.helper.ConnectivityHelper;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
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
    private final GsonCallback<User> deleteCallback = new GsonCallback<User>() {
        @Override
        public void onSuccess(User response) {
            Log.d("Debug", "a serie has been removed");
        }

        @Override
        public void onError(String err) {
            Log.d("ERROR", "a serie has fail to be remove");
        }
    };

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
     * Remove Series By Id
     *
     * @param id series id
     */
    public void removeSeriesById(String criterion, String id) {
        getRealmInstance().executeTransactionAsync((final Realm realm) -> {
            RealmResults<Series> data = realm.where(Series.class)
                    .equalTo(criterion, id)
                    .findAll();

            data.deleteAllFromRealm();
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.println(Log.WARN, "Yourname::Realm", "A serie has been deleted");
                // Call the remove services here
                HashMap<String, String> payload = new HashMap<>();
                payload.put("series_id", id);
                DeleteFavoriteSerie del = new DeleteFavoriteSerie(ctx);
                del.get(payload, deleteCallback);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.println(Log.WARN, "Yourname::Realm", "A serie has not been delete reason: "+error.getMessage());
            }
        });
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
                    response.setSaved(true);
                    stack.add(response);

                    if (stack.size() >= updates.size()) {
                        dispatchUpdate(stack);
                    }
                }

                @Override
                public void onError(String err) {
                    // in case of an error it's better to dispatch current series
                    callback.onSuccess(series);
                }
            });
        }
    }

    /**
     * Set Serie As Favorite
     *
     * @param seriesToSave Series
     */
    public void setSerieAsFavorite(Series seriesToSave) {
        // update the realm object using transaction
        getRealmInstance().executeTransactionAsync(realm -> {
            RealmObject res = this.getEntityById(Series.class, seriesToSave.getId());

            if (res == null) {
                seriesToSave.setFavorite(true);
                realm.insertOrUpdate(seriesToSave);
            } else {
                Series serie = (Series) res;
                serie.setFavorite(true);
                serie.setSaved(true);
                realm.insertOrUpdate(serie);
            }
        }, () -> {
            if (!conHelper.getConnectivityStatus()) {
                setUnsavedSeriesStatus(seriesToSave);
                return;
            }

            // Save the favorite in tvdb
            HashMap<String, String> payload = new HashMap<>();
            payload.put("series_id", seriesToSave.getId());

            AddFavorites favoritesReq = new AddFavorites(ctx);
            favoritesReq.set(payload, new GsonCallback<User>() {
                @Override
                public void onSuccess(User response) {
                    Log.d("Info", "a serie has been set as a favorite");
                }

                @Override
                public void onError(String err) {
                    // Assuming that the network is slow
                    setUnsavedSeriesStatus(seriesToSave);
                }
            });

        }, (Throwable error) -> {
            Log.println(Log.ERROR, "Realm::Error", error.getMessage());
        });
    }

    /**
     * Get Unsaved Series Stack
     *
     * @return List<Series>
     */
    public List<Series> getUnsavedSeriesStack() {
        HashMap<String, Boolean> filterPaylaod = new HashMap<>();
        filterPaylaod.put("favorite", true);
        filterPaylaod.put("saved", false);
        RealmResults<Series> series = this.getEntitiesByMultipleCriterion(Series.class, filterPaylaod);

        if (series == null) {
            return null;
        }

        // Convert the realm object to a List<Series>
        List<Series> seriesToUpdate = getRealmInstance().copyFromRealm(series);

        return seriesToUpdate;
    }

    /**
     * Set Unsaved Series Status
     *
     * @param serie Series
     */
    private void setUnsavedSeriesStatus(Series serie) {
        getRealmInstance().executeTransactionAsync(realm -> {
            RealmObject res = this. getEntityById(Series.class, serie.getId());
            Series realmSerie = (Series) res;

            realmSerie.setSaved(false);
            realm.insertOrUpdate(realmSerie);
        }, () -> {
            Log.println(Log.INFO, "Realm::Set", "Set series to not saved status");
        }, (Throwable error) -> {
            Log.println(Log.ERROR, "Realm::Error", error.getMessage());
        });
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
