package com.cricteam;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.google.firebase.FirebaseApp;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Amar on 8/22/2017.
 */

public class CricTeamApplication extends Application {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
  public  RealmConfiguration config;
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        Realm.init(this);
         config = new RealmConfiguration.Builder().name("cricketKhelo.realm").schemaVersion(1).build();
        Realm.setDefaultConfiguration(config);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
