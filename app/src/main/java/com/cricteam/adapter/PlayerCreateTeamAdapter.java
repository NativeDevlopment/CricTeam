package com.cricteam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cricteam.R;
import com.cricteam.listner.OnCaptionCreateListener;
import com.cricteam.model.Player;

import java.util.List;

/**
 * Created by Amar on 8/15/2017.
 */

public class PlayerCreateTeamAdapter extends RecyclerView.Adapter<PlayerCreateTeamAdapter.MyViewHolder> {
    private final Context mContext;
    private final OnCaptionCreateListener onCaptionCreateListener;
    private List<Player> playerList;
    private  boolean isCaption ,isVoiceCaption;
    public PlayerCreateTeamAdapter(Context context , List<Player> playerList, OnCaptionCreateListener onCaptionCreateListener){
        this.mContext=context;
        this.playerList=playerList;
        this.onCaptionCreateListener=onCaptionCreateListener;
    }



    @Override
    public PlayerCreateTeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_make_caption,parent,false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(PlayerCreateTeamAdapter.MyViewHolder holder, final int position) {

     if(playerList.get(position).isCaptaion){
         holder.tvCaption.setBackground(ContextCompat.getDrawable(mContext,R.drawable.circle_thumb));
         holder.tvCaption.setTextColor(ContextCompat.getColor(mContext,R.color.white));
     }else {
         holder.tvCaption.setBackground(ContextCompat.getDrawable(mContext,R.drawable.circle_thumb_unselected));
         holder.tvCaption.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimaryDark));
     }
     holder.tvPlayerName.setText(playerList.get(position).name);
     holder.tvPlayerType.setText(playerList.get(position).payerType);
     if(playerList.get(position).isVoiceCaption){
         holder.tvVoiceCaption.setBackground(ContextCompat.getDrawable(mContext,R.drawable.circle_thumb));
         holder.tvVoiceCaption.setTextColor(ContextCompat.getColor(mContext,R.color.white));
     }else {
         holder.tvVoiceCaption.setBackground(ContextCompat.getDrawable(mContext,R.drawable.circle_thumb_unselected));
         holder.tvVoiceCaption.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimaryDark));
     }
        holder.tvCaption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < playerList.size(); i++) {
                    if(i==position){
                        playerList.get(i).isCaptaion=!playerList.get(i).isCaptaion;
                        isCaption=playerList.get(i).isCaptaion;
                        if( playerList.get(i).isVoiceCaption){
                        playerList.get(i).isVoiceCaption=false;
                        isVoiceCaption=false;}
                    }else {
                        playerList.get(i).isCaptaion=false;
                    }

                }
                if(isCaption && isVoiceCaption){
                    onCaptionCreateListener.onClickResponse(true);
                }else {
                    onCaptionCreateListener.onClickResponse(false);
                }
                notifyDataSetChanged();
            }
        });
        holder.tvVoiceCaption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < playerList.size(); i++) {
                    if(i==position){
                        playerList.get(i).isVoiceCaption=!playerList.get(i).isVoiceCaption;
                        if(playerList.get(i).isCaptaion){
                            playerList.get(i).isCaptaion=false;
                            isCaption=false;
                        }

                        isVoiceCaption=playerList.get(i).isVoiceCaption;

                    }else {
                        playerList.get(i).isVoiceCaption=false;
                    }

                }
                notifyDataSetChanged();
                if(isCaption && isVoiceCaption){
                    onCaptionCreateListener.onClickResponse(true);
                }else {
                    onCaptionCreateListener.onClickResponse(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private  TextView tvVoiceCaption;
        private  TextView tvPlayerName;
        private  TextView tvCaption;
        private  TextView tvPlayerType;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvVoiceCaption= (TextView)  itemView.findViewById(R.id. tvVoiceCaption);
            tvPlayerType= (TextView)  itemView.findViewById(R.id. tvPlayerType);
            tvCaption= (TextView) itemView.findViewById(R.id. tvCaption);
            tvPlayerName= (TextView) itemView.findViewById(R.id. tvPlayerName);

        }
    }
}
