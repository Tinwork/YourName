package com.yellowman.tinwork.yourname.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.yellowman.tinwork.yourname.network.api.Routes;

import java.util.Arrays;
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
        if (payload.isEmpty())
            return baseURL;

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Routes.PROTOCOL)
               .authority(Routes.API_AUTHORITY)
               .appendEncodedPath(baseURL);

        for (Map.Entry<String, String> data: payload.entrySet()) {
            String key   = data.getKey();
            String value = data.getValue();

            builder.appendQueryParameter(key, value);
        }

        return builder.build().toString();
    }

    /**
     * Build Placeholder Url
     * @param baseURL
     * @param placeholders
     * @param restURL
     *
     * @TODO might change Min API Level...
     * @return
     */
    public static String buildPlaceholderUrl(String baseURL, String[] placeholders, String restURL) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Routes.PROTOCOL)
               .authority(Routes.API_AUTHORITY)
               .appendEncodedPath(baseURL);

        Arrays.stream(placeholders).forEach(placeholder -> builder.appendPath(placeholder));

        if (restURL == null)
            return builder.build().toString();

        builder.appendEncodedPath(restURL);
        return builder.build().toString();
    }

    /**
     * Build Misc URL
     * @param baseURL
     * @param patch
     * @return
     */
    public static Uri buildMiscURI(String baseURL, String patch) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Routes.PROTOCOL)
               .encodedAuthority(baseURL)
               .appendEncodedPath(patch);

        return builder.build();
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
        headers.put("Authorization", "Bearer "+token);

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
     *
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
     *
     * @param ctx
     * @param keyName
     * @return
     */
    public static String getSharedPreference(Context ctx, String keyName) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPref.getString(keyName, "");
    }

    /**
     * Get Random Number
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    /**
     * Make Nav Bar Translucent
     * @param w
     */
    public static void makeNavBarTranslucent(final Window w) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setNavigationBarColor(Color.WHITE);
        }
    }
}
