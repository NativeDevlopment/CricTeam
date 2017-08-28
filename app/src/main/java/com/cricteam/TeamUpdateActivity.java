package com.cricteam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cricteam.adapter.PlayerAdapter;
import com.cricteam.adapter.PlayerPagerAdapter;
import com.cricteam.listner.OnFragmentInteractionListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class TeamUpdateActivity extends AppCompatActivity implements OnFragmentInteractionListener,View.OnClickListener
{

    private RecyclerView rvPlayerList;
    private TextView tvTeamMore;
    private ViewPager view_pager;
    private TabLayout tabLayout;
    private CardView cardTeam;
    private Toolbar toolbar;
    private TextView titleHeader;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_update);
        view_pager=(ViewPager)findViewById(R.id.view_pager);
        cardTeam=(CardView) findViewById(R.id.cardTeam);
        tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        String ADMOB_APP_ID = "ca-app-pub-6188430950445846~6237245453";
        MobileAds.initialize(this, ADMOB_APP_ID);
        adView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()

                .build();
        adView.loadAd(adRequest);
        view_pager.setAdapter(new PlayerPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(view_pager);
        cardTeam.setOnClickListener(this);
        setHeader();
    }
    private void setHeader() {
        toolbar=(Toolbar)  findViewById(R.id.toolbar);
        titleHeader=(TextView)  findViewById(R.id.titleHeader);
        titleHeader.setText("Update Team Player");
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.cardTeam:

                break;

        }
    }


}
