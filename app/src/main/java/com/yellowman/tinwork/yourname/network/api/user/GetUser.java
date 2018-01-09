package com.yellowman.tinwork.yourname.network.api.user;

import android.content.Context;

import com.yellowman.tinwork.yourname.entity.User;
import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;
import com.yellowman.tinwork.yourname.network.api.Routes;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.network.fetch.GsonGetManager;
import com.yellowman.tinwork.yourname.network.fetch.RequestQueueManager;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;
import com.yellowman.tinwork.yourname.utils.AppUtils;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 05/01/2018.
 * Created by Didier Youn on 05/01/2018.
 * Created by Abdel-Atif Mabrouck on 05/01/2018.
 * Created by Antoine Renault on 05/01/2018.
 */

public class GetUser extends Fetch {

    private Context ctx;
    private RequestQueueManager requestQueueManager;
    private int retry;
    private GsonGetManager<User> reqUser;
    private CommonManager realmManager;

    /**
     * Get User
     *
     * @param ctx Application Context
     */
    public GetUser(Context ctx) {
        this.ctx   = ctx;
        this.retry = 0;
        this.requestQueueManager = RequestQueueManager.getInstance(ctx);
        this.realmManager = new CommonManager();
    }

    /**
     * Get
     *
     * @param payload hashmap
     * @param callback GsonCallback
     */
    @Override
    public void get(HashMap<String, String> payload, GsonCallback callback) {
        // Get the token
        String token = AppUtils.getSharedPreference(ctx, "yourname_token");
        // Headers
        HashMap<String, String> headers = AppUtils.makeHeaders(null, token);

        reqUser = new GsonGetManager<>(Routes.USER, User.class, headers, response -> {
            // Persist the user in Realm
            realmManager.commitCreatedEntity(response);
            callback.onSuccess(response);
        }, error -> {
            this.handleVolleyError(error, reqUser, ctx, retry, callback);
            retry++;
        }, true);

        requestQueueManager.addToRequestQueue(reqUser);
    }
}
