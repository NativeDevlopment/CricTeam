package com.cricteam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cricteam.R;
import com.cricteam.TeamDetailsActivity;
import com.cricteam.netwokmodel.SearchTeam;
import com.cricteam.utils.TextDrawable;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;

import java.util.List;

/**
 * Created by Amar on 8/15/2017.
 */

public class TeamCircleAdapter extends RecyclerView.Adapter<TeamCircleAdapter.MyViewHolder> {
    private static final String ADMOB_APP_ID = "ca-app-pub-6188430950445846~6237245453";

    private final Context mContext;
    private final int NORMAL_VIEW=0;
    private final int ADD_VIEW=1;
    public TeamCircleAdapter(Context context){
        this.mContext=context;
        MobileAds.initialize(mContext, ADMOB_APP_ID);
    }
    List<SearchTeam> searcheTeams;
    public TeamCircleAdapter(Context mContext, List<SearchTeam> searchTeams) {
        this.mContext=mContext;
        this.searcheTeams=searchTeams;
    }

    @Override
    public TeamCircleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_circle,parent,false);

        switch (viewType)
        {
            case NORMAL_VIEW:
        return new MyViewHolder(rowView);
            case ADD_VIEW :
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_view,parent,false);
        }
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final TeamCircleAdapter.MyViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case NORMAL_VIEW:
                holder.tvTeamName.setText(searcheTeams.get(position).getTeamName());
                holder.tvTeamLocation.setText(searcheTeams.get(position).getTeamAddress());
              //  holder.tv_time.setText(searcheTeams.get(position).getu); change it later
                holder.tvTeamDistance.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(mContext, R.drawable.ic_place_black_24dp),null,null,null);
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
        }


holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mContext.startActivity(new Intent(mContext, TeamDetailsActivity.class));
    }
});

    }

    @Override
    public int getItemViewType(int position) {
        if(position%4==3){
            return ADD_VIEW;

        }else {
            return NORMAL_VIEW;
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private  NativeExpressAdView adView;
        private  TextView tv_time;
        private  CardView cardView;
        private final TextView tvTeamDistance;
        private final ImageView ivTeamLogo;
        private final TextView tvTeamLocation;
        private final TextView tvTeamName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTeamDistance= (TextView)itemView.findViewById(R.id.tvTeamDistance);
            tvTeamName= (TextView)itemView.findViewById(R.id.tvTeamName);
            tvTeamLocation= (TextView)itemView.findViewById(R.id.tvTeamLocation);
            ivTeamLogo= (ImageView)itemView.findViewById(R.id.ivTeamLogo);
            cardView= (CardView)itemView.findViewById(R.id.cardView);
            adView= (NativeExpressAdView)itemView.findViewById(R.id.adView);
            tv_time= (TextView)itemView.findViewById(R.id.tv_time);
        }
    }
}
