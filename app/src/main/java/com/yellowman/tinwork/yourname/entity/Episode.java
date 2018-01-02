package com.yellowman.tinwork.yourname.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Class Episode
 *
 * @author                 Didier Youn <didier.youn@gmail.com>,
 *                         Marc Intha-amnouay <marc.inthaamnouay@gmail.com>,
 *                         Abdel-Latif Mabrouck <amatroud0@gmail.com>,
 *                         Antoine Renault <antoine.renault.mmi@gmail.com>.
 * @link                   https://github.com/Tinwork/YourName
 */
public class Episode implements Parcelable
{

    /**
     * Creator
     *
     */
    public static final Parcelable.Creator<Episode> CREATOR = new Parcelable.Creator<Episode>() {

        @Override
        public Episode createFromParcel(Parcel source) {
            return new Episode(source);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[0];
        }
    };

    @PrimaryKey
    private int id;
    private String episodeName;
    private String firstAired;
    private String overview;
    private int lastUpdated;
    private int dvdSeason;
    private int dvdEpisodeNumber;
    private int airedEpisodeNumber;
    private int airedSeason;
    private RealmList<String> directors;
    private String siteRating;
    private String filename;

    public Episode() {}

    /**
     * Episode::Constructor (use by Parcel.Creator<T>)
     * @param parcel
     */
    public Episode(Parcel parcel) {
        id = parcel.readInt();
        episodeName        = parcel.readString();
        firstAired         = parcel.readString();
        overview           = parcel.readString();
        lastUpdated        = parcel.readInt();
        dvdSeason          = parcel.readInt();
        dvdEpisodeNumber   = parcel.readInt();
        airedEpisodeNumber = parcel.readInt();
        airedSeason        = parcel.readInt();
        siteRating         = parcel.readString();
        filename           = parcel.readString();
        directors          = new RealmList<>();

        ArrayList<String> intermDirectors = parcel.createStringArrayList();

        if (intermDirectors != null) {
            directors.addAll(intermDirectors);
        }
    }

    /**
     * @return int
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id int
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return String
     */
    public String getEpisodeName()
    {
        return episodeName;
    }

    /**
     * @param episodeName String
     */
    public void setEpisodeName(String episodeName)
    {
        this.episodeName = episodeName;
    }

    /**
     * @return String
     */
    public String getFirstAired()
    {
        return firstAired;
    }

    /**
     * @param firstAired String
     */
    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    /**
     * @return String
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @param overview String
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return int
     */
    public int getLastUpdated()
    {
        return lastUpdated;
    }

    /**
     * @param lastUpdated int
     */
    public void setLastUpdated(int lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * @return int
     */
    public int getDvdSeason() {
        return dvdSeason;
    }

    /**
     * @param dvdSeason int
     */
    public void setDvdSeason(int dvdSeason)
    {
        this.dvdSeason = dvdSeason;
    }

    /**
     * @return int
     */
    public int getDvdEpisodeNumber()
    {
        return dvdEpisodeNumber;
    }

    /**
     * @param dvdEpisodeNumber int
     */
    public void setDvdEpisodeNumber(int dvdEpisodeNumber)
    {
        this.dvdEpisodeNumber = dvdEpisodeNumber;
    }

    /**
     * @return int
     */
    public int getAiredEpisodeNumber()
    {
        return airedEpisodeNumber;
    }

    /**
     * @param airedEpisodeNumber int
     */
    public void setAiredEpisodeNumber(int airedEpisodeNumber)
    {
        this.airedEpisodeNumber = airedEpisodeNumber;
    }

    /**
     * @return int
     */
    public int getAiredSeason()
    {
        return airedSeason;
    }

    /**
     * @param airedSeason int
     */
    public void setAiredSeason(int airedSeason)
    {
        this.airedSeason = airedSeason;
    }

    /**
     *
     * @return
     */
    public RealmList<String> getDirectors() {
        return directors;
    }

    /**
     *
     * @param directors
     */
    public void setDirectors(ArrayList<String> directors) {
        this.directors = new RealmList<>();
        this.directors.addAll(directors);
    }

    /**
     *
     * @return
     */
    public String getSiteRating() {
        return siteRating;
    }

    /**
     *
     * @param siteRating
     */
    public void setSiteRating(String siteRating) {
        this.siteRating = siteRating;
    }

    /**
     *
     * @return
     */
    public String getFilename() {
        return filename;
    }

    /**
     *
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(episodeName);
        dest.writeString(firstAired);
        dest.writeString(overview);
        dest.writeInt(lastUpdated);
        dest.writeInt(dvdSeason);
        dest.writeInt(dvdEpisodeNumber);
        dest.writeInt(airedEpisodeNumber);
        dest.writeInt(airedSeason);
        dest.writeString(siteRating);
        dest.writeString(filename);

        // Special case for Realm
        dest.writeStringList(directors);
    }
}
