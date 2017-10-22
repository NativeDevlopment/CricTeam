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
import com.cricteam.fragment.AddTeamPlayerFragment;
import com.cricteam.listner.OnApiResponse;
import com.cricteam.listner.OnCaptionCreateListener;
import com.cricteam.model.Player;
import com.cricteam.netwokmodel.APIExecutor;
import com.cricteam.netwokmodel.AddPlayerRequest;
import com.cricteam.netwokmodel.NetWorkApiCall;
import com.cricteam.netwokmodel.PlayerDetails;
import com.cricteam.netwokmodel.PlayerTypes;
import com.cricteam.netwokmodel.Response;
import com.cricteam.netwokmodel.TeamDetails;
import com.cricteam.netwokmodel.TeamRequest;
import com.cricteam.utils.AppConstants;
import com.cricteam.utils.CommonUtils;
import com.cricteam.utils.DelayedProgressDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TeamCaptionActivity extends AppCompatActivity implements OnCaptionCreateListener {

    private Button buttonTeamCreate;
    private Toolbar toolbar;
    private TextView titleHeader;
    boolean isEnable;
    OnCaptionCreateListener onCaptionCreateListener;
    private DelayedProgressDialog progressDialog;
    private PlayerCreateTeamAdapter playerCreateTeamAdapter;

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
        playerCreateTeamAdapter =  new PlayerCreateTeamAdapter(this,playerList,onCaptionCreateListener);
        recyclerView.setAdapter(playerCreateTeamAdapter);
        buttonTeamCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEnable) {
                    AddPlayerRequest addPlayerRequest= new AddPlayerRequest();
                    List<PlayerDetails> playerList= new ArrayList<PlayerDetails>();
                    addPlayerRequest.setTeamId(Integer.parseInt(CommonUtils.getPreferences(TeamCaptionActivity.this, AppConstants.TEAM_ID)));
                   addPlayerRequest.setUserId(Integer.parseInt(CommonUtils.getPreferences(TeamCaptionActivity.this, AppConstants.USER_ID)));
                    List<Player> updatePlayerList=playerCreateTeamAdapter.getplayerList();
                    for (int i = 0; i <updatePlayerList.size() ; i++) {
                        PlayerDetails playerDetails= new PlayerDetails();
                        playerDetails.setPlayerName(updatePlayerList.get(i).name);
                        playerDetails.setPlayerMobile(updatePlayerList.get(i).mobileNo);
                        if(updatePlayerList.get(i).isCaptaion){
                            playerDetails.setLeaderShip("C");
                        }else if(updatePlayerList.get(i).isVoiceCaption){
                            playerDetails.setLeaderShip("VC");
                        }
                        PlayerTypes playerTypes= new PlayerTypes();
                        if(updatePlayerList.get(i).payerType.equalsIgnoreCase(AppConstants.WK)){
                            playerTypes.setPlayerTypeId(1);
                            playerTypes.setPlayerTypeNm(AppConstants.WK);
                            playerDetails.setPlayerTypes(playerTypes);
                        }else if(updatePlayerList.get(i).payerType.equalsIgnoreCase(AppConstants.BAT)){
                            playerTypes.setPlayerTypeId(2);
                            playerTypes.setPlayerTypeNm(AppConstants.BAT);
                            playerDetails.setPlayerTypes(playerTypes);
                        }else if(updatePlayerList.get(i).payerType.equalsIgnoreCase(AppConstants.BALL)){
                            playerTypes.setPlayerTypeId(3);
                            playerTypes.setPlayerTypeNm(AppConstants.BALL);
                            playerDetails.setPlayerTypes(playerTypes);
                        }
                        TeamDetails teamDetails= new TeamDetails();
                        teamDetails.setTeamId(Integer.parseInt(CommonUtils.getPreferences(TeamCaptionActivity.this, AppConstants.TEAM_ID)));
                        playerDetails.setTeamDetails(teamDetails);
                        playerDetails.setPlayerName(updatePlayerList.get(i).name);
                        playerList.add(playerDetails);
                    }
                    addPlayerRequest.setPlayerList(playerList);

                    addPlayers( addPlayerRequest);
                }else {
                    Snackbar.make(buttonTeamCreate,"Please select Caption and Vice caption",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        setHeader();
    }
    void   addPlayers(AddPlayerRequest request){
        progressDialog = new DelayedProgressDialog();
        progressDialog.show(getSupportFragmentManager(), "tag");

        NetWorkApiCall.getInstance().getApiResponse(this, APIExecutor.getApiService().addPlayers(request), new OnApiResponse() {
            @Override
            public void onResponse(Response response) {

                progressDialog.cancel();
                if(response!=null){
                    //final TeamDetails userDetails= new Gson().fromJson(new Gson().toJsonTree(response.data).toString(),TeamDetails.class);
                    String teamDetails= new Gson().toJsonTree(response.data).toString();
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
                   /* realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            // Add a person

                            realm.copyToRealmOrUpdate(userDetails);


                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.llContainer, AddTeamPlayerFragment.newInstance(mParam1, "")).addToBackStack(null).commit();

                        }
                    });*/

                }
            }
        });
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
