package com.yellowman.tinwork.yourname.realm.decorator;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.User;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.user.GetUser;
import com.yellowman.tinwork.yourname.network.helper.ConnectivityHelper;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import io.realm.RealmObject;

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
     * @param ctx Context
     */
    public UserRealmDecorator(Context ctx) {
        this.ctx = ctx;
        this.conHelper = new ConnectivityHelper(ctx);
    }

    /**
     * Get
     *
     * @param callback GsonCallback
     */
    public void get(GsonCallback callback) {
        String username = AppUtils.getSharedPreference(ctx, "username");
        if (username.isEmpty()) {
            callback.onError("User is anonymous");
        }

        if (conHelper.getConnectivityStatus()) {
            GetUser getUser = new GetUser(ctx);
            getUser.get(null, callback);
            return;
        }

        // Otherwise we retrive the user using Realm
        RealmObject user = this.getEntityByCriterion(User.class, "userName", username);

        if (user == null) {
            callback.onError("User is null");
            return;
        }

        User usr = (User) getRealmInstance().copyFromRealm(user);
        callback.onSuccess(usr);
    }
}
