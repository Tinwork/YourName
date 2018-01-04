package com.yellowman.tinwork.yourname.realm.decorator;

import android.content.Context;

import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.helper.ConnectivityHelper;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;

/**
 * Created by Marc Intha-amnouay on 04/01/2018.
 * Created by Didier Youn on 04/01/2018.
 * Created by Abdel-Atif Mabrouck on 04/01/2018.
 * Created by Antoine Renault on 04/01/2018.
 */

public class UserRealmDecorator extends CommonManager {

    private Context ctx;
    private ConnectivityHelper conHelper;

    /**
     * User Realm Decorator::Constructor
     *
     * @param ctx
     */
    public UserRealmDecorator(Context ctx) {
        this.ctx = ctx;
        this.conHelper = new ConnectivityHelper(ctx);
    }

    /**
     * Get
     *
     * @TODO if needed pass other params
     * @param callback
     */
    public void get(GsonCallback callback) {
        if (conHelper.getConnectivityStatus()) {
            // @TODO call the services here
        }

        // @TODO Call realm here
    }
}
