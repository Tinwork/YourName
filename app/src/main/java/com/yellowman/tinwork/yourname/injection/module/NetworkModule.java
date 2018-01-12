package com.yellowman.tinwork.yourname.injection.module;

import android.content.Context;

import com.yellowman.tinwork.yourname.network.api.search.SearchEpisodes;
import com.yellowman.tinwork.yourname.network.api.search.SearchSeries;
import com.yellowman.tinwork.yourname.network.api.series.EpisodeData;
import com.yellowman.tinwork.yourname.network.api.series.EpisodeSummary;
import com.yellowman.tinwork.yourname.network.api.series.FilterSeries;
import com.yellowman.tinwork.yourname.network.api.series.ImagesSeries;
import com.yellowman.tinwork.yourname.network.api.series.ListEpisodes;
import com.yellowman.tinwork.yourname.network.api.series.SingleSerie;
import com.yellowman.tinwork.yourname.network.api.user.GetUser;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;
import com.yellowman.tinwork.yourname.realm.decorator.ActorRealmDecorator;
import com.yellowman.tinwork.yourname.realm.decorator.FavoriteRealmDecorator;
import com.yellowman.tinwork.yourname.realm.decorator.UserRealmDecorator;
import com.yellowman.tinwork.yourname.realm.manager.CommonManager;
import com.yellowman.tinwork.yourname.realm.decorator.SeriesRealmDecorator;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Marc Intha-amnouay on 03/01/2018.
 * Created by Didier Youn on 03/01/2018.
 * Created by Abdel-Atif Mabrouck on 03/01/2018.
 * Created by Antoine Renault on 03/01/2018.
 *
 */

@Module
public class NetworkModule {

    private Context ctx;

    /**
     * Network Module::Constructor
     *
     * @param ctx Application Context
     */
    public NetworkModule(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * Provide Search Series
     *
     * @return SeriesRealmDecorator
     */
    @Provides @Named("SearchSeries")
    @Singleton
    SeriesRealmDecorator provideSearchSeries() {
        return new SeriesRealmDecorator(ctx);
    }

    /**
     * Provide Series List Actors
     *
     * @return ActorRealmDecorator
     */
    @Provides @Named("ListActors")
    @Singleton
    ActorRealmDecorator provideSeriesListActors() {
        return new ActorRealmDecorator(ctx);
    }

    /**
     * Fetch Get User
     *
     * @return Fetch instance
     */
    @Provides @Named("FetchGetUser")
    @Singleton
    UserRealmDecorator provideFetchGetUser() { return new UserRealmDecorator(ctx); }

    /**
     * Provide Fetch Favorite
     *
     * @return Favorite Realm Decorator
     */
    @Provides @Named("FetchFavorite")
    @Singleton
    FavoriteRealmDecorator provideFetchFavorite() {
        return new FavoriteRealmDecorator(ctx);
    }

    /**
     * Provide Search Episodes
     *
     * @return Fetch instance
     */
    @Provides @Named("SearchEpisodes")
    @Singleton
    Fetch provideSearchEpisodes() {
        return new SearchEpisodes(ctx);
    }

    /**
     * Provide Series Episode
     *
     * @return Fetch instance
     */
    @Provides @Named("EpisodeData")
    @Singleton
    Fetch provideSeriesEpisode() {
        return new EpisodeData(ctx);
    }

    /**
     * Provide Series Episode Summary
     *
     * @return Fetch instance
     */
    @Provides @Named("EpisodeSummary")
    @Singleton
    Fetch provideSeriesEpisodeSummary() {
        return new EpisodeSummary(ctx);
    }

    /**
     * Provide Series Filter Series
     *
     * @return Fetch instance
     */
    @Provides @Named("FilterSeries")
    @Singleton
    Fetch provideSeriesFilterSeries() {
        return new FilterSeries(ctx);
    }

    /**
     * Provide Series Image Series
     *
     * @return Fetch instance
     */
    @Provides @Named("ImageSeries")
    @Singleton
    Fetch provideSeriesImageSeries() {
        return new ImagesSeries(ctx);
    }

    /**
     * Provide Series List Episodes
     *
     * @return Fetch instance
     */
    @Provides @Named("ListEpisodes")
    @Singleton
    Fetch provideSeriesListEpisodes() {
        return new ListEpisodes(ctx);
    }

    /**
     * Provide Series Single Serie
     *
     * @return Fetch instance
     */
    @Provides @Named("SingleSerie")
    @Singleton
    Fetch provideSeriesSingleSerie() {
        return new SingleSerie(ctx);
    }

    /**
     * Provide Fetch Search Series
     *
     * @return Fetch instance
     */
    @Provides @Named("FetchSearchSeries")
    @Singleton
    Fetch provideFetchSearchSeries() { return new SearchSeries(ctx); }
}
