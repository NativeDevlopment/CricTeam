package com.cricteam;

import android.app.Service;
import android.util.Log;

import com.cricteam.utils.AppConstants;
import com.cricteam.utils.CommonUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Amar on 9/14/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private String TAG=MyFirebaseInstanceIDService.class.getName();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        CommonUtils.savePreferencesString(this, AppConstants.DEVICE_TOKEN,refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }
}
