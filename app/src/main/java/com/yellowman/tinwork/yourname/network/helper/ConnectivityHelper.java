package com.yellowman.tinwork.yourname.network.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Marc Intha-amnouay on 03/01/2018.
 * Created by Didier Youn on 03/01/2018.
 * Created by Abdel-Atif Mabrouck on 03/01/2018.
 * Created by Antoine Renault on 03/01/2018.
 */

public class ConnectivityHelper {

    protected Context ctx;

    /**
     * Connectivity Helper::Constructor
     *
     * @param ctx
     */
    public ConnectivityHelper(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * Get Connectivity Status
     *
     * @return
     */
    public Boolean getConnectivityStatus() {
        ConnectivityManager cm    = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
               activeNetwork.isConnectedOrConnecting();
    }
}
