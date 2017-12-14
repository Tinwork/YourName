package com.yellowman.tinwork.yourname.model.Serie;

import com.yellowman.tinwork.yourname.entity.Episode;

/**
 * Created by Marc Intha-amnouay on 14/12/2017.
 * Created by Didier Youn on 14/12/2017.
 * Created by Abdel-Atif Mabrouck on 14/12/2017.
 * Created by Antoine Renault on 14/12/2017.
 */

public class SingleEpisodeWrapper {
    public Episode data;

    /**
     * Set Dat
     *
     * @param episode
     */
    public void setData(Episode episode) {
        this.data = episode;
    }

    /**
     * Get Data
     *
     * @return
     */
    public Episode getData() {
        return data;
    }
}
