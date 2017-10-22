package com.cricteam.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cricteam.R;
import com.cricteam.TeamDetailsActivity;
import com.cricteam.fragment.CreateTeamFragment;
import com.cricteam.listner.OnApiResponse;
import com.cricteam.netwokmodel.APIExecutor;
import com.cricteam.netwokmodel.NetWorkApiCall;
import com.cricteam.netwokmodel.Response;
import com.cricteam.netwokmodel.SearchTeam;
import com.cricteam.netwokmodel.TeamCircle;
import com.cricteam.netwokmodel.TeamCircleRequest;
import com.cricteam.netwokmodel.UserDetails;
import com.cricteam.utils.AppConstants;
import com.cricteam.utils.CommonUtils;
import com.cricteam.utils.TextDrawable;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.google.gson.Gson;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Amar on 8/15/2017.
 */

public class FindTeamAdapter extends RecyclerView.Adapter<FindTeamAdapter.MyViewHolder> {
    private static final String ADMOB_APP_ID = "ca-app-pub-6188430950445846~6237245453";
    private final Context mContext;
    private final int NORMAL_VIEW=0;
    private final int ADD_VIEW=1;
    private final int PROGRESS_VIEW=2;
    private final List<SearchTeam> searcheTeams;

    public  FindTeamAdapter (Context context , List<SearchTeam> searchTeams){
        this.mContext=context;
        MobileAds.initialize(mContext, ADMOB_APP_ID);
        this.searcheTeams=searchTeams;
    }
    @Override
    public FindTeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team,parent,false);

        switch (viewType)
        {
            case NORMAL_VIEW:
        return new MyViewHolder(rowView);
            case ADD_VIEW :
                 rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_view,parent,false);
               // new MyViewHolder(rowView);
                break;
            case PROGRESS_VIEW:
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress,parent,false);
break;
        }
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final FindTeamAdapter.MyViewHolder holder, final int position) {
        switch (getItemViewType(position)){
            case NORMAL_VIEW:
                holder.tvTeamDistance.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(mContext, R.drawable.ic_place_black_24dp),null,null,null);
                holder.tvTeamName.setText(searcheTeams.get(position).getTeamName());
                holder.tvTeamLocation.setText(searcheTeams.get(position).getTeamAddress());
                holder.tv_send_action.setText(searcheTeams.get(position).getTeamCircleStatus());
                if(searcheTeams.get(position).getTeamCircleStatus().equalsIgnoreCase("SEND")){
                    holder.tv_send_action.setText("INVITED");
                }else{
                    holder.tv_send_action.setText("WANT PLAY");
                }
               /* if(searcheTeams.get(position).getTeamCircleStatus().equalsIgnoreCase("Want Play")){
                    holder.tv_send_action.setText(searcheTeams.get(position).getTeamCircleStatus());
                }else if(searcheTeams.get(position).getTeamCircleStatus().equalsIgnoreCase("RECEIVED")){
                    holder.tv_send_action.setText(searcheTeams.get(position).getTeamCircleStatus());

                }else if(searcheTeams.get(position).getTeamCircleStatus().equalsIgnoreCase("REJECT")){
                    holder.tv_send_action.setText(searcheTeams.get(position).getTeamCircleStatus());

                }else if(searcheTeams.get(position).getTeamCircleStatus().equalsIgnoreCase("ACCEPT")){
                    holder.tv_send_action.setText(searcheTeams.get(position).getTeamCircleStatus());

                }*/
                try {
                    holder.tvTeamDistance.setText(String.format("%.2f",Float.valueOf(searcheTeams.get(position).getDistance()))+" Km");
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if(searcheTeams.get(position).getTeamLogoUrl()!=null&&!searcheTeams.get(position).getTeamLogoUrl().equalsIgnoreCase("")){
                    Glide.with(mContext).load(searcheTeams.get(position).getTeamLogoUrl()).into(holder.ivTeamLogo);

                }else{
                    if(searcheTeams.get(position).getTeamName()!=null&&!searcheTeams.get(position).getTeamName().equalsIgnoreCase("")) {
                        TextDrawable drawable = TextDrawable.builder()
                                .beginConfig()
                                .withBorder(4) /* thickness in px */
                                .endConfig()
                                .buildRoundRect("" + searcheTeams.get(position).getTeamName().charAt(0), ContextCompat.getColor(mContext, R.color.colorPrimary), 10);
                        holder.ivTeamLogo.setImageDrawable(drawable);
                    }
                }
                holder.tv_send_action.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(searcheTeams.get(position).getTeamCircleStatus().equalsIgnoreCase("SEND")){
                            ShowMessage(mContext,"Do you want to cancel Team Invitation",position);
                        }else{
                            sendInvitation(mContext,position);
                        }
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=   new Intent(mContext, TeamDetailsActivity.class);
                        intent.putExtra(AppConstants.TEAM_ID,searcheTeams.get(position).getTeamId());
                        intent.putExtra(AppConstants.USER_ID,searcheTeams.get(position).getUserId());
                        mContext.startActivity(intent);
                    }
                });
                break;
            case  ADD_VIEW:
                // Set its video options.

             holder.adView.setVideoOptions(new VideoOptions.Builder()
                        .setStartMuted(true)
                        .build());

                // The VideoController can be used to get lifecycle events and info about an ad's video
                // asset. One will always be returned by getVideoController, even if the ad has no video
                // asset.
                final VideoController mVideoController = holder.adView.getVideoController();
                mVideoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                    @Override
                    public void onVideoEnd() {
                        super.onVideoEnd();
                    }
                });

                // Set an AdListener for the AdView, so the Activity can take action when an ad has finished
                // loading.
                holder.adView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        if (mVideoController.hasVideoContent()) {
                           //Log.d(LOG_TAG, "Received an ad that contains a video asset.");
                        } else {
                           // Log.d(LOG_TAG, "Received an ad that does not contain a video asset.");
                        }
                    }
                });
                holder.adView.loadAd(new AdRequest.Builder().build());
                break;
            case PROGRESS_VIEW:
                break;
        }



    }

    private void sendInvitation(final Context mContext, final int position) {

        final TeamCircleRequest teamCircleRequest= new TeamCircleRequest();
        teamCircleRequest.setSenderId(Integer.parseInt(CommonUtils.getPreferences(mContext, AppConstants.TEAM_ID)));
        teamCircleRequest.setSenderUserId(Integer.parseInt(CommonUtils.getPreferences(mContext, AppConstants.USER_ID)));
        teamCircleRequest.setReceiverId(Integer.parseInt(searcheTeams.get(position).getTeamId()));
        teamCircleRequest.setReceiverUserId(Integer.parseInt(searcheTeams.get(position).getUserId()));
        teamCircleRequest.setRequestAction("SEND");

        NetWorkApiCall.getInstance().getApiResponse(mContext, APIExecutor.getApiService().sendTeamCircleRequest(teamCircleRequest), new OnApiResponse() {
            @Override
            public void onResponse(Response response) {


                if(response!=null){
                    final TeamCircle teamCircle= new Gson().fromJson(new Gson().toJsonTree(response.data).toString(),TeamCircle.class);
                    if(teamCircle!=null){
                        searcheTeams.get(position).setTeamCircleStatus("SEND");
                       searcheTeams.get(position).setTeamRequestId(String.valueOf(teamCircle.getTeamCircleId()));
                        notifyItemChanged(position);
                    }

                }
            }
        });
    }

    private void CancelInvitation(final Context mContext, final int position) {

        final TeamCircleRequest teamCircleRequest= new TeamCircleRequest();
        teamCircleRequest.setSenderId(Integer.parseInt(CommonUtils.getPreferences(mContext, AppConstants.TEAM_ID)));
        teamCircleRequest.setSenderUserId(Integer.parseInt(CommonUtils.getPreferences(mContext, AppConstants.USER_ID)));
        teamCircleRequest.setReceiverId(Integer.parseInt(searcheTeams.get(position).getTeamId()));
        teamCircleRequest.setReceiverUserId(Integer.parseInt(searcheTeams.get(position).getUserId()));
        teamCircleRequest.setRequestId(Integer.parseInt(searcheTeams.get(position).getTeamRequestId()));
        teamCircleRequest.setRequestAction("CANCEL");
        searcheTeams.get(position).setTeamCircleStatus("Want Play");
        searcheTeams.get(position).setTeamRequestId("");
        notifyItemChanged(position);
        NetWorkApiCall.getInstance().getApiResponse(mContext, APIExecutor.getApiService().sendTeamCircleRequest(teamCircleRequest), new OnApiResponse() {
            @Override
            public void onResponse(Response response) {


                if(response!=null){
                  //  final TeamCircle teamCircle= new Gson().fromJson(new Gson().toJsonTree(response.data).toString(),TeamCircle.class);

                        searcheTeams.get(position).setTeamCircleStatus("Want Play");
                        searcheTeams.get(position).setTeamRequestId("");
                        notifyItemChanged(position);


                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if(searcheTeams.get(position)!=null){
        if(searcheTeams.get(position).isAddView()){
            return ADD_VIEW;

        } else {
            return NORMAL_VIEW;
        }}else {

                return PROGRESS_VIEW;


        }

    }

    @Override
    public int getItemCount() {
        return searcheTeams.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_send_action;
        private final TextView tvTeamDistance;
        private final ImageView ivTeamLogo;
        private final TextView tvTeamLocation;
        private final TextView tvTeamName;
        private  NativeExpressAdView adView;
        private  CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_send_action= (TextView)itemView.findViewById(R.id.tv_send_action);
            tvTeamDistance= (TextView)itemView.findViewById(R.id.tvTeamDistance);
            tvTeamName= (TextView)itemView.findViewById(R.id.tvTeamName);
            tvTeamLocation= (TextView)itemView.findViewById(R.id.tvTeamLocation);
            ivTeamLogo= (ImageView)itemView.findViewById(R.id.ivTeamLogo);
            cardView= (CardView)itemView.findViewById(R.id.cardView);
            adView= (NativeExpressAdView)itemView.findViewById(R.id.adView);
        }
    }
    void ShowMessage (Context context , String message, final int position){

        AlertDialog.Builder builder= new AlertDialog.Builder(context).setMessage(message).
                setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CancelInvitation(mContext,position);
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
