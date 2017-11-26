package com.yellowman.tinwork.yourname.model.User;

import java.util.Map;

/**
 * Created by Marc Intha-amnouay on 25/11/2017.
 * Created by Didier Youn on 25/11/2017.
 * Created by Abdel-Atif Mabrouck on 25/11/2017.
 * Created by Antoine Renault on 25/11/2017.
 */

public class User {
    private Map<String, Object> data;

    /**
     * Get Data
     * @return
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Set Data
     * @param data
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
