package com.cricteam.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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

import com.cricteam.R;
import com.cricteam.TeamDetailsActivity;
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

import java.util.List;

/**
 * Created by Amar on 8/15/2017.
 */

public class FindTeamAdapter extends RecyclerView.Adapter<FindTeamAdapter.MyViewHolder> {
    private static final String ADMOB_APP_ID = "ca-app-pub-6188430950445846~6237245453";

    private final Context mContext;
    private final int NORMAL_VIEW=0;
    private final int ADD_VIEW=1;
    public  FindTeamAdapter (Context context){
        this.mContext=context;
        MobileAds.initialize(mContext, ADMOB_APP_ID);
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
        }
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final FindTeamAdapter.MyViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case NORMAL_VIEW:

                if(position==2){
                    holder.tv_send_action.setText("Team Invited");
                }
                if(position==5){
                    holder.tv_send_action.setText("Call Team");
                }
                TextDrawable drawable = TextDrawable.builder()
                        .beginConfig()
                        .withBorder(4) /* thickness in px */
                        .endConfig()
                        .buildRoundRect("A", ContextCompat.getColor(mContext,R.color.colorPrimary), 10);
                holder.ivTeamLogo.setImageDrawable(drawable);
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

    private void populateContentAdView(NativeContentAd nativeContentAd,
                                       NativeContentAdView adView) {


        adView.setHeadlineView(adView.findViewById(R.id.contentad_headline));
        adView.setImageView(adView.findViewById(R.id.contentad_image));
        adView.setBodyView(adView.findViewById(R.id.contentad_body));
        adView.setCallToActionView(adView.findViewById(R.id.contentad_call_to_action));
        adView.setLogoView(adView.findViewById(R.id.contentad_logo));
        adView.setAdvertiserView(adView.findViewById(R.id.contentad_advertiser));

        // Some assets are guaranteed to be in every NativeContentAd.
        ((TextView) adView.getHeadlineView()).setText(nativeContentAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeContentAd.getBody());
        ((TextView) adView.getCallToActionView()).setText(nativeContentAd.getCallToAction());
        ((TextView) adView.getAdvertiserView()).setText(nativeContentAd.getAdvertiser());

        List<NativeAd.Image> images = nativeContentAd.getImages();

        if (images.size() > 0) {
            ((ImageView) adView.getImageView()).setImageDrawable(images.get(0).getDrawable());
        }

        // Some aren't guaranteed, however, and should be checked.
        NativeAd.Image logoImage = nativeContentAd.getLogo();

        if (logoImage == null) {
            adView.getLogoView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getLogoView()).setImageDrawable(logoImage.getDrawable());
            adView.getLogoView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeContentAd);
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
        private final TextView tv_send_action;
        private final ImageView ivTeamLogo;
        private  NativeExpressAdView adView;
        private  CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_send_action= (TextView)itemView.findViewById(R.id.tv_send_action);
            ivTeamLogo= (ImageView)itemView.findViewById(R.id.ivTeamLogo);
            cardView= (CardView)itemView.findViewById(R.id.cardView);
            adView= (NativeExpressAdView)itemView.findViewById(R.id.adView);
        }
    }
}
