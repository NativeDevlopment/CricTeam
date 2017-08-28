package com.cricteam.imagepicker;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amar on 8/16/2017.
 */

public class CommonUtils {
    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.

  static   String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
          };




    public static   boolean checkPermissions(Activity mContext) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(mContext,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mContext, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }
    public static boolean getGpsEnabled(Context mContext) {
        boolean gpsEnabled;
        LocationManager locationManager= (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        try {
            gpsEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            gpsEnabled=false;
            e.printStackTrace();
        }
        return gpsEnabled;
    }
}
