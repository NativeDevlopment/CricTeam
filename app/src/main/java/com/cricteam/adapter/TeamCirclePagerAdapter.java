package com.cricteam.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricteam.fragment.AddTeamPlayerPagerFragment;
import com.cricteam.fragment.NotificationFragment;
import com.cricteam.fragment.TeamCirclePagerFragment;
import com.cricteam.fragment.TeamCircleRequestPagerFragment;

/**
 * Created by Amar on 8/16/2017.
 */

public class TeamCirclePagerAdapter extends FragmentStatePagerAdapter {
    public TeamCirclePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
        case 0:
            return TeamCircleRequestPagerFragment.newInstance("","");

        case 1:
            return TeamCirclePagerFragment.newInstance("","");


    }
        return NotificationFragment.newInstance("","");
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "TEAM REQUESTS";
            case 1:
                return "TEAM CIRCLE";

        }
        return null;
    }
}
