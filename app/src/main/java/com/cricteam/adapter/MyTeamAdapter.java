package com.cricteam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
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
import com.cricteam.TeamUpdateActivity;
import com.cricteam.model.MyTeam;
import com.cricteam.netwokmodel.TeamDetails;
import com.cricteam.utils.TextDrawable;

import java.util.List;

/**
 * Created by Amar on 8/15/2017.
 */

public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.MyViewHolder> {
    private final Context mContext;
    private List<TeamDetails> searcheTeams;

    public MyTeamAdapter(Context context, List<TeamDetails> teamDetailsList){
        this.mContext=context;
        this.searcheTeams= teamDetailsList;
    }
    @Override
    public MyTeamAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team,parent,false);
        return new MyViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(MyTeamAdapter.MyViewHolder holder, int position) {
        holder.tvTeamDistance.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(mContext, R.drawable.ic_place_black_24dp),null,null,null);
        holder.tvTeamName.setText(searcheTeams.get(position).getTeamName());
        holder.tvTeamLocation.setText(searcheTeams.get(position).getTeamAddress());

               /* if(searcheTeams.get(position).getTeamCircleStatus().equalsIgnoreCase("Want Play")){
                    holder.tv_send_action.setText(searcheTeams.get(position).getTeamCircleStatus());
                }else if(searcheTeams.get(position).getTeamCircleStatus().equalsIgnoreCase("RECEIVED")){
                    holder.tv_send_action.setText(searcheTeams.get(position).getTeamCircleStatus());

                }else if(searcheTeams.get(position).getTeamCircleStatus().equalsIgnoreCase("REJECT")){
                    holder.tv_send_action.setText(searcheTeams.get(position).getTeamCircleStatus());

                }else if(searcheTeams.get(position).getTeamCircleStatus().equalsIgnoreCase("ACCEPT")){
                    holder.tv_send_action.setText(searcheTeams.get(position).getTeamCircleStatus());

                }*/

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, TeamUpdateActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return searcheTeams.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTeamDistance;
        private final TextView tv_send_action;
        private final TextView tvTeamName;
        private final TextView tvTeamLocation;
        private final ImageView ivTeamLogo;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_send_action= (TextView)itemView.findViewById(R.id.tv_send_action);
            tvTeamDistance= (TextView)itemView.findViewById(R.id.tvTeamDistance);
            tvTeamDistance.setVisibility(View.GONE);
            tv_send_action.setVisibility(View.GONE);
            tvTeamName= (TextView)itemView.findViewById(R.id.tvTeamName);
            tvTeamLocation= (TextView)itemView.findViewById(R.id.tvTeamLocation);
            ivTeamLogo= (ImageView)itemView.findViewById(R.id.ivTeamLogo);
        }
    }
}
