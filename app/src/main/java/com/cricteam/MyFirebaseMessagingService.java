package com.cricteam;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Amar on 9/14/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
       Log.e("message", remoteMessage.getData().toString());
        sendLocalAppUpdateNotification();
    }
    private void sendLocalAppUpdateNotification() {
        Intent intent= new Intent(new Intent());
        PendingIntent pendingIntent= PendingIntent.getActivity(this,100,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,"message")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("test"))
                .setContentText("test1");
        mBuilder.setAutoCancel(true);
        mBuilder.setColor(ContextCompat.getColor(this,R.color.colorAccent));
        mBuilder.setContentIntent(pendingIntent);
        ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).notify(100,mBuilder.build());

    }
}
