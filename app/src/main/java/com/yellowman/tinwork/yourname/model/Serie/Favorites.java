package com.yellowman.tinwork.yourname.model.Serie;

import com.yellowman.tinwork.yourname.entity.IdSeries;
import com.yellowman.tinwork.yourname.model.Series;

import java.util.List;

/**
 * Created by abdel-latifmabrouck on 19/12/2017.
 */

public class Favorites {
    private List<IdSeries> data;

    /**
     * Set Data
     * @param series
     */
    public void setData(List<IdSeries> series) {
        this.data = series;
    }

    /**
     * Get Data
     * @return
     */
    public List<IdSeries> getData() {
        return this.data;
    }
}
