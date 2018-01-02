package com.yellowman.tinwork.yourname.realm.mixins;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Marc Intha-amnouay on 02/01/2018.
 * Created by Didier Youn on 02/01/2018.
 * Created by Abdel-Atif Mabrouck on 02/01/2018.
 * Created by Antoine Renault on 02/01/2018.
 */

public interface RealmDefaultBehavior<E extends RealmObject> {

    /**
     * Get Realm Instance
     *
     * @return
     */
    public Realm getRealmInstance();

    /**
     * Close Realm
     *
     * @return
     */
    default void closeRealm() {
        getRealmInstance().close();
    }

    /**
     * Get Entities By Id
     *
     * @param instance
     * @param id
     * @return
     */
    default RealmResults<E> getEntitiesById(Class<E> instance, int id) {
        RealmResults<E> res = getRealmInstance()
                .where(instance)
                .equalTo("id", id)
                .findAll();

        return res;
    }

    /**
     * GEt Entities By Single Criterion
     *
     * @param instance
     * @param criterion
     * @param value
     * @return
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
     * @param instance
     * @return
     */
    default void commitCreatedEntity(E instance) {
        getRealmInstance().executeTransactionAsync(realm -> realm.copyToRealm(instance),
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
     * @param data
     */
    default void commitMultipleEntities(List<E> data) {

        getRealmInstance().executeTransactionAsync(realm -> {
            realm.insert(data);
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
     * @param instance
     * @param criterion
     * @param mode
     * @return
     */
    default RealmResults<E> sortEntitiesByCriterion(Class<E> instance, String criterion, Sort mode) {
        RealmResults<E> data = getRealmInstance().where(instance).findAll();
        data.sort(criterion, mode);

        return data;
    }
}
