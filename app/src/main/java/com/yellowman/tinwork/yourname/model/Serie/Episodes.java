package com.yellowman.tinwork.yourname.model.Serie;

import com.yellowman.tinwork.yourname.entity.Episode;
import java.util.List;

/**
 * Class Episodes
 *
 * @author                 Didier Youn <didier.youn@gmail.com>,
 *                         Marc Intha-amnouay <marc.inthaamnouay@gmail.com>,
 *                         Abdel-Latif Mabrouck <amatroud0@gmail.com>,
 *                         Antoine Renault <antoine.renault.mmi@gmail.com>.
 * @link                   https://github.com/Tinwork/YourName
 */
public class Episodes
{
    private List<Episode> data;

    /**
     * Get Data
     *
     * @return List
     */
    public List<Episode> getData()
    {
        return this.data;
    }

    /**
     * Set Data
     *
     * @param episodes List<Episode>
     */
    public void setData(List<Episode> episodes)
    {
        this.data = episodes;
    }
}
