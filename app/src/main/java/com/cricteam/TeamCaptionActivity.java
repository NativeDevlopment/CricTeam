package com.cricteam;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cricteam.adapter.PlayerCreateTeamAdapter;
import com.cricteam.listner.OnCaptionCreateListener;
import com.cricteam.model.Player;
import com.cricteam.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class TeamCaptionActivity extends AppCompatActivity implements OnCaptionCreateListener {

    private Button buttonTeamCreate;
    private Toolbar toolbar;
    private TextView titleHeader;
    boolean isEnable;
    OnCaptionCreateListener onCaptionCreateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_caption);

        ArrayList<Player>  playerArrayList= getIntent().getParcelableArrayListExtra(AppConstants.PLAYER_LIST);
        List<Player> playerList=    playerArrayList;

        onCaptionCreateListener=this;
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.RecylerListView);
        buttonTeamCreate= (Button) findViewById(R.id.buttonTeamCreate);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PlayerCreateTeamAdapter(this,playerList,onCaptionCreateListener));
        buttonTeamCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEnable) {
                    if (getIntent().getStringExtra(AppConstants.FROM_ACCOUNT_SCREEN).equalsIgnoreCase(AppConstants.FROM_ACCOUNT_SCREEN)) {
                        Intent intent = getIntent();
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else {
                        Intent intent = new Intent(TeamCaptionActivity.this, DashBordActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(new Intent(TeamCaptionActivity.this, DashBordActivity.class));
                        finishAffinity();
                    }
                }else {
                    Snackbar.make(buttonTeamCreate,"Please select Caption and Vice caption",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        setHeader();
    }
    private void setHeader() {
        toolbar=(Toolbar)  findViewById(R.id.toolbar);
        titleHeader=(TextView)  findViewById(R.id.titleHeader);
        titleHeader.setText("Add Caption and Vice Caption");
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

    }

    @Override
    public void onClickResponse(Object oject) {
        isEnable=(boolean)oject;
        if(isEnable){
            buttonTeamCreate.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
        }else {
            buttonTeamCreate.setBackgroundColor(ContextCompat.getColor(this,R.color.grey));
        }

    }
}
