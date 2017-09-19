package com.cricteam.utils;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

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
}
