package com.yellowman.tinwork.yourname.injection;

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

    /**
     * Provide Search Series
     *
     * @param ctx
     * @return
     */
    @Provides @Named("SearchSeries")
    @Singleton
    Fetch provideSearchSeries(Context ctx) {
        return new SearchSeries(ctx);
    }

    /**
     * Provide Search Episodes
     *
     * @param ctx
     * @return
     */
    @Provides @Named("SearchEpisodes")
    @Singleton
    Fetch provideSearchEpisodes(Context ctx) {
        return new SearchEpisodes(ctx);
    }

    /**
     * Provide Series Episode
     *
     * @param ctx
     * @return
     */
    @Provides @Named("EpisodeData")
    @Singleton
    Fetch provideSeriesEpisode(Context ctx) {
        return new EpisodeData(ctx);
    }

    /**
     * Provide Series Episode Summary
     *
     * @param ctx
     * @return
     */
    @Provides @Named("EpisodeSummary")
    @Singleton
    Fetch provideSeriesEpisodeSummary(Context ctx) {
        return new EpisodeSummary(ctx);
    }

    /**
     * Provide Series Filter Series
     *
     * @param ctx
     * @return
     */
    @Provides @Named("FilterSeries")
    @Singleton
    Fetch provideSeriesFilterSeries(Context ctx) {
        return new FilterSeries(ctx);
    }

    /**
     * Provide Series Get Serie
     *
     * @param ctx
     * @return
     */
    @Provides @Named("GetSerie")
    @Singleton
    Fetch provideSeriesGetSeries(Context ctx) {
        return new GetSerie(ctx);
    }

    /**
     * Provide Series Image Series
     *
     * @param ctx
     * @return
     */
    @Provides @Named("ImageSeries")
    @Singleton
    Fetch provideSeriesImageSeries(Context ctx) {
        return new ImagesSeries(ctx);
    }

    /**
     * Provide Series List Actors
     *
     * @param ctx
     * @return
     */
    @Provides @Named("ListActors")
    @Singleton
    Fetch provideSeriesListActors(Context ctx) {
        return new ListActors(ctx);
    }

    /**
     * Provide Series List Episodes
     *
     * @param ctx
     * @return
     */
    @Provides @Named("ListEpisodes")
    @Singleton
    Fetch provideSeriesListEpisodes(Context ctx) {
        return new ListEpisodes(ctx);
    }

    /**
     * Provide Series Single Serie
     *
     * @param ctx
     * @return
     */
    @Provides @Named("SingleSerie")
    @Singleton
    Fetch provideSeriesSingleSerie(Context ctx) {
        return new SingleSerie(ctx);
    }
}
