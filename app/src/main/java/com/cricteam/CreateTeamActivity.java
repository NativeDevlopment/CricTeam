package com.cricteam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cricteam.fragment.UserAccountFragment;
import com.cricteam.listner.OnFragmentInteractionListener;

import com.cricteam.fragment.CreateTeamFragment;
import com.cricteam.model.ResultCancel;
import com.cricteam.utils.AppConstants;
import com.cricteam.utils.CommonUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import org.greenrobot.eventbus.EventBus;

public class CreateTeamActivity extends AppCompatActivity implements OnFragmentInteractionListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private static final int PERMISSION_LOCATION_REQUEST_CODE = 100;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    PendingResult<LocationSettingsResult> result;
    final static int REQUEST_LOCATION = 199;
    private Toolbar toolbar;
    private TextView titleHeader;
    private String TAG = CreateTeamActivity.class.getSimpleName();
   public Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
       // getAddressAndEnableLocation();

fragment=UserAccountFragment.newInstance("", "");
        if (getIntent() != null) {
            if (getIntent().getStringExtra(AppConstants.FROM_ACCOUNT_SCREEN) != null) {
                fragment=CreateTeamFragment.newInstance(AppConstants.FROM_ACCOUNT_SCREEN, "");
                getSupportFragmentManager().beginTransaction().add(R.id.llContainer,fragment , "CreateTeamFragment").commit();

            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.llContainer,fragment , "UserAccountFragment").commit();

            }
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.llContainer, fragment, "UserAccountFragment").commit();

        }
        //setHeader();
    }

    public void getAddressAndEnableLocation() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();

        if(CommonUtils.getGpsEnabled(this)){
        getPlaceLocation();
    }
}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult()", Integer.toString(resultCode));
        for (int i = 0; i <getSupportFragmentManager().getFragments().size() ; i++) {
            Log.e("fragment list", getSupportFragmentManager().getFragments().get(i).getClass().getName());
        }
        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_OK: {
                        if(CommonUtils.isOnline(CreateTeamActivity.this)){
                            if( fragment instanceof CreateTeamFragment){
                                ((CreateTeamFragment)fragment).textLabel.setVisibility(View.VISIBLE);
                            }
                            if( fragment instanceof UserAccountFragment){
                                ((UserAccountFragment)fragment).textLabel.setVisibility(View.VISIBLE);
                            }
                        getPlaceLocation();}else {
                            CommonUtils.showToast(CreateTeamActivity.this,"Please Check Network Connection");
                        }

                        // All required changes were successfully made
                        //   Toast.makeText(MainActivity.this, "Location enabled by user!", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case Activity.RESULT_CANCELED: {
                        if( fragment instanceof CreateTeamFragment){
                            ((CreateTeamFragment)fragment).textLabel.setVisibility(View.INVISIBLE);
                            ShowMessage(CreateTeamActivity.this,"Please Click On Location Icon For Add Team Address");

                        }
                        if( fragment instanceof UserAccountFragment){
                            ((UserAccountFragment)fragment).textLabel.setVisibility(View.INVISIBLE);
                            ShowMessage(CreateTeamActivity.this,"Please Click On Location Icon For Add User Address");

                        }                        // The user was asked to change settings, but chose not to
                        // Toast.makeText(MainActivity.this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
        }
        if(fragment!=null)
           fragment.onActivityResult(requestCode,resultCode,data);

    }

    private void setHeader() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleHeader = (TextView) findViewById(R.id.titleHeader);
        titleHeader.setText("USER PROFILE");
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

    }
    void ShowMessage (Context context ,String message){

        AlertDialog.Builder builder= new AlertDialog.Builder(context).setMessage(message).
                setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }

    @Override
    public void onFragmentInteractions(Fragment sfragment) {
        fragment=sfragment;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                //final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        getPlaceLocation();

                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        //...

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    CreateTeamActivity.this,
                                    REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        //...
                        break;
                }
            }
        });

    }

    public void getPlaceLocation() {
        if (ActivityCompat.checkSelfPermission(CreateTeamActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(
                    CreateTeamActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_LOCATION_REQUEST_CODE);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        PendingResult<PlaceLikelihoodBuffer> placeResult = Places.PlaceDetectionApi
                .getCurrentPlace(mGoogleApiClient, null);
        placeResult.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                if(likelyPlaces.getCount()>0){
                    Log.e("location address",""+likelyPlaces.get(0).getPlace().getAddress());
                    EventBus.getDefault().post(likelyPlaces.get(0).getPlace());
                    likelyPlaces.release();}
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getPlaceLocation();
                } else {

                    // permissions list of don't granted permission
                }


                return;
            }
        }
    }
}
