package com.yellowman.tinwork.yourname.application;

import com.yellowman.tinwork.yourname.network.Listeners.GsonCallback;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 03/01/2018.
 * Created by Didier Youn on 03/01/2018.
 * Created by Abdel-Atif Mabrouck on 03/01/2018.
 * Created by Antoine Renault on 03/01/2018.
 */

public interface CommonNetwork {
    void get(HashMap<String, String> payload, final GsonCallback callback);
}
