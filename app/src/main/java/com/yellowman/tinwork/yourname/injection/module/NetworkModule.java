package com.yellowman.tinwork.yourname.injection.module;

import android.content.Context;

import com.yellowman.tinwork.yourname.network.api.search.SearchEpisodes;
import com.yellowman.tinwork.yourname.network.api.search.SearchSeries;
import com.yellowman.tinwork.yourname.network.api.series.EpisodeData;
import com.yellowman.tinwork.yourname.network.api.series.EpisodeSummary;
import com.yellowman.tinwork.yourname.network.api.series.FilterSeries;
import com.yellowman.tinwork.yourname.network.api.series.GetSerie;
import com.yellowman.tinwork.yourname.network.api.series.ImagesSeries;
import com.yellowman.tinwork.yourname.network.api.series.ListActors;
import com.yellowman.tinwork.yourname.network.api.series.ListEpisodes;
import com.yellowman.tinwork.yourname.network.api.series.SingleSerie;
import com.yellowman.tinwork.yourname.network.fetch.Fetch;

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
 * Well...
 */

@Module
public class NetworkModule {

    private Context ctx;

    /**
     * Network Module::Constructor
     *
     * @param ctx
     */
    public NetworkModule(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * Provide Search Series
     *
     * @return
     */
    @Provides @Named("SearchSeries")
    @Singleton
    Fetch provideSearchSeries() {
        return new SearchSeries(ctx);
    }

    /**
     * Provide Search Episodes
     *
     * @return
     */
    @Provides @Named("SearchEpisodes")
    @Singleton
    Fetch provideSearchEpisodes() {
        return new SearchEpisodes(ctx);
    }

    /**
     * Provide Series Episode
     *
     * @return
     */
    @Provides @Named("EpisodeData")
    @Singleton
    Fetch provideSeriesEpisode() {
        return new EpisodeData(ctx);
    }

    /**
     * Provide Series Episode Summary
     *
     * @return
     */
    @Provides @Named("EpisodeSummary")
    @Singleton
    Fetch provideSeriesEpisodeSummary() {
        return new EpisodeSummary(ctx);
    }

    /**
     * Provide Series Filter Series
     *
     * @return
     */
    @Provides @Named("FilterSeries")
    @Singleton
    Fetch provideSeriesFilterSeries() {
        return new FilterSeries(ctx);
    }

    /**
     * Provide Series Get Serie
     *
     * @return
     */
    @Provides @Named("GetSerie")
    @Singleton
    Fetch provideSeriesGetSeries() {
        return new GetSerie(ctx);
    }

    /**
     * Provide Series Image Series
     *
     * @return
     */
    @Provides @Named("ImageSeries")
    @Singleton
    Fetch provideSeriesImageSeries() {
        return new ImagesSeries(ctx);
    }

    /**
     * Provide Series List Actors
     *
     * @return
     */
    @Provides @Named("ListActors")
    @Singleton
    Fetch provideSeriesListActors() {
        return new ListActors(ctx);
    }

    /**
     * Provide Series List Episodes
     *
     * @return
     */
    @Provides @Named("ListEpisodes")
    @Singleton
    Fetch provideSeriesListEpisodes() {
        return new ListEpisodes(ctx);
    }

    /**
     * Provide Series Single Serie
     *
     * @return
     */
    @Provides @Named("SingleSerie")
    @Singleton
    Fetch provideSeriesSingleSerie() {
        return new SingleSerie(ctx);
    }
}
