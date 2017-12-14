package com.yellowman.tinwork.yourname.entity;

/**
 * Class Episode
 *
 * @author                 Didier Youn <didier.youn@gmail.com>,
 *                         Marc Intha-amnouay <marc.inthaamnouay@gmail.com>,
 *                         Abdel-Latif Mabrouck <amatroud0@gmail.com>,
 *                         Antoine Renault <antoine.renault.mmi@gmail.com>.
 * @link                   https://github.com/Tinwork/YourName
 */
public class Episode
{
    private int id;
    private String episodeName;
    private String firstAired;
    private String overview;
    private int lastUpdated;
    private int dvdSeason;
    private int dvdEpisodeNumber;
    private int airedEpisodeNumber;
    private int airedSeason;

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
}
