package com.yellowman.tinwork.yourname.network.api;


/**
 * List of ROUTES to use within the App
 * Avoid reuse the String of the route everywhere
 *
 * Created by Marc Intha-amnouay on 19/11/2017.
 * Created by Didier Youn on 19/11/2017.
 * Created by Abdel-Latif Mabrouck on 19/11/2017.
 * Created by Antoine Renault on 19/11/2017.
 */
public class Routes {
    // Protocol
    public static final String PROTOCOL = "https";
    // Route that use the Authority and partial URI instead of the full one
    public static final String API_AUTHORITY = "api.thetvbd.com";
    // Series
    public static final String SEARCH_SERIES = "search/series";
    private static final String PREFIX = "https://api.thetvdb.com";
    // User auth
    public static final String LOGIN = PREFIX+"/login";
    public static final String REFRESH_TOKEN = PREFIX+"/refresh_token";
    // User
    // Route that not need params
    public static final String USER = PREFIX+"/user";
}
