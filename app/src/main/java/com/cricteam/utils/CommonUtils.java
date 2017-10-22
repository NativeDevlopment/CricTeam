package com.cricteam.utils;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.media.ExifInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Amar on 8/16/2017.
 */

public class CommonUtils {
    public static List<Country> getLibraryMasterCountriesEnglish() {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country("af", "93", "Afghanistan"));
        countries.add(new Country("al", "355", "Albania"));
        countries.add(new Country("dz", "213", "Algeria"));
        countries.add(new Country("ad", "376", "Andorra"));
        countries.add(new Country("ao", "244", "Angola"));
        countries.add(new Country("ai", "1", "Anguilla"));
        countries.add(new Country("aq", "672", "Antarctica"));
        countries.add(new Country("ag", "1", "Antigua and Barbuda"));
        countries.add(new Country("ar", "54", "Argentina"));
        countries.add(new Country("am", "374", "Armenia"));
        countries.add(new Country("aw", "297", "Aruba"));
        countries.add(new Country("au", "61", "Australia"));
        countries.add(new Country("at", "43", "Austria"));
        countries.add(new Country("az", "994", "Azerbaijan"));
        countries.add(new Country("bs", "1", "Bahamas"));
        countries.add(new Country("bh", "973", "Bahrain"));
        countries.add(new Country("bd", "880", "Bangladesh"));
        countries.add(new Country("bb", "1", "Barbados"));
        countries.add(new Country("by", "375", "Belarus"));
        countries.add(new Country("be", "32", "Belgium"));
        countries.add(new Country("bz", "501", "Belize"));
        countries.add(new Country("bj", "229", "Benin"));
        countries.add(new Country("bm", "1", "Bermuda"));
        countries.add(new Country("bt", "975", "Bhutan"));
        countries.add(new Country("bo", "591", "Bolivia, Plurinational State Of"));
        countries.add(new Country("ba", "387", "Bosnia And Herzegovina"));
        countries.add(new Country("bw", "267", "Botswana"));
        countries.add(new Country("br", "55", "Brazil"));
        countries.add(new Country("vg", "1", "British Virgin Islands"));
        countries.add(new Country("bn", "673", "Brunei Darussalam"));
        countries.add(new Country("bg", "359", "Bulgaria"));
        countries.add(new Country("bf", "226", "Burkina Faso"));
        countries.add(new Country("mm", "95", "Myanmar"));
        countries.add(new Country("bi", "257", "Burundi"));
        countries.add(new Country("kh", "855", "Cambodia"));
        countries.add(new Country("cm", "237", "Cameroon"));
        countries.add(new Country("ca", "1", "Canada"));
        countries.add(new Country("cv", "238", "Cape Verde"));
        countries.add(new Country("ky", "1", "Cayman Islands"));
        countries.add(new Country("cf", "236", "Central African Republic"));
        countries.add(new Country("td", "235", "Chad"));
        countries.add(new Country("cl", "56", "Chile"));
        countries.add(new Country("cn", "86", "China"));
        countries.add(new Country("cx", "61", "Christmas Island"));
        countries.add(new Country("cc", "61", "Cocos (keeling) Islands"));
        countries.add(new Country("co", "57", "Colombia"));
        countries.add(new Country("km", "269", "Comoros"));
        countries.add(new Country("cg", "242", "Congo"));
        countries.add(new Country("cd", "243", "Congo, The Democratic Republic Of The"));
        countries.add(new Country("ck", "682", "Cook Islands"));
        countries.add(new Country("cr", "506", "Costa Rica"));
        countries.add(new Country("hr", "385", "Croatia"));
        countries.add(new Country("cu", "53", "Cuba"));
        countries.add(new Country("cy", "357", "Cyprus"));
        countries.add(new Country("cz", "420", "Czech Republic"));
        countries.add(new Country("dk", "45", "Denmark"));
        countries.add(new Country("dj", "253", "Djibouti"));
        countries.add(new Country("dm", "1", "Dominica"));
        countries.add(new Country("do", "1", "Dominican Republic"));
        countries.add(new Country("tl", "670", "Timor-leste"));
        countries.add(new Country("ec", "593", "Ecuador"));
        countries.add(new Country("eg", "20", "Egypt"));
        countries.add(new Country("sv", "503", "El Salvador"));
        countries.add(new Country("gq", "240", "Equatorial Guinea"));
        countries.add(new Country("er", "291", "Eritrea"));
        countries.add(new Country("ee", "372", "Estonia"));
        countries.add(new Country("et", "251", "Ethiopia"));
        countries.add(new Country("fk", "500", "Falkland Islands (malvinas)"));
        countries.add(new Country("fo", "298", "Faroe Islands"));
        countries.add(new Country("fj", "679", "Fiji"));
        countries.add(new Country("fi", "358", "Finland"));
        countries.add(new Country("fr", "33", "France"));
        countries.add(new Country("gf", "594", "French Guyana"));
        countries.add(new Country("pf", "689", "French Polynesia"));
        countries.add(new Country("ga", "241", "Gabon"));
        countries.add(new Country("gm", "220", "Gambia"));
        countries.add(new Country("ge", "995", "Georgia"));
        countries.add(new Country("de", "49", "Germany"));
        countries.add(new Country("gh", "233", "Ghana"));
        countries.add(new Country("gi", "350", "Gibraltar"));
        countries.add(new Country("gr", "30", "Greece"));
        countries.add(new Country("gl", "299", "Greenland"));
        countries.add(new Country("gd", "1", "Grenada"));
        countries.add(new Country("gt", "502", "Guatemala"));
        countries.add(new Country("gn", "224", "Guinea"));
        countries.add(new Country("gw", "245", "Guinea-bissau"));
        countries.add(new Country("gy", "592", "Guyana"));
        countries.add(new Country("ht", "509", "Haiti"));
        countries.add(new Country("hn", "504", "Honduras"));
        countries.add(new Country("hk", "852", "Hong Kong"));
        countries.add(new Country("hu", "36", "Hungary"));
        countries.add(new Country("in", "91", "India"));
        countries.add(new Country("id", "62", "Indonesia"));
        countries.add(new Country("ir", "98", "Iran, Islamic Republic Of"));
        countries.add(new Country("iq", "964", "Iraq"));
        countries.add(new Country("ie", "353", "Ireland"));
        countries.add(new Country("im", "44", "Isle Of Man"));
        countries.add(new Country("il", "972", "Israel"));
        countries.add(new Country("ps", "970", "Palestine"));
        countries.add(new Country("it", "39", "Italy"));
        countries.add(new Country("ci", "225", "Côte D\'ivoire"));
        countries.add(new Country("jm", "1", "Jamaica"));
        countries.add(new Country("jp", "81", "Japan"));
        countries.add(new Country("jo", "962", "Jordan"));
        countries.add(new Country("kz", "7", "Kazakhstan"));
        countries.add(new Country("ke", "254", "Kenya"));
        countries.add(new Country("ki", "686", "Kiribati"));
        countries.add(new Country("kw", "965", "Kuwait"));
        countries.add(new Country("kg", "996", "Kyrgyzstan"));
        countries.add(new Country("la", "856", "Lao People\'s Democratic Republic"));
        countries.add(new Country("lv", "371", "Latvia"));
        countries.add(new Country("lb", "961", "Lebanon"));
        countries.add(new Country("ls", "266", "Lesotho"));
        countries.add(new Country("lr", "231", "Liberia"));
        countries.add(new Country("ly", "218", "Libya"));
        countries.add(new Country("li", "423", "Liechtenstein"));
        countries.add(new Country("lt", "370", "Lithuania"));
        countries.add(new Country("lu", "352", "Luxembourg"));
        countries.add(new Country("mo", "853", "Macao"));
        countries.add(new Country("mk", "389", "Macedonia, The Former Yugoslav Republic Of"));
        countries.add(new Country("mg", "261", "Madagascar"));
        countries.add(new Country("mw", "265", "Malawi"));
        countries.add(new Country("my", "60", "Malaysia"));
        countries.add(new Country("mv", "960", "Maldives"));
        countries.add(new Country("ml", "223", "Mali"));
        countries.add(new Country("mt", "356", "Malta"));
        countries.add(new Country("mh", "692", "Marshall Islands"));
        countries.add(new Country("mq", "596", "Martinique"));
        countries.add(new Country("mr", "222", "Mauritania"));
        countries.add(new Country("mu", "230", "Mauritius"));
        countries.add(new Country("yt", "262", "Mayotte"));
        countries.add(new Country("mx", "52", "Mexico"));
        countries.add(new Country("fm", "691", "Micronesia, Federated States Of"));
        countries.add(new Country("md", "373", "Moldova, Republic Of"));
        countries.add(new Country("mc", "377", "Monaco"));
        countries.add(new Country("mn", "976", "Mongolia"));
        countries.add(new Country("ms", "1", "Montserrat"));
        countries.add(new Country("me", "382", "Montenegro"));
        countries.add(new Country("ma", "212", "Morocco"));
        countries.add(new Country("mz", "258", "Mozambique"));
        countries.add(new Country("na", "264", "Namibia"));
        countries.add(new Country("nr", "674", "Nauru"));
        countries.add(new Country("np", "977", "Nepal"));
        countries.add(new Country("nl", "31", "Netherlands"));
        countries.add(new Country("nc", "687", "New Caledonia"));
        countries.add(new Country("nz", "64", "New Zealand"));
        countries.add(new Country("ni", "505", "Nicaragua"));
        countries.add(new Country("ne", "227", "Niger"));
        countries.add(new Country("ng", "234", "Nigeria"));
        countries.add(new Country("nu", "683", "Niue"));
        countries.add(new Country("kp", "850", "North Korea"));
        countries.add(new Country("no", "47", "Norway"));
        countries.add(new Country("om", "968", "Oman"));
        countries.add(new Country("pk", "92", "Pakistan"));
        countries.add(new Country("pw", "680", "Palau"));
        countries.add(new Country("pa", "507", "Panama"));
        countries.add(new Country("pg", "675", "Papua New Guinea"));
        countries.add(new Country("py", "595", "Paraguay"));
        countries.add(new Country("pe", "51", "Peru"));
        countries.add(new Country("ph", "63", "Philippines"));
        countries.add(new Country("pn", "870", "Pitcairn"));
        countries.add(new Country("pl", "48", "Poland"));
        countries.add(new Country("pt", "351", "Portugal"));
        countries.add(new Country("pr", "1", "Puerto Rico"));
        countries.add(new Country("qa", "974", "Qatar"));
        countries.add(new Country("re", "262", "Réunion"));
        countries.add(new Country("ro", "40", "Romania"));
        countries.add(new Country("ru", "7", "Russian Federation"));
        countries.add(new Country("rw", "250", "Rwanda"));
        countries.add(new Country("bl", "590", "Saint Barthélemy"));
        countries.add(new Country("kn", "1", "Saint Kitts and Nevis"));
        countries.add(new Country("lc", "1", "Saint Lucia"));
        countries.add(new Country("vc", "1", "Saint Vincent & The Grenadines"));
        countries.add(new Country("ws", "685", "Samoa"));
        countries.add(new Country("sm", "378", "San Marino"));
        countries.add(new Country("st", "239", "Sao Tome And Principe"));
        countries.add(new Country("sa", "966", "Saudi Arabia"));
        countries.add(new Country("sn", "221", "Senegal"));
        countries.add(new Country("rs", "381", "Serbia"));
        countries.add(new Country("sc", "248", "Seychelles"));
        countries.add(new Country("sl", "232", "Sierra Leone"));
        countries.add(new Country("sg", "65", "Singapore"));
        countries.add(new Country("sx", "1", "Sint Maarten"));
        countries.add(new Country("sk", "421", "Slovakia"));
        countries.add(new Country("si", "386", "Slovenia"));
        countries.add(new Country("sb", "677", "Solomon Islands"));
        countries.add(new Country("so", "252", "Somalia"));
        countries.add(new Country("za", "27", "South Africa"));
        countries.add(new Country("kr", "82", "South Korea"));
        countries.add(new Country("es", "34", "Spain"));
        countries.add(new Country("lk", "94", "Sri Lanka"));
        countries.add(new Country("sh", "290", "Saint Helena, Ascension And Tristan Da Cunha"));
        countries.add(new Country("pm", "508", "Saint Pierre And Miquelon"));
        countries.add(new Country("sd", "249", "Sudan"));
        countries.add(new Country("sr", "597", "Suriname"));
        countries.add(new Country("sz", "268", "Swaziland"));
        countries.add(new Country("se", "46", "Sweden"));
        countries.add(new Country("ch", "41", "Switzerland"));
        countries.add(new Country("sy", "963", "Syrian Arab Republic"));
        countries.add(new Country("tw", "886", "Taiwan, Province Of China"));
        countries.add(new Country("tj", "992", "Tajikistan"));
        countries.add(new Country("tz", "255", "Tanzania, United Republic Of"));
        countries.add(new Country("th", "66", "Thailand"));
        countries.add(new Country("tg", "228", "Togo"));
        countries.add(new Country("tk", "690", "Tokelau"));
        countries.add(new Country("to", "676", "Tonga"));
        countries.add(new Country("tt", "1", "Trinidad & Tobago"));
        countries.add(new Country("tn", "216", "Tunisia"));
        countries.add(new Country("tr", "90", "Turkey"));
        countries.add(new Country("tm", "993", "Turkmenistan"));
        countries.add(new Country("tc", "1", "Turks and Caicos Islands"));
        countries.add(new Country("tv", "688", "Tuvalu"));
        countries.add(new Country("ae", "971", "United Arab Emirates"));
        countries.add(new Country("ug", "256", "Uganda"));
        countries.add(new Country("gb", "44", "United Kingdom"));
        countries.add(new Country("ua", "380", "Ukraine"));
        countries.add(new Country("uy", "598", "Uruguay"));
        countries.add(new Country("us", "1", "United States"));
        countries.add(new Country("vi", "1", "US Virgin Islands"));
        countries.add(new Country("uz", "998", "Uzbekistan"));
        countries.add(new Country("vu", "678", "Vanuatu"));
        countries.add(new Country("va", "39", "Holy See (vatican City State)"));
        countries.add(new Country("ve", "58", "Venezuela, Bolivarian Republic Of"));
        countries.add(new Country("vn", "84", "Viet Nam"));
        countries.add(new Country("wf", "681", "Wallis And Futuna"));
        countries.add(new Country("ye", "967", "Yemen"));
        countries.add(new Country("zm", "260", "Zambia"));
        countries.add(new Country("zw", "263", "Zimbabwe"));
        return countries;
    }
    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.

  static   String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.RECEIVE_SMS};




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

    /**
     * Is online boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }


    /**
     * Hide key pad.
     *
     * @param activity the activity
     */
    public static void hideKeyPad(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);

            if(activity.getCurrentFocus()!=null)
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hide soft keyboard.
     *
     * @param activity the activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Send email.
     *
     * @param context the context
     * @param To      the to
     */
    public static void SendEmail(Activity context, String To) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,To);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Gets time stamp.
     *
     * @return the time stamp
     */
    public static String getTimeStamp() {

        long timestamp = (System.currentTimeMillis() / 1000L);
        String tsTemp = "" + timestamp;

        return "" + tsTemp;

    }

    /**
     * Is valid email boolean.
     *
     * @param target the target
     * @return the boolean
     */

    /**
     * Save preferences string.
     *
     * @param context the context
     * @param key     the key
     * @param value   the value
     */
    public static void savePreferencesString(Context context, String key, String value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static void savePreferencesBoolean(Context context, String key, boolean value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }
    /**
     * Gets preferences.
     *
     * @param context the context
     * @param key     the key
     * @return the preferences
     */
    public static String getPreferences(Context context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");

    }

    /**
     * Remove preferences.
     *
     * @param context the context
     * @param key     the key
     */
    public static void removePreferences(Activity context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);

    }

    /**
     * Gets preferences boolean.
     *
     * @param context the context
     * @param key     the key
     * @return the preferences boolean
     */
    public static boolean getPreferencesBoolean(Activity context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);

    }

    /**
     * Gets preferences string.
     *
     * @param context the context
     * @param key     the key
     * @return the preferences string
     */
    public static String getPreferencesString(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }

    /**
     * Gets date.
     *
     * @param context             the context
     * @param timestamp_in_string the timestamp in string
     * @return the date
     */
    public static String getDate(Context context, String timestamp_in_string) {
        long dv = (Long.valueOf(timestamp_in_string))*1000;// its need to be in milisecond
        Date df = new Date(dv);
        String vv = new SimpleDateFormat("MMM dd/yyyy,hh:mma").format(df);
        return vv;
    }

    /**
     * Get time string.
     *
     * @param timestamp_in_string the timestamp in string
     * @return the string
     */
    public static String getTime(String timestamp_in_string){
        long dv = Long.valueOf(timestamp_in_string)*1000;// its need to be in milisecond
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(dv);
        String date = DateFormat.format("hh:mm:ss", cal).toString();
        return date;

    }

    /**
     * Is date today boolean.
     *
     * @param milliSeconds the milli seconds
     * @return the boolean
     */
    public static boolean isDateToday(long milliSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        Date getDate = calendar.getTime();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startDate = calendar.getTime();

        return getDate.compareTo(startDate) > 0;

    }

    /**
     * Show alert title.
     *
     * @param title   the title
     * @param message the message
     * @param context the context
     */
    public static void showAlertTitle(String title, String message,
                                      final Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setCancelable(false).setTitle(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        try {
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Gets hours from millis.
     *
     * @param milliseconds the milliseconds
     * @return the hours from millis
     */
    public static String getHoursFromMillis(long milliseconds) {
        return "" + (int) ((milliseconds / (1000 * 60 * 60)) % 24);
    }

    /**
     * Gets bit map from image u rl.
     *
     * @param imagepath the imagepath
     * @param activity  the activity
     * @return the bit map from image u rl
     */
    public static Bitmap getBitMapFromImageURl(String imagepath, Activity activity) {

        Bitmap bitmapFromMapActivity = null;
        Bitmap bitmapImage = null;
        try {

            File file = new File(imagepath);
            // We need to recyle unused bitmaps
            if (bitmapImage != null) {
                bitmapImage.recycle();
            }
            bitmapImage = reduceImageSize(file, activity);
            int exifOrientation = 0;
            try {
                ExifInterface exif = new ExifInterface(imagepath);
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int rotate = 0;

            switch (exifOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
            }

            if (rotate != 0) {
                int w = bitmapImage.getWidth();
                int h = bitmapImage.getHeight();

                // Setting pre rotate
                Matrix mtx = new Matrix();
                mtx.preRotate(rotate);

                // Rotating Bitmap & convert to ARGB_8888, required by
                // tess

                Bitmap myBitmap = Bitmap.createBitmap(bitmapImage, 0, 0, w, h,
                        mtx, false);
                bitmapFromMapActivity = myBitmap;
            } else {
                int SCALED_PHOTO_WIDTH = 150;
                int SCALED_PHOTO_HIGHT = 200;
                Bitmap myBitmap = Bitmap.createScaledBitmap(bitmapImage,
                        SCALED_PHOTO_WIDTH, SCALED_PHOTO_HIGHT, true);
                bitmapFromMapActivity = myBitmap;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bitmapFromMapActivity;

    }

    /**
     * Reduce image size bitmap.
     *
     * @param f       the f
     * @param context the context
     * @return the bitmap
     */
    public static Bitmap reduceImageSize(File f, Context context) {

        Bitmap m = null;
        try {

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            final int REQUIRED_SIZE = 150;

            int width_tmp = o.outWidth, height_tmp = o.outHeight;

            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            o2.inPreferredConfig = Bitmap.Config.ARGB_8888;
            m = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            // Toast.makeText(context,
            // "Image File not found in your phone. Please select another image.",
            // Toast.LENGTH_LONG).show();
        } catch (Exception e) {

        }
        return m;
    }

    /**
     * Print key hash string.
     *
     * @param context the context
     * @return the string
     */
    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }


    /**
     * Dialog select date.
     *
     * @param context  the context
     * @param textView the text view
     */
    public static void dialogSelectDate(final Context context, final TextView textView) {
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        Calendar userAge = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                        Calendar minAdultAge = new GregorianCalendar();
                        minAdultAge.add(Calendar.YEAR, 0);
                        if (minAdultAge.after(userAge)) {
                            textView.setText(dayOfMonth + "-"+ (monthOfYear + 1) + "-" + year);
                        }else {
                          //  showAlertOk("Please select a valid date of birth.",context);
                        }
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
        dpd.setCancelable(false);
    }


    /**
     * Toast.
     *
     * @param context the context
     * @param string  the string
     */
    public static void Toast(Context context, String string) {

        Toast.makeText(context,"Work in progress", Toast.LENGTH_SHORT).show();

    }


    /**
     * Show toast.
     *
     * @param context the context
     * @param message the message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * Dp 2 px float.
     *
     * @param resources the resources
     * @param dp        the dp
     * @return the float
     */
/*It is used for dynamic progress bar*/
    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    /**
     * Sp 2 px float.
     *
     * @param resources the resources
     * @param sp        the sp
     * @return the float
     */
    public static float sp2px(Resources resources, float sp){
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }


    /**
     * Gets screen width resolution.
     *
     * @param context the context
     * @return the screen width resolution
     */
    public static int getScreenWidthResolution(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        System.out.println(size.x);
        return size.x;

    }

    /**
     * Gets screen height resolution.
     *
     * @param context the context
     * @return the screen height resolution
     */
    public static String getScreenHeightResolution(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        return  height + "";
    }





    /**
     * Gets month.
     *
     * @param timeStamp the time stamp
     * @return the month
     */
    public static int getMonth(long timeStamp)
    {

        java.text.DateFormat formatter1 = new SimpleDateFormat("MM", Locale.ENGLISH);
        Log.e("##@@Month", formatter1.format(new java.sql.Date((Long.valueOf(timeStamp) * 1000))));

        return Integer.parseInt(formatter1.format(new java.sql.Date((Long.valueOf(timeStamp) * 1000))));

    }

    /**
     * Gets year.
     *
     * @param timeStamp the time stamp
     * @return the year
     */
    public static int getYear(long timeStamp)
    {

        java.text.DateFormat formatter1 = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        Log.e("##@@", formatter1.format(new java.sql.Date((Long.valueOf(timeStamp) * 1000))));

        return Integer.parseInt(formatter1.format(new java.sql.Date((Long.valueOf(timeStamp) * 1000))));

    }

    /**
     * Gets day.
     *
     * @param timeStamp the time stamp
     * @return the day
     */
    public static int getDay(long timeStamp)
    {


        java.text.DateFormat formatter1 = new SimpleDateFormat("dd", Locale.ENGLISH);
        Log.e("##@@Day", formatter1.format(new java.sql.Date((Long.valueOf(timeStamp) * 1000))));

        return Integer.parseInt(formatter1.format(new java.sql.Date((Long.valueOf(timeStamp) * 1000))));

    }

    /**
     * Check permission storage boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static  boolean checkPermissionStorage(Activity context){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result  == PackageManager.PERMISSION_GRANTED){

            if(result1 == PackageManager.PERMISSION_GRANTED){
                return true;
            }else {
                return false;
            }

        } else {
            return false;

        }
    }


    /**
     * Check permission camera boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean checkPermissionCamera(Activity context){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        if (result  == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {
            return false;

        }
    }

    /**
     * Check permission user contact boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean checkPermissionUserContact(Activity context){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
        if (result  == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {
            return false;

        }
    }

    /**
     * Check permission send sms boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean checkPermissionSendSms(Activity context){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS);
        if (result  == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {
            return false;

        }
    }

    /**
     * Check permission call phone boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean checkPermissionCallPhone(Activity context){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        if (result  == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {
            return false;

        }
    }

    /**
     * Check pass word and confirm password boolean.
     *
     * @param password        the password
     * @param confirmPassword the confirm password
     * @return the boolean
     */
    public static boolean checkPassWordAndConfirmPassword(String password, String confirmPassword)
    {
        boolean pstatus = false;
        if (confirmPassword != null && password != null)
        {
            if (password.equals(confirmPassword))
            {
                pstatus = true;
            }
        }
        return pstatus;
    }



    /**
     * Is leap year boolean.
     *
     * @param year the year
     * @return the boolean
     */
    public static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(cal.DAY_OF_YEAR) > 365;
    }

    /**
     * Load json from asset string.
     *
     * @param context  the context
     * @param fileName the file name
     * @return the string
     */
    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }








    public static float getFloatValue(String s){
        float value = 0;
        try{
            value = Float.parseFloat(setValidText(s, "0"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

    public static String setValidText(String value, String defaultValue){
        return TextUtils.isEmpty(value)?defaultValue:value;
    }

    public static String changeDateTimeFormat(String date, String oldFormat, String newFormat) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(oldFormat, Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(newFormat, Locale.getDefault());
        Date newDate;
        String str;
        try {
            newDate = inputFormat.parse(date);
            str = outputFormat.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Log.i("TimeStampFormatter", "_____oldFormat_____" + oldFormat);
        Log.i("TimeStampFormatter", "_____newFormat_____" + newFormat);
        Log.i("TimeStampFormatter", "_____date_____" + date);
      //  Log.i("TimeStampFormatter", "_____changeDateFormat_____" + str);

        return "";
    }


    public static String getSomeDeviceId(Context context) {
        String deviceId = "";

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            try {
                deviceId = telephonyManager.getDeviceId();
            } catch(Exception e) {

            }
        }

        if (deviceId == null || deviceId.equals("")) {
            // find some different id
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

            if (deviceId == null || deviceId.equals("")) {
                WifiManager m_wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (m_wm != null) {
                    deviceId = m_wm.getConnectionInfo().getMacAddress();
                }
            }
        }
        return deviceId;
    }


    /*check the Email id pattern*/

    public static boolean isValidEmail(CharSequence target) {
        return target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
