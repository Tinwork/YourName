package com.yellowman.tinwork.yourname.realm.manager;

import android.util.Log;

import com.yellowman.tinwork.yourname.entity.User;
import com.yellowman.tinwork.yourname.realm.mixins.RealmDefaultBehavior;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Marc Intha-amnouay on 02/01/2018.
 * Created by Didier Youn on 02/01/2018.
 * Created by Abdel-Atif Mabrouck on 02/01/2018.
 * Created by Antoine Renault on 02/01/2018.
 */

public class UserManager implements RealmDefaultBehavior {

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
     * Update User Favorites
     *
     * @param favorites
     */
    public void updateUserFavorites(ArrayList<String> favorites, String id) {
        getRealmInstance().executeTransactionAsync(realm -> {
            // Retrieve the current user
            RealmObject realmObj = this.getEntitiesById(User.class, id);

            if (realmObj == null) {
                Log.println(Log.WARN, "RealmUser", "Cannot update user");
                return;
            }

            User user = (User) realmObj;
            user.setFavorites(favorites);

            realm.insertOrUpdate(user);
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
    }
}
