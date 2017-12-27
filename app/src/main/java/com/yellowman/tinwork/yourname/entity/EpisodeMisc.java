package com.yellowman.tinwork.yourname.entity;

/**
 * Created by Marc Intha-amnouay on 27/12/2017.
 * Created by Didier Youn on 27/12/2017.
 * Created by Abdel-Atif Mabrouck on 27/12/2017.
 * Created by Antoine Renault on 27/12/2017.
 */

public class EpisodeMisc {
    private String[] airedSeasons;
    private String airedEpisodes;

    /**
     *
     * @return
     */
    public String[] getAiredSeasons() {
        return airedSeasons;
    }

    /**
     *
     * @param airedSeasons
     */
    public void setAiredSeasons(String[] airedSeasons) {
        this.airedSeasons = airedSeasons;
    }

    /**
     *
     * @return
     */
    public String getAiredEpisodes() {
        return airedEpisodes;
    }

    /**
     *
     * @param airedEpisodes
     */
    public void setAiredEpisodes(String airedEpisodes) {
        this.airedEpisodes = airedEpisodes;
    }
}
