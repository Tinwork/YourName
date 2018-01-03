package com.yellowman.tinwork.yourname.realm.manager;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.Actor;
import com.yellowman.tinwork.yourname.model.Series;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.series.ListActors;
import com.yellowman.tinwork.yourname.network.helper.ConnectivityHelper;

import java.util.HashMap;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by Marc Intha-amnouay on 03/01/2018.
 * Created by Didier Youn on 03/01/2018.
 * Created by Abdel-Atif Mabrouck on 03/01/2018.
 * Created by Antoine Renault on 03/01/2018.
 */

public class ActorRealmManager extends CommonManager {

    private Context ctx;
    private ConnectivityHelper conHelper;

    /**
     * Actor Realm Manager
     *
     * @param ctx
     */
    public ActorRealmManager(Context ctx) {
        this.ctx = ctx;
        this.conHelper = new ConnectivityHelper(ctx);
    }

    /**
     * Get
     *
     * @param serie_id
     * @param callback
     */
    public void get(String serie_id, GsonCallback callback) {
        if (conHelper.getConnectivityStatus()) {
            HashMap<String, String> payload = new HashMap<>();
            payload.put("series_id", serie_id);
            ListActors actors = new ListActors(ctx);

            actors.get(payload, callback);
            return;
        }

        // Request the actors with realm
        RealmResults<Series> serie = this.getEntitiesBySingleCriterion(Series.class, "id", serie_id);

        if (serie == null) {
            callback.onError("Actors has not been retrieve for this series");
            return;
        } else if (serie.size() == 0) {
            callback.onError("No actors has been saved");
            return;
        }

        if (serie.get(0).getActors().size() == 0) {
            callback.onError("No actors has been saved");
            return;
        }

        List<Actor> actors = serie.get(0).getActors();
        callback.onSuccess(actors);
    }
}
