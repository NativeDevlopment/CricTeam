package com.cricteam;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricteam.fragment.AccountFragment;
import com.cricteam.fragment.FindTeamFragment;
import com.cricteam.fragment.NotificationFragment;
import com.cricteam.fragment.TeamCircleFragment;
import com.cricteam.listner.OnFragmentInteractionListener;

public class DashBordActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    private TextView mTextMessage;
    private ImageView searchImage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, AccountFragment.newInstance("","")).commit();
                    searchImage.setVisibility(View.GONE);
                    tvHeader.setText("My Profile");
                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, FindTeamFragment.newInstance("","")).commit();
                    searchImage.setVisibility(View.VISIBLE);
                    tvHeader.setText("Search team  by location");
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, NotificationFragment.newInstance("","")).commit();

                    searchImage.setVisibility(View.GONE);
                    tvHeader.setText("Notification");
                    return true;
                case R.id.navigation_accepted:
                    searchImage.setVisibility(View.GONE);
                    tvHeader.setText("Team Circle");
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, TeamCircleFragment.newInstance("","")).commit();

                    return true;
            }
            return false;
        }

    };
    private TextView tvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_bord);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
         searchImage = (ImageView) findViewById(R.id.searchImage);
        tvHeader = (TextView) findViewById(R.id.tvHeader);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
