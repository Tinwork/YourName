package com.yellowman.tinwork.yourname.network.Listeners;

/**
 * Created by Marc Intha-amnouay on 20/11/2017.
 * Created by Didier Youn on 20/11/2017.
 * Created by Abdel-Latif Mabrouck on 20/11/2017.
 * Created by Antoine Renault on 20/11/2017.
 */

public interface GsonCallback<T> {
    void onSuccess(T response);
}
