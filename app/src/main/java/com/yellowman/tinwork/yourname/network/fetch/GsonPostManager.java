package com.yellowman.tinwork.yourname.network.fetch;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

/**
 * Created by Marc Intha-amnouay on 18/11/2017.
 * Created by Didier Youn on 18/11/2017.
 * Created by Abdel-Latif Mabrouck on 18/11/2017.
 * Created by Antoine Renault on 18/11/2017.
 */

public class GsonPostManager<T> extends JsonRequest<T> {

    // Final fields
    private final Gson gson = new Gson();

    // Class fields
    private Class<T> cls;
    private Response.Listener listener;

    /**
     * Gson Post Manager::Constructor
     * @param URL String
     * @param body String
     * @param cls Class
     * @param listener Response.Listener
     * @param errListener Response.ErrorListener
     */
    public GsonPostManager(String URL, String body, Class<T> cls, Response.Listener<T> listener, Response.ErrorListener errListener) {
        super(Method.POST, URL, body, listener, errListener);
        this.cls = cls;
        this.listener = listener;
    }

    /**
     * Deliver Response
     *      Override the abstract implementation
     * @param response Response
     */
    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    /**
     * Parse Network Response
     *
     * @param response Response
     * @return Response
     */
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
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

        return Response.success(
                gson.fromJson(json, cls),
                HttpHeaderParser.parseCacheHeaders(response));
    }
}
