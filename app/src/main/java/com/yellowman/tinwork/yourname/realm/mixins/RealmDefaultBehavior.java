package com.yellowman.tinwork.yourname.realm.mixins;

import android.util.Log;


import com.yellowman.tinwork.yourname.entity.Actor;
import com.yellowman.tinwork.yourname.model.Series;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * ❤️ Happy new year !!! 🎊 L∞∞∞∞∞∞∞∞MM∞∞∞∞∞∞∞∞L 🎊 ❤️
 *
 * Created by Marc Intha-amnouay on 02/01/2018.
 * Created by Didier Youn on 02/01/2018.
 * Created by Abdel-Atif Mabrouck on 02/01/2018.
 * Created by Antoine Renault on 02/01/2018.
 */

public interface RealmDefaultBehavior<E extends RealmObject> {

    /**
     * Get Realm Instance
     *
     * @return Realm
     */
    public Realm getRealmInstance();


    /**
     * Close Realm
     *
     */
    default void closeRealm() {
        getRealmInstance().close();
    }

    /**
     * Get Entity By Id
     *
     * @param instance Class which extends of RealmObject
     * @param id String
     * @return RealmObject
     */
    default RealmObject getEntityById(Class<E> instance, String id) {
        RealmObject res = getRealmInstance()
                .where(instance)
                .equalTo("id", id)
                .findFirst();

        return res;
    }

    /**
     * Get Entity By Criterion
     *
     * @param instance Class instance exenting RealmObject
     * @param criterion Criteria for filtering
     * @param value Value of the criteria
     * @return RealmObject
     */
    default RealmObject getEntityByCriterion(Class<E> instance, String criterion, String value) {
        RealmObject res = getRealmInstance()
                .where(instance)
                .equalTo(criterion, value)
                .findFirst();

        return res;
    }

    /**
     * GEt Entities By Single Criterion
     *
     * @param instance Class which extends of RealmObject
     * @param criterion String
     * @param value String
     * @return RealmResults
     */
    default RealmResults<E> getEntitiesBySingleCriterion(Class<E> instance, String criterion, String value) {
        RealmResults<E> res = getRealmInstance()
                .where(instance)
                .equalTo(criterion, value)
                .findAll();

        return res;
    }

    /**
     * Commit Created Entity
     *
     * @param instance Object extending from RealmObject
     */
    default void commitCreatedEntity(E instance) {
        getRealmInstance().executeTransactionAsync(realm -> realm.insertOrUpdate(instance),
           new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d("Debug", "Data is saved on realm");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d("Debug", "Data has not been saved");
            }
        });
    }

    /**
     * Commit Multiple Entities
     *
     * @param data List of Object extending from RealmObject
     */
    default void commitMultipleEntities(List<E> data) {
        getRealmInstance().executeTransactionAsync(realm -> {
            realm.insertOrUpdate(data);
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d("Debug", "Data List is saved on realm");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.println(Log.WARN, "Warn", error.getMessage());
                Log.println(Log.ERROR, "Error", "Data List has not been saved");
            }
        });
    }

    /**
     * Sort Entities By Criterion
     *
     * @param instance Class which extends of RealmObject
     * @param criterion String
     * @param mode Sort mode (RealmSort)
     * @param value int
     * @return RealmResults
     */
    default RealmResults<E> sortEntitiesByCriterion(Class<E> instance, String criterion, Sort mode, int value) {
        RealmResults<E> data = getRealmInstance()
                .where(instance)
                .greaterThan(criterion, value)
                .findAll();

        data.sort(criterion, mode);

        return data;
    }
}
