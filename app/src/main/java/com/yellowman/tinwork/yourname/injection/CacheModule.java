package com.yellowman.tinwork.yourname.injection;

import com.yellowman.tinwork.yourname.realm.manager.CommonManager;

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
public class CacheModule {

    /**
     * Provides Realm
     *
     * @return
     */
    @Provides
    @Singleton
    CommonManager providesRealm() {
        CommonManager realmManager = new CommonManager();

        return realmManager;
    }
}
