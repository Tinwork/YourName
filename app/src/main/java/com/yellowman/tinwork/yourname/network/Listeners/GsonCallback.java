package com.yellowman.tinwork.yourname.network.Listeners;

/**
 * Created by Marc Intha-amnouay on 20/11/2017.
 * Created by Didier Youn on 20/11/2017.
 * Created by Abdel-Latif Mabrouck on 20/11/2017.
 * Created by Antoine Renault on 20/11/2017.
 */

/**
 * Gson Callback
 *
 * @param <T>
 */
public interface GsonCallback<T> {
    /**
     * On Success
     *
     * @param response Any
     */
    void onSuccess(T response);

    /**
     * On Error
     *
     * @param err String
     */
    void onError(String err);
}
