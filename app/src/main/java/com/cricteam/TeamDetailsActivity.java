package com.cricteam;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cricteam.adapter.PlayerAdapter;
import com.cricteam.fragment.CreateTeamFragment;
import com.cricteam.listner.OnFragmentInteractionListener;
import com.cricteam.model.Player;
import com.cricteam.utils.ItemDecorationAlbumColumns;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class TeamDetailsActivity extends AppCompatActivity implements OnFragmentInteractionListener,View.OnClickListener
{

    private RecyclerView rvPlayerList;
    private TextView tvTeamMore;
    private boolean isRated;
    private AdView adView;
    private Toolbar toolbar;
    private TextView titleHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);
        setHeader();
        rvPlayerList=(RecyclerView)findViewById(R.id.rvPlayerList);
        tvTeamMore=(TextView)findViewById(R.id.tvTeamMore);
        tvTeamMore.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPlayerList.setLayoutManager(linearLayoutManager);
        List<Player> playerList= new ArrayList<>();
        for (int i = 0; i <11 ; i++) {
            Player player= new Player();
            player.name="amar1";
            player.payerType="BAT";

            playerList.add(player);


        }
        rvPlayerList.setAdapter(new PlayerAdapter(this,playerList));
        String ADMOB_APP_ID = "ca-app-pub-6188430950445846~6237245453";
        MobileAds.initialize(this, ADMOB_APP_ID);
        adView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()

                .build();
        adView.loadAd(adRequest);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tvTeamMore:
                bottomDialog();
                break;

        }
    }

    private void bottomDialog() {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);

        View sheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_team_details, null);

        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }

    @Override
    public void onBackPressed() {
        if(isRated){
            super.onBackPressed();

        }else {
            ratingDialogBox(this);
            isRated=true;
        }

    }

    public  void ratingDialogBox(Context mContext) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final Dialog mDialog = new Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);

        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.75f;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow();
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().setAttributes(lp);
        final View dialogLayout = inflater.inflate(R.layout.dialog_custom_rating, null);
        Button btLatter=dialogLayout.findViewById(R.id.btLatter);
        Button btSubmit=dialogLayout.findViewById(R.id.btSubmit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                isRated=true;

            }
        });
        btLatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                isRated=true;
            }
        });
        mDialog.setContentView(dialogLayout);

        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    private void setHeader() {
        toolbar=(Toolbar)  findViewById(R.id.toolbar);
        titleHeader=(TextView)  findViewById(R.id.titleHeader);
        titleHeader.setText("Team Details");
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

    }
}
