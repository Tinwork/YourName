package com.yellowman.tinwork.yourname.application;

import android.app.Application;

import com.yellowman.tinwork.yourname.injection.component.DaggerNetworkComponent;
import com.yellowman.tinwork.yourname.injection.component.NetworkComponent;
import com.yellowman.tinwork.yourname.injection.module.NetworkModule;

import dagger.internal.DaggerCollections;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Marc Intha-amnouay on 02/01/2018.
 * Created by Didier Youn on 02/01/2018.
 * Created by Abdel-Atif Mabrouck on 02/01/2018.
 * Created by Antoine Renault on 02/01/2018.
 */

public class YourName extends Application {

    private NetworkComponent mNetworkComponent;

    /**
     * On Create
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("yourname.db")
                .build();
        Realm.setDefaultConfiguration(config);

        mNetworkComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(getApplicationContext()))
                .build();
    }

    /**
     * Get mNetwork Component
     *
     * @return
     */
    public NetworkComponent getmNetworkComponent() {
        return mNetworkComponent;
    }
}
