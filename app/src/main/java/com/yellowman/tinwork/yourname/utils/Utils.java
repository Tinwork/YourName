package com.yellowman.tinwork.yourname.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marc Intha-amnouay on 18/11/2017.
 * Created by Didier Youn on 18/11/2017.
 * Created by Abdel-Latif Mabrouck on 18/11/2017.
 * Created by Antoine Renault on 18/11/2017.
 */

public class Utils {

    /**
     * Build Get Url
     *      Build a custom GET Url to pass to the GsonGetManager
     * @param baseURL
     * @param payload
     * @return
     */
    public static String buildGetUrl(String baseURL, HashMap<String, String> payload) {
        Boolean startPattern = true;
        if (payload.isEmpty())
            return baseURL;

        for (Map.Entry<String, String> data: payload.entrySet()) {
            String key   = data.getKey();
            String value = data.getValue();

            if (startPattern) {
                baseURL += "?";
            } else {
                baseURL += "&";
            }

            baseURL += key+"="+value;
        }

        return baseURL;
    }

    /**
     * Make Headers
     *      Make Custom headers for request
     * @param extras
     * @param token
     * @return
     */
    public static HashMap<String, String> makeHeaders(HashMap<String, String> extras, String token) {
        // Init a header with basic properties
        final HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        headers.put("Authorisation", "Bearer "+token);

        // No extas return the basic URL
        if (extras == null)
            return headers;

        for (Map.Entry<String, String> extrasHeader: extras.entrySet()) {
            String key   = extrasHeader.getKey();
            String value = extrasHeader.getValue();

            headers.put(key, value);
        }

        return headers;
    }

    /**
     * Save Shared Preference
     * @param ctx
     * @param keyName
     * @param keyValue
     */
    public static void saveSharedPreference(Context ctx, String keyName, String keyValue) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(keyName, keyValue);
        editor.commit();
    }

    /**
     * Get Shared Preference
     * @param ctx
     * @param keyName
     * @return
     */
    public static String getSharedPreference(Context ctx, String keyName) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);

        return sharedPref.getString(keyName, "nopayload");
    }
}
