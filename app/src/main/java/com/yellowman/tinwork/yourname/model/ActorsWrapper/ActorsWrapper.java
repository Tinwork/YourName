package com.yellowman.tinwork.yourname.model.ActorsWrapper;

import com.yellowman.tinwork.yourname.model.Actors;

import java.util.List;

/**
 * Created by abdel-latifmabrouck on 11/12/2017.
 */

public class ActorsWrapper {
    private List<Actors> data;

    /**
     * Set Data
     * @param actors
     */
    public void setData(List<Actors> actors) {
        this.data = actors;
    }

    /**
     * Get Data
     * @return
     */
    public List<Actors> getData() {
        return this.data;
    }
}
