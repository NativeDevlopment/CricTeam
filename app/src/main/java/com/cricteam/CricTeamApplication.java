package com.cricteam;

import android.app.Application;

import com.google.firebase.FirebaseApp;

/**
 * Created by Amar on 8/22/2017.
 */

public class CricTeamApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
