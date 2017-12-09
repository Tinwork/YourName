package com.yellowman.tinwork.yourname.model;

/**
 * Created by almabrouck on 07/12/2017.
 */

public class Episodes {
    private static final long serialVersionUID = 1L;
    private String id;
    private String combinedEpisodeNumber;
    private String combinedSeason;
    private String dvdChapter;
    private String dvdDiscId;
    private String dvdEpisodeNumber;
    private String dvdSeason;
    private String[] directors;
    private String epImgFlag;
    private String episodeName;
    private int episodeNumber;
    private String firstAired;
    private String[] guestStars;
    private String imdbId;
    private String language;
    private String overview;
    private String productionCode;
    private String rating;
    private int seasonNumber;
    private String[] writers;
    private String absoluteNumber;
    private int airsAfterSeason;
    private int airsBeforeSeason;
    private int airsBeforeEpisode;
    private String filename;
    private String lastUpdated;
    private String seriesId;
    private String seasonId;

    public Episodes(String id, String combinedEpisodeNumber, String combinedSeason, String dvdChapter, String dvdDiscId, String dvdEpisodeNumber, String dvdSeason, String[] directors, String epImgFlag, String episodeName, int episodeNumber, String firstAired, String[] guestStars, String imdbId, String language, String overview, String productionCode, String rating, int seasonNumber, String[] writers, String absoluteNumber, int airsAfterSeason, int airsBeforeSeason, int airsBeforeEpisode, String filename, String lastUpdated, String seriesId, String seasonId) {
        this.id = id;
        this.combinedEpisodeNumber = combinedEpisodeNumber;
        this.combinedSeason = combinedSeason;
        this.dvdChapter = dvdChapter;
        this.dvdDiscId = dvdDiscId;
        this.dvdEpisodeNumber = dvdEpisodeNumber;
        this.dvdSeason = dvdSeason;
        this.directors = directors;
        this.epImgFlag = epImgFlag;
        this.episodeName = episodeName;
        this.episodeNumber = episodeNumber;
        this.firstAired = firstAired;
        this.guestStars = guestStars;
        this.imdbId = imdbId;
        this.language = language;
        this.overview = overview;
        this.productionCode = productionCode;
        this.rating = rating;
        this.seasonNumber = seasonNumber;
        this.writers = writers;
        this.absoluteNumber = absoluteNumber;
        this.airsAfterSeason = airsAfterSeason;
        this.airsBeforeSeason = airsBeforeSeason;
        this.airsBeforeEpisode = airsBeforeEpisode;
        this.filename = filename;
        this.lastUpdated = lastUpdated;
        this.seriesId = seriesId;
        this.seasonId = seasonId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCombinedEpisodeNumber() {
        return combinedEpisodeNumber;
    }

    public void setCombinedEpisodeNumber(String combinedEpisodeNumber) {
        this.combinedEpisodeNumber = combinedEpisodeNumber;
    }

    public String getCombinedSeason() {
        return combinedSeason;
    }

    public void setCombinedSeason(String combinedSeason) {
        this.combinedSeason = combinedSeason;
    }

    public String getDvdChapter() {
        return dvdChapter;
    }

    public void setDvdChapter(String dvdChapter) {
        this.dvdChapter = dvdChapter;
    }

    public String getDvdDiscId() {
        return dvdDiscId;
    }

    public void setDvdDiscId(String dvdDiscId) {
        this.dvdDiscId = dvdDiscId;
    }

    public String getDvdEpisodeNumber() {
        return dvdEpisodeNumber;
    }

    public void setDvdEpisodeNumber(String dvdEpisodeNumber) {
        this.dvdEpisodeNumber = dvdEpisodeNumber;
    }

    public String getDvdSeason() {
        return dvdSeason;
    }

    public void setDvdSeason(String dvdSeason) {
        this.dvdSeason = dvdSeason;
    }

    public String[] getDirectors() {
        return directors;
    }

    public void setDirectors(String[] directors) {
        this.directors = directors;
    }

    public String getEpImgFlag() {
        return epImgFlag;
    }

    public void setEpImgFlag(String epImgFlag) {
        this.epImgFlag = epImgFlag;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public String[] getGuestStars() {
        return guestStars;
    }

    public void setGuestStars(String[] guestStars) {
        this.guestStars = guestStars;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String[] getWriters() {
        return writers;
    }

    public void setWriters(String[] writers) {
        this.writers = writers;
    }

    public String getAbsoluteNumber() {
        return absoluteNumber;
    }

    public void setAbsoluteNumber(String absoluteNumber) {
        this.absoluteNumber = absoluteNumber;
    }

    public int getAirsAfterSeason() {
        return airsAfterSeason;
    }

    public void setAirsAfterSeason(int airsAfterSeason) {
        this.airsAfterSeason = airsAfterSeason;
    }

    public int getAirsBeforeSeason() {
        return airsBeforeSeason;
    }

    public void setAirsBeforeSeason(int airsBeforeSeason) {
        this.airsBeforeSeason = airsBeforeSeason;
    }

    public int getAirsBeforeEpisode() {
        return airsBeforeEpisode;
    }

    public void setAirsBeforeEpisode(int airsBeforeEpisode) {
        this.airsBeforeEpisode = airsBeforeEpisode;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }
}
