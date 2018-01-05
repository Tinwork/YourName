package com.yellowman.tinwork.yourname.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.yellowman.tinwork.yourname.network.api.Routes;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.internal.IOException;

/**
 * Created by Marc Intha-amnouay on 18/11/2017.
 * Created by Didier Youn on 18/11/2017.
 * Created by Abdel-Latif Mabrouck on 18/11/2017.
 * Created by Antoine Renault on 18/11/2017.
 */

public class AppUtils {

    /**
     * Build Get Url
     *      Build a custom GET Url to pass to the GsonGetManager
     * @param baseURL URL of the API
     * @param payload Payload of queryStrings
     * @return
     */
    public static String buildGetUrl(String baseURL, HashMap<String, String> payload) {
        String uri = "";
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


        try {
            uri = URLDecoder.decode(builder.build().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.d("Error", "Unable to decode URL with payload "+payload.toString());

            // we at least build the url anyway hoping that it'll be accepted
            uri = builder.build().toString();
        }

        return uri;
    }

    /**
     * Build Placeholder Url
     * @param baseURL Api URL
     * @param placeholders Array of URL Placeholder
     * @param restURL String representing the rest of the URL
     *
     * @TODO might change Min API Level...
     * @return URL
     */
    public static String buildPlaceholderUrl(String baseURL, String[] placeholders, String restURL) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(Routes.PROTOCOL)
               .authority(Routes.API_AUTHORITY)
               .appendEncodedPath(baseURL);

        for (String placeholder: placeholders) {
            builder.appendEncodedPath(placeholder);
        }

        if (restURL == null)
            return builder.build().toString();

        builder.appendEncodedPath(restURL);
        return builder.build().toString();
    }

    /**
     * Build Misc URL
     * @param baseURL Api URL
     * @param patch Misc URL data
     * @return URL
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
     * @param extras Extras data for the headers
     * @param token Token
     * @return Headers
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
     * @param ctx Context
     * @param keyName Key
     * @param keyValue Value
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
     * @param ctx Context
     * @param keyName Key
     * @return String
     */
    public static String getSharedPreference(Context ctx, String keyName) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPref.getString(keyName, "");
    }

    /**
     * Get Random Number
     * @param min int
     * @param max int
     * @return int random number
     */
    public static int getRandomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    /**
     * Make Nav Bar Translucent
     * @param w Window
     */
    public static void makeNavBarTranslucent(final Window w) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setNavigationBarColor(Color.WHITE);
        }
    }

    /**
     * Colorize Status Bar
     *
     * @param w Window
     * @param ctx Context
     * @param color R.id.color
     */
    public static void colorizeStatusBar(final Window w, Context ctx ,int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.setStatusBarColor(ContextCompat.getColor(ctx, color));
        }
    }

    /**
     * Output Realm File
     *
     * Credits @Budi Oktaviyan Suryanto
     * This code refer to the following article: https://medium.com/@budioktaviyans/android-realm-database-export-24753503b0c7
     */
    public static void outputRealmFile() {
        final Realm realm = Realm.getDefaultInstance();

        try {
            final File file = new File(Environment.getExternalStorageDirectory().getPath().concat("/sample.realm"));
            if (file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }

            Log.println(Log.WARN, "STORAGE", "Export path "+Environment.getExternalStorageDirectory().getPath().concat("/sample.realm"));
            realm.writeCopyTo(file);
        } catch (IOException e) {
            realm.close();
            e.printStackTrace();
        }
    }

    /**
     * Get Yesterday Timestamp
     *
     * @return int
     */
    public static int getYesterdayTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        int timestamp = (int) (cal.getTimeInMillis() / 1000);

        return timestamp;
    }
}
