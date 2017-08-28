package com.cricteam;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cricteam.imagepicker.ImagePicker;
import com.cricteam.model.Player;
import com.cricteam.utils.AppConstants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import static com.cricteam.R.id.btSubmit;
import static com.cricteam.R.id.ivUserPics;

public class UpdatePlayerActivity extends AppCompatActivity {
    AdView adView;
    private ImagePicker ivTeamLogo;
    private Button buttonUpdate;
    private TextView tvLabel;
    private TextInputEditText etPlayerName;
    private TextInputEditText etPlayerMobileNo;
    private TextInputLayout tlPlayerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_player);
        String ADMOB_APP_ID = "ca-app-pub-6188430950445846~6237245453";
        MobileAds.initialize(this, ADMOB_APP_ID);
        adView=(AdView)findViewById(R.id.adView);
        ivTeamLogo=(ImagePicker)findViewById(R.id.ivTeamLogo);
        buttonUpdate=(Button)findViewById(R.id.buttonUpdate);
        etPlayerMobileNo=(TextInputEditText)findViewById(R.id.etPlayerMobileNo);
        etPlayerName=(TextInputEditText)findViewById(R.id.etPlayerName);
        tlPlayerName=(TextInputLayout)findViewById(R.id.tlPlayerName);
        tvLabel=(TextView)findViewById(R.id.tvLabel);
        ivTeamLogo.setMainactivity(this);
        AdRequest adRequest = new AdRequest.Builder()

                .build();
        adView.loadAd(adRequest);
        if(getIntent()!=null){
        if(getIntent().getStringExtra(AppConstants.UPDATE_PLAYER).equalsIgnoreCase(AppConstants.ISEDIT)){
            Player player=getIntent().getParcelableExtra(AppConstants.ADD_PLAYER);
            ivTeamLogo.setVisibility(View.GONE);
            buttonUpdate.setText("EDIT PLAYER");
            tvLabel.setText( "EDIT"+" "+ player.payerType);
            etPlayerMobileNo.setText(player.mobileNo);
            etPlayerName.setText(player.name);
        }else {
            Player player=getIntent().getParcelableExtra(AppConstants.ADD_PLAYER);
            ivTeamLogo.setVisibility(View.GONE);
            tvLabel.setText( "UPDATE"+" "+ player.payerType);
            etPlayerMobileNo.setText(player.mobileNo);
            etPlayerName.setText(player.name);
            ivTeamLogo.setVisibility(View.VISIBLE);
            buttonUpdate.setText("UPDATE PLAYER");
        }}else{
            ivTeamLogo.setVisibility(View.VISIBLE);
            buttonUpdate.setText("UPDATE PLAYER");
        }
    buttonUpdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case ImagePicker.REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    ivTeamLogo.setImage(data);
                }
                break;
            case ImagePicker.REQUEST_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    ivTeamLogo.setImage(data);
                }
                break;
        }
    }
}
