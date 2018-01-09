package com.yellowman.tinwork.yourname.UIKit.misc;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.yellowman.tinwork.yourname.UIKit.holder.FavoriteHolder;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;

/**
 * Created by Marc Intha-amnouay on 07/01/2018.
 * Created by Didier Youn on 07/01/2018.
 * Created by Abdel-Atif Mabrouck on 07/01/2018.
 * Created by Antoine Renault on 07/01/2018.
 */

public class SwipeController extends ItemTouchHelper.Callback {

    private final CommonManager realmManager;

    /**
     * Swipe Controller::Constructor
     *
     */
    public SwipeController() {
        this.realmManager = new CommonManager();
    }

    /**
     * Get Movement Flags
     *
     * @param recyclerView Recycler View
     * @param viewHolder View Holder
     * @return int
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT | RIGHT);
    }

    /**
     * On Move
     *
     * @param recyclerView Recycler View
     * @param viewHolder View Holder
     * @param target Element target
     * @return boolean
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * On Swiped
     *
     * @param viewHolder View Holder
     * @param direction Swipe direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        FavoriteHolder holder = (FavoriteHolder) viewHolder;
        // Ok i know this is dirty, but we don't have much time
        String id = holder.getSeriesFromHolder();
        realmManager.removeSeriesById("id", id);
    }
}
