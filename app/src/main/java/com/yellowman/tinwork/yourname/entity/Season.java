package com.yellowman.tinwork.yourname.entity;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Marc Intha-amnouay on 02/01/2018.
 * Created by Didier Youn on 02/01/2018.
 * Created by Abdel-Atif Mabrouck on 02/01/2018.
 * Created by Antoine Renault on 02/01/2018.
 */

public class Season extends RealmObject {
    private RealmList<Episode> episodes;

    /**
     * Get Episodes
     *
     * @return
     */
    public RealmList<Episode> getEpisodes() {
        return episodes;
    }

    /**
     * Set Episode
     *
     * @param episodes
     */
    public void setEpisodes(RealmList<Episode> episodes) {
        this.episodes = episodes;
    }
}
