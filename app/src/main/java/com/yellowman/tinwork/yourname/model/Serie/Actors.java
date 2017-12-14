package com.yellowman.tinwork.yourname.model.Serie;

import com.yellowman.tinwork.yourname.entity.Actor;

import java.util.List;

/**
 * Created by abdel-latifmabrouck on 11/12/2017.
 */

public class Actors {
    private List<Actor> data;

    /**
     * Set Data
     * @param actors
     */
    public void setData(List<Actor> actors) {
        this.data = actors;
    }

    /**
     * Get Data
     * @return
     */
    public List<Actor> getData() {
        return this.data;
    }
}
