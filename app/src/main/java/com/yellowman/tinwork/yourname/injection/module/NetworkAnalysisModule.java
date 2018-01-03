package com.yellowman.tinwork.yourname.injection.module;

import android.content.Context;

import com.yellowman.tinwork.yourname.network.helper.ConnectivityHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Marc Intha-amnouay on 03/01/2018.
 * Created by Didier Youn on 03/01/2018.
 * Created by Abdel-Atif Mabrouck on 03/01/2018.
 * Created by Antoine Renault on 03/01/2018.
 */
@Module
public class NetworkAnalysisModule {

    Context ctx;

    /**
     * Network Analysis Module::Constructor
     *
     * @param ctx
     */
    public NetworkAnalysisModule(Context ctx) {
        this.ctx = ctx;
    }

    /**
     * Provide Connectivity Helper
     *
     * @return
     */
    @Provides
    @Singleton
    ConnectivityHelper provideConnectivityHelper() {
        return new ConnectivityHelper(ctx);
    }
}
