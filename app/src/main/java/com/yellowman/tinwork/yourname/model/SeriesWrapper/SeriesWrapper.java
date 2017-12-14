package com.yellowman.tinwork.yourname.model.SeriesWrapper;

import com.yellowman.tinwork.yourname.model.Series;

/**
 * Created by abdel-latifmabrouck on 11/12/2017.
 */

public class SeriesWrapper {
    Series data;

    public void setData(Series series) {
        this.data = series;
    }

    public Series getData() {
        return this.data;
    }
}
