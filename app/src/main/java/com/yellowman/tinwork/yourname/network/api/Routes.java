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

    public static final String LOGIN = PREFIX+"/login";
    public static final String REFRESH_TOKEN = PREFIX+"/refresh_token";
}
