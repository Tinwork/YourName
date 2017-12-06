package com.yellowman.tinwork.yourname.UIKit.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yellowman.tinwork.yourname.UIKit.adapters.TrendAdapter;

/**
 * Created by Marc Intha-amnouay on 06/12/2017.
 * Created by Didier Youn on 06/12/2017.
 * Created by Abdel-Atif Mabrouck on 06/12/2017.
 * Created by Antoine Renault on 06/12/2017.
 */

public class FragmentManager {
    // Set Class fields
    private final RecyclerView recyclerView;
    private final RecyclerView.Adapter vAdapter;
    private final RecyclerView.LayoutManager vLayout;

    /**
     * FragmentManager::Constructor
     * @param v
     * @param ctx
     */
    public FragmentManager(RecyclerView v, Context ctx, String[] dataset) {
        recyclerView = v;
        vLayout  = new LinearLayoutManager(ctx);
        vAdapter = new TrendAdapter(dataset);
    }

    /**
     * Set Fix Size
     * @void
     */
    public void setFixSize() {
        recyclerView.setHasFixedSize(true);
    }

    /**
     * Set Layout
     *      Please Ly....... help me ~~~~~~~~~~
     * @void
     */
    public void setLayout() {
        recyclerView.setLayoutManager(vLayout);
        recyclerView.setAdapter(vAdapter);
    }
}
