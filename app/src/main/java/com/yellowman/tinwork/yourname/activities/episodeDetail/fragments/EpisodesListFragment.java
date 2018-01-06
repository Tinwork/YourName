package com.yellowman.tinwork.yourname.activities.episodeDetail.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yellowman.tinwork.yourname.R;
import com.yellowman.tinwork.yourname.UIKit.adapters.EpisodeAdapter;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentBinder;
import com.yellowman.tinwork.yourname.UIKit.iface.FragmentListener;
import com.yellowman.tinwork.yourname.entity.Episode;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Marc Intha-amnouay on 29/12/2017.
 * Created by Didier Youn on 29/12/2017.
 * Created by Abdel-Atif Mabrouck on 29/12/2017.
 * Created by Antoine Renault on 29/12/2017.
 */

public class EpisodesListFragment extends Fragment implements FragmentBinder {

    private RecyclerView recyclerView;

    /**
     * Episodes List Fragment::Constructor
     *
     */
    public EpisodesListFragment() {}

    /**
     * On Create View
     *
     * @param inflater Layout Inflater
     * @param container ViewGroup
     * @param savedInstanceBundle Bundle
     * @return View
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View episodesList = inflater.inflate(R.layout.fragment_episodes_season, container, false);
        // Create the recycler view
        this.recyclerView = episodesList.findViewById(R.id.episodes_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                episodesList.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        ));
        recyclerView.setHasFixedSize(true);

        return episodesList;
    }

    /**
     * Bind Recycler View
     *
     * @param data List of any Type
     */
    @Override
    public void bindRecycleView(List<?> data) {
        if (data == null) {
            recyclerView.setAdapter(null);
        } else {
            // /!\ We do an implicit cast
            EpisodeAdapter adapter = new EpisodeAdapter((List<Episode>) data);
            recyclerView.setAdapter(adapter);
        }
    }
}
