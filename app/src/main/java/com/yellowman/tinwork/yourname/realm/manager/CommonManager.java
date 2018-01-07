package com.yellowman.tinwork.yourname.realm.manager;

import android.util.Log;

import com.yellowman.tinwork.yourname.UIKit.errors.UIErrorManager;
import com.yellowman.tinwork.yourname.UIKit.helpers.Utils;
import com.yellowman.tinwork.yourname.entity.Actor;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.realm.mixins.RealmDefaultBehavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;


/**
 * Created by Marc Intha-amnouay on 02/01/2018.
 * Created by Didier Youn on 02/01/2018.
 * Created by Abdel-Atif Mabrouck on 02/01/2018.
 * Created by Antoine Renault on 02/01/2018.
 */

public class CommonManager<E> implements RealmDefaultBehavior {

    /**
     * Get Realm Instance
     *
     * @return
     */
    @Override
    public Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }

    /**
     * Update Series Misc
     *
     * @param serie
     * @param id
     */
    public void updateSeriesMisc(Series serie, String id) {
        getRealmInstance().executeTransactionAsync(realm -> {
            RealmObject realmObj = this.getEntitiesById(Series.class, id);

            if (realmObj == null) {
                Log.println(Log.WARN, "Realm", "Series cannot be update");
                return;
            }

            Series serieToUpdate = (Series) realmObj;

            // update fields
            serieToUpdate.setGenre(Utils.getArrayListFromRealm(serie.getGenre()));
            serieToUpdate.setSiteRating(serie.getSiteRating());


            // finally update the series
            realm.insertOrUpdate(serieToUpdate);
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                getRealmInstance().close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.println(Log.ERROR, "Realm Error", error.getMessage());
            }
        });
    }


    /**
     *
     * @param serie_id
     * @param actors
     */
    public void updateSeriesActor(String serie_id, Actor[] actors) {
        getRealmInstance().executeTransactionAsync(realm -> {
            RealmObject realmObj = this.getEntitiesById(Series.class, serie_id);

            if (realmObj == null) {
                Log.println(Log.WARN, "Realm", "Series Actors cannot be update");
                return;
            }

            Series serieToUpdate = (Series) realmObj;
            ArrayList<Actor> actorsList = new ArrayList<>();
            actorsList.addAll(Arrays.asList(actors));
            serieToUpdate.setActors(actorsList);

            realm.insertOrUpdate(serieToUpdate);
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                getRealmInstance().close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.println(Log.ERROR, "RealmSeriesActors", error.getMessage());
            }
        });
    }

    /**
     * Set Actors
     *
     * @param actors
     */
    public void setActors(Actor[] actors) {
        if (actors == null) {
            Log.d("Debug", "no actors exist for this series");
        }

        List<Actor> actorList = Arrays.asList(actors);
        this.commitMultipleEntities(actorList);
    }

    /**
     * Destroy All
     *     /!\ Caution this drop the database
     */
    public void destroyAll() {
        getRealmInstance().executeTransaction(realm -> {
            realm.deleteAll();
        });
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
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.println(Log.WARN, "Yourname::Realm", "A serie has not been delete reason: "+error.getMessage());
            }
        });
    }
}
