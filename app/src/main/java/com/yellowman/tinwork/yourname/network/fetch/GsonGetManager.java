package com.yellowman.tinwork.yourname.network.fetch;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;


import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Marc Intha-amnouay on 17/11/2017.
 * Created by Didier Youn on 17/11/2017.
 * Created by Abdel-Latif Mabrouck on 17/11/2017.
 * Created by Antoine Renault on 17/11/2017.
 */

public class GsonGetManager<T> extends Request<T> {

    // Final fields
    private final Gson gson = new Gson();
    private final Boolean extract;

    private Class<T> cls;
    private Map<String, String> headers;
    private Response.Listener listener;

    /**
     * Gson Reflection Manager
     *      Though we could have use Optional but for backward compatibility reason we do not...
     *
     * @param URL
     * @param cls
     * @param headers
     * @param listener
     * @param errListener
     */
    public GsonGetManager(String URL, Class<T> cls, Map<String, String> headers, Response.Listener<T> listener, Response.ErrorListener errListener, Boolean extract) {
        // Instance the super class
        super(Method.GET, URL, errListener);
        this.cls      = cls;
        this.headers  = headers;
        this.listener = listener;
        this.extract  = extract;
    }

    /**
     * Get Headers
     *
     * @return
     * @throws AuthFailureError
     */
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (headers == null)
            throw new Error("AuthFailureError");

        return headers;
    }

    /**
     * Deliver Response
     *
     * @param response
     */
    @Override
    public void deliverResponse(T response) {
        listener.onResponse(response);
    }

    /**
     * Parse Network Response
     *
     * @param response
     * @return
     */
    @Override
    public Response<T> parseNetworkResponse(NetworkResponse response) {
        String json = "";

        try {
            json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            Log.d("Error", "UnsupportedEncodingException error: "+e.toString());
        } catch (JsonSyntaxException e) {
            Log.d("Error", "JsonSyntaxEception error:  "+e.toString());
        }

        // If we extract need to extract the datas
        if (extract) {
            return desarialize(json, response);
        }

        return Response.success(
                gson.fromJson(json, cls),
                HttpHeaderParser.parseCacheHeaders(response));
    }

    /**
     * Desarialize
     *      Deserialize data when a payload need to be extract from the "data" object
     *
     * @param json
     * @param response
     * @return
     */
    private Response<T> desarialize(String json, NetworkResponse response) {
        JsonElement element = gson.fromJson(json, JsonElement.class);
        JsonObject obj = element.getAsJsonObject();

        if (obj.get("data").isJsonNull()) {
            return Response.error(new VolleyError("Data is empty"));
        } else if (obj.get("data").isJsonArray()) {
            JsonArray arr = obj.get("data").getAsJsonArray();
            if (arr.size() > 0) {
                return Response.success(
                        gson.fromJson(arr, cls),
                        HttpHeaderParser.parseCacheHeaders(response)
                );
            }
        }

        return Response.success(
                gson.fromJson(obj.get("data"), cls),
                HttpHeaderParser.parseCacheHeaders(response)
        );
    }
}
