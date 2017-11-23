package com.yellowman.tinwork.yourname.network.helper;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marc Intha-amnouay on 22/11/2017.
 * Created by Didier Youn on 22/11/2017.
 * Created by Abdel-Latif Mabrouck on 22/11/2017.
 * Created by Antoine Renault on 22/11/2017.
 */

/**
 * @TODO test this Helper
 */
public class VolleyErrorHelper {

    /**
     * Is Basic Error
     *
     * @param error
     */
    public static Boolean isBasicError(VolleyError error) {
        Boolean isBasic = true;

        if (error instanceof NetworkError) {
            Log.d("Error", "Network error");
        } else if (error instanceof  AuthFailureError) {
            Log.d("Error", "AuthFailureError");
            isBasic = false;
        } else if (error instanceof ServerError) {
            Log.d("Error", "Server error");
        }

        return isBasic;
    }


    /**
     * Get Error Code
     * @param error
     * @return
     */
    public static int getErrorCode(VolleyError error) {
        if (error.networkResponse != null)
            return error.networkResponse.statusCode;

        return 0;
    }

    /**
     * Get Network Error Data
     * @param error
     * @return
     */
    public static HashMap<String, String> getNetworkErrorData(VolleyError error) {
        HashMap<String, String> errorType = new HashMap<>();

        if (error.networkResponse == null) {
            errorType.put("message", error.getMessage());

            return errorType;
        }

        int code = getErrorCode(error);
        errorType.put("code", Integer.toString(code));
        errorType.put("message", getJSONnetworkError(error));

        return errorType;
    }


    /**
     * Get JSON Network Error
     * @param dataError
     * @return
     */
    protected static String getJSONnetworkError(VolleyError dataError) {
        try {
            // Try to get the error using Gson
            HashMap<String, String> result = new Gson().fromJson(
                    new String(dataError.networkResponse.data),
                    new TypeToken<Map<String, String>>(){}.getType()
            );

            if(result != null && result.containsKey("Error"))
                return result.get("Error");
        } catch (Exception e) {
            Log.d("Warning", "Can not parse JSON Error");
        }

        return dataError.getMessage();
    }
}
