package com.cricteam;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cricteam.adapter.PlayerAdapter;
import com.cricteam.fragment.CreateTeamFragment;
import com.cricteam.listner.OnApiResponse;
import com.cricteam.listner.OnFragmentInteractionListener;
import com.cricteam.model.Player;
import com.cricteam.netwokmodel.APIExecutor;
import com.cricteam.netwokmodel.FullTeamDetails;
import com.cricteam.netwokmodel.NetWorkApiCall;
import com.cricteam.netwokmodel.Response;
import com.cricteam.netwokmodel.TeamCircleRequest;
import com.cricteam.netwokmodel.TeamDetails;
import com.cricteam.utils.AppConstants;
import com.cricteam.utils.CommonUtils;
import com.cricteam.utils.ItemDecorationAlbumColumns;
import com.cricteam.utils.TextDrawable;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamDetailsActivity extends AppCompatActivity implements OnFragmentInteractionListener,View.OnClickListener
{

    private RecyclerView rvPlayerList;
    private TextView tvTeamMore;
    private boolean isRated;
    private AdView adView;
    private Toolbar toolbar;
    private TextView titleHeader;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvTeamName;
    private TextView tvTeamLocation;
    private ImageView ivTeamLogo;
    private RatingBar ratingBar;
     FullTeamDetails fullTeamDetails;
    private TextView playerListLabel;
    private Button tv_send_action;
    private FloatingActionButton fbCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);
        setHeader();
        rvPlayerList=(RecyclerView)findViewById(R.id.rvPlayerList);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setNestedScrollingEnabled(false);
        swipeRefreshLayout.setRefreshing(true);
        tvTeamMore=(TextView)findViewById(R.id.tvTeamMore);
        fbCall=(FloatingActionButton)findViewById(R.id.fbCall);
        tvTeamName=(TextView)findViewById(R.id.tvTeamName);
        tv_send_action=(Button)findViewById(R.id.tv_send_action);
        tvTeamLocation=(TextView)findViewById(R.id.tvTeamLocation);
        playerListLabel=(TextView)findViewById(R.id.playerListLabel);
        ivTeamLogo=(ImageView)findViewById(R.id.ivTeamLogo);
        ratingBar=(RatingBar)findViewById(R.id.ratingbar);
        tvTeamMore.setOnClickListener(this);

        String ADMOB_APP_ID = "ca-app-pub-6188430950445846~6237245453";
        MobileAds.initialize(this, ADMOB_APP_ID);
        adView=(AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()

                .build();
        adView.loadAd(adRequest);
        if(getIntent()!=null){
            getTeamDetails(getIntent().getStringExtra(AppConstants.TEAM_ID),getIntent().getStringExtra(AppConstants.USER_ID),CommonUtils.getPreferences(this,AppConstants.TEAM_ID));

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteractions(Fragment fragment) {

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
        TextView tvTeamOwenerName=(TextView) sheetView.findViewById(R.id.tvTeamOwenerName);
        TextView tvOwenerLocation=(TextView) sheetView.findViewById(R.id.tvOwenerLocation);
        ImageView ivTeamOwener=(ImageView) sheetView.findViewById(R.id.ivTeamOwener);
        if(fullTeamDetails!=null){
            tvTeamOwenerName.setText(fullTeamDetails.getTeamdetails().getUserDetails().getName());
            tvOwenerLocation.setText(fullTeamDetails.getTeamdetails().getUserDetails().getUserAddress());

            if(fullTeamDetails.getTeamdetails().getUserDetails().getUserImageUrl()!=null&&!fullTeamDetails.getTeamdetails().getUserDetails().getUserImageUrl().equalsIgnoreCase("")){
            Glide.with(TeamDetailsActivity.this).load(fullTeamDetails.getTeamdetails().getUserDetails().getUserImageUrl()).into(ivTeamOwener);

            }else{
                if(fullTeamDetails.getTeamdetails().getUserDetails().getName()!=null&&!fullTeamDetails.getTeamdetails().getUserDetails().getName().equalsIgnoreCase("")) {
                    TextDrawable drawable = TextDrawable.builder()
                            .beginConfig()
                            .withBorder(4) /* thickness in px */
                            .endConfig()
                            .buildRoundRect("" + fullTeamDetails.getTeamdetails().getUserDetails().getName().charAt(0), ContextCompat.getColor(TeamDetailsActivity.this, R.color.colorPrimary), 10);
                    ivTeamLogo.setImageDrawable(drawable);
                }
            }
        }
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


    private void getTeamDetails(String teamId ,String userId,String ownTeamId) {


        Map<String,Integer> map= new HashMap<>();
        try {
            map.put("teamId", Integer.valueOf(teamId));
            map.put("userId", Integer.valueOf(userId));
            map.put("ownTeamId", Integer.valueOf(ownTeamId));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        NetWorkApiCall.getInstance().getApiResponse(this, APIExecutor.getApiService().getTeamDetails(map), new OnApiResponse() {
            @Override
            public void onResponse(Response response) {
                swipeRefreshLayout.setRefreshing(false);

                if(response!=null){
                    fullTeamDetails= new Gson().fromJson(new Gson().toJsonTree(response.data).toString(),FullTeamDetails.class);

                  if(fullTeamDetails!=null){
                      tvTeamName.setText(fullTeamDetails.getTeamdetails().getTeamName());
                      tvTeamLocation.setText(fullTeamDetails.getTeamdetails().getTeamAddress());

                      if(fullTeamDetails.getTeamdetails().getTeamLogoUrl()!=null&&!fullTeamDetails.getTeamdetails().getTeamLogoUrl().equalsIgnoreCase("")){
                          Glide.with(TeamDetailsActivity.this).load(fullTeamDetails.getTeamdetails().getTeamLogoUrl()).into(ivTeamLogo);

                      }else{
                          if(fullTeamDetails.getTeamdetails().getTeamName()!=null&&!fullTeamDetails.getTeamdetails().getTeamName().equalsIgnoreCase("")) {
                              TextDrawable drawable = TextDrawable.builder()
                                      .beginConfig()
                                      .withBorder(4) /* thickness in px */
                                      .endConfig()
                                      .buildRoundRect("" + fullTeamDetails.getTeamdetails().getTeamName().charAt(0), ContextCompat.getColor(TeamDetailsActivity.this, R.color.colorPrimary), 10);
                              ivTeamLogo.setImageDrawable(drawable);
                          }
                      }
                      ratingBar.setRating((float) fullTeamDetails.getTeamRating());
                      if(fullTeamDetails.getTeamPlayerList().size()>0){
                          playerListLabel.setVisibility(View.GONE);
                          rvPlayerList.setVisibility(View.VISIBLE);
                          LinearLayoutManager linearLayoutManager= new LinearLayoutManager(TeamDetailsActivity.this);
                          linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                          rvPlayerList.setLayoutManager(linearLayoutManager);
                          rvPlayerList.setAdapter(new PlayerAdapter(TeamDetailsActivity.this,fullTeamDetails.getTeamPlayerList()));
                      }else {
                          playerListLabel.setVisibility(View.VISIBLE);
                          rvPlayerList.setVisibility(View.GONE);
                      }
                      if(fullTeamDetails.getTeamCircleStatus()!=null){
                          tv_send_action.setText("WANT PLAY");
                      }else{
                          if (fullTeamDetails.getTeamCircleStatus().getActionStatus().equalsIgnoreCase("SEND")){
                              if(fullTeamDetails.getTeamCircleStatus().getReceiverId().equalsIgnoreCase(CommonUtils.getPreferences(TeamDetailsActivity.this,AppConstants.TEAM_ID)))
                              tv_send_action.setText("INVITED");
                              else
                                  tv_send_action.setText("ACCEPT");
                          }else if(fullTeamDetails.getTeamCircleStatus().getActionStatus().equalsIgnoreCase("ACCEPT")){
                              tv_send_action.setVisibility(View.GONE);
                              fbCall.setVisibility(View.VISIBLE);
                          }
                      }
                  }


                }
            }
        });
    }
}
