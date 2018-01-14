package com.yellowman.tinwork.yourname.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.yellowman.tinwork.yourname.entity.Actor;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Marc Intha-amnouay on 19/11/2017.
 * Created by Didier Youn on 19/11/2017.
 * Created by Abdel-Latif Mabrouck on 19/11/2017.
 * Created by Antoine Renault on 19/11/2017.
 */

public class Series extends RealmObject implements Parcelable {

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

    @PrimaryKey
    private String id;
    private RealmList<String> aliases;
    private String banner;
    private String firstAired;
    private int    lastUpdated;
    private String network;
    private String overview;
    private String seriesName;
    private String status;
    private RealmList<String> genre;
    private RealmList<Actor> actors;
    private String siteRating;
    private Boolean favorite = false;
    private Boolean saved = false;


    public Series() {}

    /**
     * Series Constructor for Parcelable
     *
     * @param parcel Parcel
     */
    public Series(Parcel parcel) {
        banner     = parcel.readString();
        firstAired = parcel.readString();
        id         = parcel.readString();
        network    = parcel.readString();
        overview   = parcel.readString();
        seriesName = parcel.readString();
        status     = parcel.readString();
        siteRating = parcel.readString();
        lastUpdated= parcel.readInt();
        favorite   = parcel.readByte() != 0;
        saved      = parcel.readByte() != 0;
        aliases    = new RealmList<>();
        genre      = new RealmList<>();
        actors     = new RealmList<>();

        // set the list to the RealmList
        ArrayList<String> intermAliases = parcel.createStringArrayList();
        ArrayList<String> intermGenre   = parcel.createStringArrayList();
        List<Actor> intermActors        = parcel.createTypedArrayList(Actor.CREATOR);

        if (intermAliases != null) {
            aliases.addAll(intermAliases);
        }

        if (intermGenre != null) {
            genre.addAll(intermGenre);
        }

        if (intermActors != null) {
            actors.addAll(intermActors);
        }
    }

    /**
     * Get Aliases
     *
     * @return RealmList<String>
     */
    public RealmList<String> getAliases() {
        return aliases;
    }

    /**
     * Set Aliases
     *
     * @param aliases ArrayList<String>
     */
    public void setAliases(ArrayList<String> aliases) {
        this.aliases = new RealmList<>();
        this.aliases.addAll(aliases);
    }

    /**
     * Get Banner
     *
     * @return String
     */
    public String getBanner() {
        return banner;
    }

    /**
     * Set Banner
     *
     * @param banner String
     */
    public void setBanner(String banner) {
        this.banner = banner;
    }

    /**
     * Get First Aired
     *
     * @return String
     */
    public String getFirstAired() {
        return firstAired;
    }

    /**
     * Set First Aired
     *
     * @param firstAired String
     */
    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    /**
     * Get Id
     *
     * @return String id
     */
    public String getId() {
        return id;
    }

    /**
     * Set Id
     *
     * @param id Strings
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get Network
     *
     * @return String
     */
    public String getNetwork() {
        return network;
    }

    /**
     * Set Network
     *
     * @param network String
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
     *
     * @param overview String
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
     *
     * @param seriesName string
     */
    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    /**
     * Get Status
     *
     * @return String status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set Status
     *
     * @param status String
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get Genre
     *
     * @return RealmList<String>
     */
    public RealmList<String> getGenre() {
        return genre;
    }

    /**
     * Set Genre
     *
     * @param genre ArrayList<String>
     */
    public void setGenre(ArrayList<String> genre) {
        this.genre = new RealmList<>();
        this.genre.addAll(genre);
    }

    /**
     * Get Site Rating
     *
     * @return string
     */
    public String getSiteRating() {
        return siteRating;
    }

    /**
     * Set Site Ratings
     *
     * @param siteRating int
     */
    public void setSiteRating(String siteRating) {
        this.siteRating = siteRating;
    }

    /**
     * Describe Contents
     * @return int
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Get Actors
     *
     * @return RealmList<Actors>
     */
    public RealmList<Actor> getActors() {
        return actors;
    }

    /**
     * Set Actors
     *
     * @param actors ArrayList<Actors>
     */
    public void setActors(ArrayList<Actor> actors) {
        this.actors = new RealmList<>();
        this.actors.addAll(actors);
    }

    /**
     * Get Last Updated
     *
     * @return int
     */
    public int getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Set Last Updated
     *
     * @param lastUpdated int
     */
    public void setLastUpdated(int lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Get Favorite
     *
     * @return Boolean favorite
     */
    public Boolean getFavorite() {
        return favorite;
    }

    /**
     * Set Favorite
     *
     * @param favorite Boolean
     */
    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    /**
     * Get Saved
     *
     * @return saved Boolean
     */
    public Boolean getSaved() {
        return saved;
    }

    /**
     * Set Saved
     *
     * @param saved boolean saved
     */
    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    /**
     * Write To Parcel
     *      Write the class properties into the Parcel
     * @param dest
     * @param i
     */
    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(banner);
        dest.writeString(firstAired);
        dest.writeString(id);
        dest.writeString(network);
        dest.writeString(overview);
        dest.writeString(seriesName);
        dest.writeString(status);
        dest.writeString(siteRating);
        dest.writeInt(lastUpdated);
        dest.writeByte((byte) (favorite ? 1 : 0));
        dest.writeByte((byte) (saved ? 1 : 0));

        // Special case for Realm
        dest.writeStringList(aliases);
        dest.writeStringList(genre);
        dest.writeTypedList(actors);
    }
}
