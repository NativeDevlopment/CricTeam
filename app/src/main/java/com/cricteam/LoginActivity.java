package com.cricteam;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cricteam.listner.OnApiResponse;
import com.cricteam.netwokmodel.APIExecutor;
import com.cricteam.netwokmodel.ApiService;
import com.cricteam.netwokmodel.NetWorkApiCall;
import com.cricteam.netwokmodel.Response;
import com.cricteam.utils.AppConstants;
import com.cricteam.utils.CommonUtils;
import com.cricteam.utils.Country;
import com.cricteam.utils.SearchableListDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements SearchableListDialog.SearchableItem, OnClickListener {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    // UI references.


    private View mLoginFormView;
    private TextView tvCountryCode;
    private FloatingActionButton fbNext;
    private SearchableListDialog _searchableListDialog;
    private Toolbar toolbar;
    private TextView titleHeader;
    private EditText etMobileNo;
    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private GoogleApiClient mGoogleApiClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private String TAG=LoginActivity.class.getCanonicalName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
      //  setHeader();
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);
        SettingLocationOn(this);
        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        fbNext = (FloatingActionButton) findViewById(R.id.fbNext);
        tvCountryCode = (TextView) findViewById(R.id.tvCountryCode);
        etMobileNo = (EditText) findViewById(R.id.etMobileNo);

        tvCountryCode.setOnClickListener(this);
        fbNext.setOnClickListener(this);

        mLoginFormView = findViewById(R.id.login_form);

        _searchableListDialog = SearchableListDialog.newInstance
                (CommonUtils.getLibraryMasterCountriesEnglish());
        _searchableListDialog.setOnSearchableItemClickListener(this);

        _searchableListDialog.setTitle("Country Code ");



        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                .getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    Log.i(TAG, String.format("Place '%s' has likelihood: %g",
                            placeLikelihood.getPlace().getName(),
                            placeLikelihood.getLikelihood()));
                }
                likelyPlaces.release();
            }
        });




    }

    private void setHeader() {
        toolbar=(Toolbar)  findViewById(R.id.toolbar);
        titleHeader=(TextView)  findViewById(R.id.titleHeader);
        titleHeader.setText("Login");
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

    }


    @Override
    public void onSearchableItemClicked(Object item, int position) {
      Country country= (Country) item;
      tvCountryCode.setText("+"+country.getPhoneCode());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tvCountryCode:
                try {
                    if(_searchableListDialog.isAdded()){
                        getSupportFragmentManager().beginTransaction().remove(_searchableListDialog);
                    }else {
                    }

                    _searchableListDialog.show(getSupportFragmentManager(), "TAG");

                }catch (Exception e){

                }
                break;
            case R.id.fbNext:
                if(!etMobileNo.getText().toString().equalsIgnoreCase("")&& etMobileNo.getText().toString().length()==10) {
                    /*NetWorkApiCall.getInstance().getApiResponse(this, APIExecutor.getApiService().sendOtp(etMobileNo.getText().toString().trim()), new OnApiResponse() {
                        @Override
                        public void onResponse(Response response) {
                            Intent intent=   new Intent(LoginActivity.this,OtpVerifyActivity.class);
                            intent.putExtra(AppConstants.MOBILE_NO,tvCountryCode.getText().toString()+"-"+etMobileNo.getText().toString());
                            startActivity(intent);
                        }
                    });*/

                    Intent intent=   new Intent(LoginActivity.this,OtpVerifyActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra(AppConstants.MOBILE_NO,tvCountryCode.getText().toString()+"-"+etMobileNo.getText().toString());
                    startActivity(intent);

                }else if (etMobileNo.getText().toString().length()<10){
                    Snackbar.make(etMobileNo,"Please enter the actual mobile number.",Snackbar.LENGTH_SHORT).show();
                } else
                    Snackbar.make(etMobileNo,"Please entered your mobile number.",Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

  void  SettingLocationOn(Context context){
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).addApi(Places.GEO_DATA_API)
              .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .build();
        mGoogleApiClient.connect();
    }
}

