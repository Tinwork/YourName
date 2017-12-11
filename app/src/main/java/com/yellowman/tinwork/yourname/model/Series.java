package com.yellowman.tinwork.yourname.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Marc Intha-amnouay on 19/11/2017.
 * Created by Didier Youn on 19/11/2017.
 * Created by Abdel-Latif Mabrouck on 19/11/2017.
 * Created by Antoine Renault on 19/11/2017.
 */

public class Series implements Parcelable {

    /**
     * Creator
     *      Deserializer an instance of a Class (bind the class with the parcel)
     */
    public static final Parcelable.Creator<Series> CREATOR = new Parcelable.Creator<Series>() {
        @Override
        public Series createFromParcel(Parcel parcel) {
            return new Series(parcel);
        }

        @Override
        public Series[] newArray(int i) {
            return new Series[0];
        }
    };
    private String[] aliases;
    private String banner;
    private String firstAired;
    private String id;
    private String network;
    private String overview;
    private String seriesName;
    private String status;

    public Series() {}

    /**
     * Series Constructor for Parcelable
     * @param parcel
     */
    public Series(Parcel parcel) {
        aliases    = parcel.createStringArray();
        banner     = parcel.readString();
        firstAired = parcel.readString();
        id         = parcel.readString();
        network    = parcel.readString();
        overview   = parcel.readString();
        seriesName = parcel.readString();
        status     = parcel.readString();
    }

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

    /**
     * Describe Contents
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write To Parcel
     *      Write the class properties into the Parcel
     * @param dest
     * @param i
     */
    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeStringArray(aliases);
        dest.writeString(banner);
        dest.writeString(firstAired);
        dest.writeString(id);
        dest.writeString(network);
        dest.writeString(overview);
        dest.writeString(seriesName);
        dest.writeString(status);
    }
}
