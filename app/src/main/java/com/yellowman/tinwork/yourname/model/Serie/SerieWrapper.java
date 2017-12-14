package com.yellowman.tinwork.yourname.model.Serie;

import com.yellowman.tinwork.yourname.model.Series;

/**
 * Created by Marc Intha-amnouay on 11/12/2017.
 * Created by Didier Youn on 11/12/20177.
 * Created by Abdel-Latif Mabrouck on 11/12/2017.
 * Created by Antoine Renault on 11/12/2017.
 */

public class SerieWrapper {
    Series data;

    /**
     * Set Data
     *
     * @param series Series
     */
    public void setData(Series series) {
        this.data = series;
    }

    /**
     * Get Data
     *
     * @return Series
     */
    public Series getData() {
        return this.data;
    }
}
