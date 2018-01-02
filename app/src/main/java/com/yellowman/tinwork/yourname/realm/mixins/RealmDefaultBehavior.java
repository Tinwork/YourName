package com.yellowman.tinwork.yourname.realm.mixins;

import io.realm.Realm;

/**
 * Created by Marc Intha-amnouay on 02/01/2018.
 * Created by Didier Youn on 02/01/2018.
 * Created by Abdel-Atif Mabrouck on 02/01/2018.
 * Created by Antoine Renault on 02/01/2018.
 */

public interface RealmDefaultBehavior {

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
}
