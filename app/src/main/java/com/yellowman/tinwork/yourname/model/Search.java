package com.yellowman.tinwork.yourname.model;

import java.util.List;

/**
 * Created by Marc Intha-amnouay on 23/11/2017.
 * Created by Didier Youn on 23/11/2017.
 * Created by Abdel-Latif Mabrouck on 23/11/2017.
 * Created by Antoine Renault on 23/11/2017.
 */

public class Search {
    private List<Series> data;

    /**
     * Set Data
     * @param series
     */
    public void setData(List<Series> series) {
        this.data = series;
    }

    /**
     * Get Data
     * @return
     */
    public List<Series> getData() {
        return this.data;
    }
}
