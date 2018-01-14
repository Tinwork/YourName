package com.yellowman.tinwork.yourname.injection.component;

import com.yellowman.tinwork.yourname.activities.episodeDetail.EpisodeListActivity;
import com.yellowman.tinwork.yourname.activities.episodeDetail.fragments.EpisodesListFragment;
import com.yellowman.tinwork.yourname.activities.filmDetail.FilmDetailsActivity;
import com.yellowman.tinwork.yourname.activities.filmDetail.fragments.FilmContentFragment;
import com.yellowman.tinwork.yourname.activities.filmDetail.fragments.FilmEpisodesFragment;
import com.yellowman.tinwork.yourname.activities.home.HomeActivity;
import com.yellowman.tinwork.yourname.activities.home.fragments.FavoriteFragment;
import com.yellowman.tinwork.yourname.activities.home.fragments.RandomFragment;
import com.yellowman.tinwork.yourname.activities.home.fragments.TrendingFragment;
import com.yellowman.tinwork.yourname.activities.login.LoginActivity;
import com.yellowman.tinwork.yourname.activities.search.SearchResultsActivity;
import com.yellowman.tinwork.yourname.activities.singleEpisode.EpisodeDetailActivity;
import com.yellowman.tinwork.yourname.injection.module.AppModule;
import com.yellowman.tinwork.yourname.injection.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Marc Intha-amnouay on 03/01/2018.
 * Created by Didier Youn on 03/01/2018.
 * Created by Abdel-Atif Mabrouck on 03/01/2018.
 * Created by Antoine Renault on 03/01/2018.
 *
 * Injector Presenter
 */

@Singleton
@Component(modules={AppModule.class, NetworkModule.class})
public interface NetworkComponent {
    // Home activity & Fragments
    void inject(HomeActivity activity);
    void inject(TrendingFragment fragment);
    void inject(RandomFragment fragment);
    void inject(FavoriteFragment fragment);

    // Film Details Activity & Fragments
    void inject(FilmDetailsActivity  activity);
    void inject(FilmContentFragment  fragment);
    void inject(FilmEpisodesFragment fragment);

    // Episode Detail fragment Activity & Fragments
    void inject(EpisodeListActivity activity);
    void inject(EpisodesListFragment fragment);

    // Login
    void inject(LoginActivity activity);

    // Search
    void inject(SearchResultsActivity activity);

    // Single Episode
    void inject(EpisodeDetailActivity activity);
}