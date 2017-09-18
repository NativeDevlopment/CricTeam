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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cricteam.utils.AppConstants;
import com.cricteam.utils.CommonUtils;
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

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A login screen that offers login via email/password.
 */
public class OtpVerifyActivity extends AppCompatActivity  implements OnClickListener{


    private static final int REQUEST_READ_SMS = 100;
    private View fbVerifyOtp;
    private Toolbar toolbar;
    private TextView titleHeader;
    private TextView tvMobileNO;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_verify_otp);
        fbVerifyOtp = findViewById(R.id.fbVerifyOtp);
        tvMobileNO = (TextView) findViewById(R.id.tvMobileNO);
        tvResendCode = (TextView) findViewById(R.id.tvResendCode);
        setenableResendButton(false);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        etOtpNo = (EditText) findViewById(R.id.etOtpNo);
        tvMobileNO.setOnClickListener(this);
        fbVerifyOtp.setOnClickListener(this);
        tvResendCode.setOnClickListener(this);
        otpCountDownTimer = new OtpCountDownTimer(30000, 1000);
        otpCountDownTimer.start();
        setHeader();
        if (getIntent().getStringExtra(AppConstants.MOBILE_NO) != null) {
            tvMobileNO.setText(getIntent().getStringExtra(AppConstants.MOBILE_NO));
           mobileNo=getIntent().getStringExtra(AppConstants.MOBILE_NO).replace("-","");
        }

         mAuth= FirebaseAuth.getInstance();
        mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
               // signInWithPhoneAuthCredential(phoneAuthCredential);
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);
                Log.d(TAG, "onCodeSent:" + verificationId);
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = forceResendingToken;


            }
        };


        ;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobileNo,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
        Log.d(TAG, "provider id phone Auth :" + PhoneAuthProvider.PROVIDER_ID);
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
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            user.getUid();
                            startActivity(new Intent(OtpVerifyActivity.this,CreateTeamActivity.class));
                            finishAffinity();

                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
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
                break;
            case R.id.fbVerifyOtp:
                if(!etOtpNo.getText().toString().equalsIgnoreCase("")&&etOtpNo.getText().toString().length()==6){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, etOtpNo.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                    startActivity(new Intent(OtpVerifyActivity.this,CreateTeamActivity.class));
                    finishAffinity();
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

