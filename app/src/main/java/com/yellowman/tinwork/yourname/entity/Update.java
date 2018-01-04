package com.yellowman.tinwork.yourname.entity;

/**
 * Created by Marc Intha-amnouay on 04/01/2018.
 * Created by Didier Youn on 04/01/2018.
 * Created by Abdel-Atif Mabrouck on 04/01/2018.
 * Created by Antoine Renault on 04/01/2018.
 */

public class Update {
    private String id;
    private String lastUpdated;

    /**
     * Get Id
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Set Id
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get Last Updated
     *
     * @return
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Set Last Updated
     *
     * @param lastUpdated
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
