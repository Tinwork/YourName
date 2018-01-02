package com.yellowman.tinwork.yourname.realm.manager;

import com.yellowman.tinwork.yourname.realm.mixins.RealmDefaultBehavior;

import io.realm.Realm;

/**
 * Created by Marc Intha-amnouay on 02/01/2018.
 * Created by Didier Youn on 02/01/2018.
 * Created by Abdel-Atif Mabrouck on 02/01/2018.
 * Created by Antoine Renault on 02/01/2018.
 */

public class CommonManager implements RealmDefaultBehavior {

    /**
     * Get Realm Instance
     *
     * @return
     */
    @Override
    public Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }
}
