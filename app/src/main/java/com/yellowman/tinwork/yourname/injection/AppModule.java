package com.yellowman.tinwork.yourname.injection;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Marc Intha-amnouay on 03/01/2018.
 * Created by Didier Youn on 03/01/2018.
 * Created by Abdel-Atif Mabrouck on 03/01/2018.
 * Created by Antoine Renault on 03/01/2018.
 *
 *
 * This singleton will help Dagger to provide every modules that need to be inject throughout the App
 */
@Module
public class AppModule {

    Application mApplication;

    /**
     * App Module
     *
     * @param app
     */
    public AppModule(Application app) {
        this.mApplication = app;
    }

    /**
     * Provides Application
     *
     * @return
     */
    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }
}
