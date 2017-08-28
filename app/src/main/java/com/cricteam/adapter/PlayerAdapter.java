package com.cricteam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.cricteam.R;
import com.cricteam.UpdatePlayerActivity;
import com.cricteam.model.Player;
import com.cricteam.utils.AppConstants;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Amar on 8/15/2017.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.MyViewHolder> {
    private final Context mContext;
    List<Player> playerList= Collections.emptyList();

    public PlayerAdapter(Context context, List<Player> playerList){
        this.mContext=context;
        this.playerList=playerList;
    }
    @Override
    public PlayerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player,parent,false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(PlayerAdapter.MyViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,UpdatePlayerActivity.class);
                intent.putExtra(AppConstants.UPDATE_PLAYER,"UPDATE");
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


        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
