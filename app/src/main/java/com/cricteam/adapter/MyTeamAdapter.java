package com.cricteam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cricteam.R;
import com.cricteam.TeamDetailsActivity;
import com.cricteam.TeamUpdateActivity;
import com.cricteam.model.MyTeam;

import java.util.List;

/**
 * Created by Amar on 8/15/2017.
 */

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.MyViewHolder> {
    private final Context mContext;
    private List<MyTeam> myTeamList;

    public MyTeamAdapter(Context context, List<MyTeam> myTeamList){
        this.mContext=context;
        this.myTeamList= myTeamList;
    }
    @Override
    public MyTeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team,parent,false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(MyTeamAdapter.MyViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, TeamUpdateActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTeamDistance;
        private final TextView tv_send_action;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_send_action= (TextView)itemView.findViewById(R.id.tv_send_action);
            tvTeamDistance= (TextView)itemView.findViewById(R.id.tvTeamDistance);
            tvTeamDistance.setVisibility(View.GONE);
            tv_send_action.setVisibility(View.GONE);
        }
    }
}
