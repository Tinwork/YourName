package com.yellowman.tinwork.yourname.network.fetch;

import java.util.HashMap;

/**
 * Created by Marc Intha-amnouay on 18/11/2017.
 * Created by Didier Youn on 18/11/2017.
 * Created by Abdel-Latif Mabrouck on 18/11/2017.
 * Created by Antoine Renault on 18/11/2017.
 */

public class RoutesManager {

    // Final fields
    public final String API_PREFIX = "https://api.thetvdb.com";

    // Route fields
    public String LOGIN = "/login";


    /**
     * Get Routes Props
     * @return
     */
    public HashMap<String, Object> getRoutesProps() {
        String[] constraintName = {"token"};
        HashMap<String, Object> route = new HashMap<String, Object>();

        route.put("endpoint", this.API_PREFIX+this.LOGIN);
        route.put("constraint", constraintName);

        return route;
    }
}
