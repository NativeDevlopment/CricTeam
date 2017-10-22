package com.cricteam.utils;

import android.content.Context;
import android.os.Build;

/**
 * Created by Amar on 8/17/2017.
 */

public class AppConstants {
    public  static final  String ADD_PLAYER="add player";
    public  static final  String USER_ID="user id";
    public  static final  String TEAM_ID="team id";
    public static final String DEVICE_TOKEN="deviceToken";
    public static final String DEVICE_ID="deviceId";
    public static final String IS_COMPLETE_USER="isCompleteUser";
    public static final String WK="WK";
    public static final String BAT="BAT";
    public static final String BALL="BALL";
    public static final String ISEDIT = "isEdit";
    public static final String UPDATE_PLAYER = "update player";
    public static final String TEAM_PREVIEW = "team_preview";
    public static final String MOBILE_NO="mobile no";
    public static final  String FROM_ACCOUNT_SCREEN="from account screen";
    public static final String PLAYER_LIST = "player_list";
    public static final String LOCATION_ADDRESS = "Location address";
  public static final   String PSEUDO_CODE_DEVICE_ID = "35" + //we make this look like a valid IMEI
            Build.BOARD.length()%10+ Build.BRAND.length()%10 +
            Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
            Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
            Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
            Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
            Build.TAGS.length()%10 + Build.TYPE.length()%10 +
            Build.USER.length()%10 ; //13 digits
    public static final String OTP="otp";
    public static final String LAST_ADDRESS = "last address";
    public static String CUURENT_LAT="current lat";
    public static String CUURENT_LANG="current lang";
}
