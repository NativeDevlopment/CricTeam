package com.cricteam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cricteam.R;
import com.cricteam.UpdatePlayerActivity;
import com.cricteam.model.Player;
import com.cricteam.utils.AppConstants;

import java.util.Collections;
import java.util.List;

/**
 * Created by Amar on 8/15/2017.
 */

public class PlayerPreviewAdapter extends RecyclerView.Adapter<PlayerPreviewAdapter.MyViewHolder> {
    private final Context mContext;
    private  List<Player> playerList= Collections.emptyList();

    public PlayerPreviewAdapter(Context context){
        this.mContext=context;
    }

    public PlayerPreviewAdapter(Context mContext, List<Player> playerList) {
        this.mContext=mContext;
        this.playerList=playerList;
    }

    @Override
    public PlayerPreviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_preview,parent,false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(PlayerPreviewAdapter.MyViewHolder holder, final int position) {
        holder.tvPlayerMobileNo.setText(playerList.get(position).mobileNo);
        holder.tvPlayerName.setText(playerList.get(position).name);
        holder.tvPlayerType.setText(playerList.get(position).payerType);
        holder.ivEditPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent=new Intent(mContext,UpdatePlayerActivity.class);
                intent.putExtra(AppConstants.UPDATE_PLAYER,AppConstants.ISEDIT);
                intent.putExtra(AppConstants.ADD_PLAYER,playerList.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private final TextView tvPlayerName;
        private final TextView tvPlayerMobileNo;
        private final TextView tvPlayerType;
        private final ImageView ivEditPlayer;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvPlayerName=(TextView)   itemView.findViewById(R.id.tvPlayerName);
            tvPlayerMobileNo=(TextView)   itemView.findViewById(R.id.tvPlayerMobileNo);
            tvPlayerType=(TextView)   itemView.findViewById(R.id.tvPlayerType);
            ivEditPlayer=(ImageView)   itemView.findViewById(R.id.ivEditPlayer);
        }
    }
}
