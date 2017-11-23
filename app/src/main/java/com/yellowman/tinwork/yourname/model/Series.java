package com.yellowman.tinwork.yourname.model;

/**
 * Created by Marc Intha-amnouay on 19/11/2017.
 * Created by Didier Youn on 19/11/2017.
 * Created by Abdel-Latif Mabrouck on 19/11/2017.
 * Created by Antoine Renault on 19/11/2017.
 */

public class Series {

    private String[] aliases;
    private String banner;
    private String firstAired;
    private String id;
    private String network;
    private String overview;
    private String seriesName;
    private String status;

    /**
     * Get Aliases
     * @return
     */
    public String[] getAliases() {
        return aliases;
    }

    /**
     * Set Aliases
     * @param aliases
     */
    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    /**
     * Get Banner
     * @return
     */
    public String getBanner() {
        return banner;
    }

    /**
     * Set Banner
     * @param banner
     */
    public void setBanner(String banner) {
        this.banner = banner;
    }

    /**
     * Get First Aired
     * @return
     */
    public String getFirstAired() {
        return firstAired;
    }

    /**
     * Set First Aired
     * @param firstAired
     */
    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    /**
     * Get Id
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Set Id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get Network
     * @return
     */
    public String getNetwork() {
        return network;
    }

    /**
     * Set Network
     * @param network
     */
    public void setNetwork(String network) {
        this.network = network;
    }

    /**
     * Get Overview
     * @return
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Set Overview
     * @param overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * Get Series Name
     * @return
     */
    public String getSeriesName() {
        return seriesName;
    }

    /**
     * Set Series Name
     * @param seriesName
     */
    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    /**
     * Get Status
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set Status
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
