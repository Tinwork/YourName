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

    private static final String PREFIX = "https://api.thetvdb.com";
    // Protocol
    public static final String PROTOCOL = "https";
    // Route that use the Authority and partial URI instead of the full one
    public static final String API_AUTHORITY = "api.thetvdb.com";
    // Series
    public static final String SEARCH_SERIES = "search/series";
    public static final String SERIES = "series";
    public static final String SEARCH_EPISODES = "episodes";

    public static final String IMG_PATH = "www.thetvdb.com/banners";
    public static final String SUFFIX_ROUTES_EPISODES_FROM_SERIES = "episodes/query";
    public static final String SUFFIX_ROUTES_EPISODES_SUMMARY = "episodes/summary";
    public static final String UPDATE_SERIES = "updated/query";

    // User auth
    public static final String LOGIN = PREFIX+"/login";
    public static final String REFRESH_TOKEN = PREFIX+"/refresh_token";
    // Prefix series
    public static final String PREFIX_SERIES = "series";
    // User
    // Route that not need params
    public static final String USER = PREFIX+"/user";
    public static final String FAVORITES = "user/favorites";
    public static final String FAVORITES_FULL = PREFIX+"/"+FAVORITES;
    public static final String TVDB_SITE_URL  = "https://www.thetvdb.com/?tab=series&id=";

    // IMDB Base URL
    public static final String IMDB_BASE_URL = "http://www.imdb.com/";
    public static final String IMDB_URI_SEARCH = "find?s=nm&q=";


    public static String getEpisode(String id) {
        return SEARCH_EPISODES+id;
    }
}
