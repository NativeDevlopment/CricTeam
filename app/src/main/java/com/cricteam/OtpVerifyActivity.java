package com.cricteam;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cricteam.listner.OnApiResponse;
import com.cricteam.netwokmodel.APIExecutor;
import com.cricteam.netwokmodel.NetWorkApiCall;
import com.cricteam.netwokmodel.Response;
import com.cricteam.netwokmodel.UserCompleteDetails;
import com.cricteam.netwokmodel.UserDetails;
import com.cricteam.netwokmodel.VerifyOtp;
import com.cricteam.utils.AppConstants;
import com.cricteam.utils.CommonUtils;
import com.cricteam.utils.DelayedProgressDialog;
import com.cricteam.utils.SearchableListDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A login screen that offers login via email/password.
 */
public class OtpVerifyActivity extends AppCompatActivity  implements OnClickListener{


    private static final int REQUEST_READ_SMS = 100;
    private View fbVerifyOtp;
    private Toolbar toolbar;
    private TextView titleHeader;
    private TextView tvMobileNO,tvLabel;
    private EditText etOtpNo;
    private TextView tvResendCode;
    private TextView tvTimer;
    OtpCountDownTimer otpCountDownTimer;
    private String TAG;
    private FirebaseAuth mAuth;
    private com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mobileNo;
    private String OTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_verify_otp);
        fbVerifyOtp = findViewById(R.id.fbVerifyOtp);
        tvMobileNO = (TextView) findViewById(R.id.tvMobileNO);
        tvLabel = (TextView) findViewById(R.id.tvLabel);
        tvMobileNO.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(this, R.drawable.ic_edit_black_24dp), null);
        tvResendCode = (TextView) findViewById(R.id.tvResendCode);
        setenableResendButton(false);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        etOtpNo = (EditText) findViewById(R.id.etOtpNo);
        tvMobileNO.setOnClickListener(this);
        fbVerifyOtp.setOnClickListener(this);
        tvResendCode.setOnClickListener(this);
        otpCountDownTimer = new OtpCountDownTimer(30000, 1000);
        otpCountDownTimer.start();
        // setHeader();
        OTP = getIntent().getStringExtra(AppConstants.OTP);
        //setHeader();
        if (getIntent().getStringExtra(AppConstants.MOBILE_NO) != null) {
            tvMobileNO.setText(getIntent().getStringExtra(AppConstants.MOBILE_NO));
            etOtpNo.setText(""+OTP);
            mobileNo = getIntent().getStringExtra(AppConstants.MOBILE_NO).replace("-", "");
        }

    }
    private void setenableResendButton(boolean b) {
        tvResendCode.setEnabled(b);
        if(!b)
        tvResendCode.setTextColor(ContextCompat.getColor(this,R.color.grey));
        else
        tvResendCode.setTextColor(ContextCompat.getColor(this,R.color.red));
    }

    private void setHeader() {
        toolbar=(Toolbar)  findViewById(R.id.toolbar);
        titleHeader=(TextView)  findViewById(R.id.titleHeader);
        titleHeader.setText("OTP Verify");
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

    }
    @Override
    protected void onPause() {
        super.onPause();
        otpCountDownTimer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        otpCountDownTimer.onFinish();
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_SMS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvMobileNO:
                finish();
                break;
            case R.id.tvResendCode:
                otpCountDownTimer.start();
                setenableResendButton(false);
                  NetWorkApiCall.getInstance().getApiResponse(this, APIExecutor.getApiService().sendOtp(mobileNo.trim()), new OnApiResponse() {
                        @Override
                        public void onResponse(Response response) {
                            if(response!=null){

                          }
                        }
                    });
                break;
            case R.id.fbVerifyOtp:
                if(!etOtpNo.getText().toString().equalsIgnoreCase("")&&etOtpNo.getText().toString().length()==6){
                    final DelayedProgressDialog progressDialog = new DelayedProgressDialog();
                    progressDialog.show(getSupportFragmentManager(), "tag");
                    VerifyOtp verifyOtp= new VerifyOtp();
                    verifyOtp.setMobileNo(mobileNo.substring(1));
                    verifyOtp.setDeviceId(AppConstants.PSEUDO_CODE_DEVICE_ID);
                    verifyOtp.setDeviceType("Android");
                    verifyOtp.setOtp(etOtpNo.getText().toString());
                    verifyOtp.setDeviceToken(CommonUtils.getPreferences(OtpVerifyActivity.this,AppConstants.DEVICE_TOKEN));
                    Log.e("Request",new Gson().toJsonTree(verifyOtp).toString());
                    NetWorkApiCall.getInstance().getApiResponse(this, APIExecutor.getApiService().verifyOtp(verifyOtp), new OnApiResponse() {
                        @Override
                        public void onResponse(Response response) {
                            progressDialog.cancel();

                            if(response!=null) {

                                final UserCompleteDetails userCompleteDetails = new Gson().fromJson(new Gson().toJsonTree(response.data).toString(), UserCompleteDetails.class);
                                if (userCompleteDetails != null) {
                                    final UserDetails userDetails = userCompleteDetails.getUserDetails();
                                    CommonUtils.savePreferencesString(OtpVerifyActivity.this, AppConstants.USER_ID, "" + userDetails.getUserId());
                                    CricTeamApplication cricTeamApplication = (CricTeamApplication) getApplication();
                                    Realm realm = Realm.getInstance(cricTeamApplication.config);
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            // Add a person
                                            UserDetails userDetails1 = realm.createObject(UserDetails.class, userDetails.getUserId());
                                            userDetails1.setUserAddress(userDetails.getUserAddress());
                                            userDetails1.setUserEmail(userDetails.getUserEmail());
                                            userDetails1.setUserLat(userDetails.getUserLat());
                                            userDetails1.setUserLong(userDetails.getUserLong());
                                            userDetails1.setDeviceType(userDetails.getDeviceType());
                                            userDetails1.setDeviceId(userDetails.getDeviceId());
                                            userDetails1.setDeviceToken(userDetails.getDeviceToken());
                                            userDetails1.setUserImageUrl(userDetails.getUserImageUrl());
                                            userDetails1.setName(userDetails.getName());
                                            userDetails1.setMobileNo(userDetails.getMobileNo());

                                        }
                                    });

                                }
                                if(userCompleteDetails.getUserTeamList().size()>0){
                                    CommonUtils.savePreferencesString(OtpVerifyActivity.this,AppConstants.TEAM_ID, String.valueOf(userCompleteDetails.getUserTeamList().get(0).getTeamId()));
                                    CommonUtils.savePreferencesString(OtpVerifyActivity.this,AppConstants.CUURENT_LAT,String.valueOf(userCompleteDetails.getUserTeamList().get(0).getTeamLat()));
                                    CommonUtils.savePreferencesString(OtpVerifyActivity.this,AppConstants.CUURENT_LANG,String.valueOf(userCompleteDetails.getUserTeamList().get(0).getTeamLong()));

                                    startActivity(new Intent(OtpVerifyActivity.this, DashBordActivity.class));
                                    finishAffinity();
                                }else {
                                    startActivity(new Intent(OtpVerifyActivity.this, CreateTeamActivity.class));
                                    finishAffinity();
                                }

                            }
                        }
                    });

                }
                else {
                    Snackbar.make(etOtpNo,"Please enter Otp which you received.",Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }
    class OtpCountDownTimer extends CountDownTimer{

        public OtpCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            tvTimer.setText(""+(l/1000));
        }

        @Override
        public void onFinish() {
            tvTimer.setText("");
            setenableResendButton(true);
        }
    }

}

