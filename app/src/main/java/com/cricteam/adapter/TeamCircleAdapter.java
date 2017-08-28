package com.cricteam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cricteam.R;
import com.cricteam.TeamDetailsActivity;
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
    @Override
    public TeamCircleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team,parent,false);

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
                holder.tv_send_action.setText("Team Invited");
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
        private  TextView tv_send_action;
        private  NativeExpressAdView adView;
        private  CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_send_action= (TextView)itemView.findViewById(R.id.tv_send_action);
            cardView= (CardView)itemView.findViewById(R.id.cardView);
            adView= (NativeExpressAdView)itemView.findViewById(R.id.adView);
        }
    }
}
