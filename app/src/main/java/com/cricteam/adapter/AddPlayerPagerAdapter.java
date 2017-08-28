package com.cricteam.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricteam.fragment.AddTeamPlayerPagerFragment;
import com.cricteam.fragment.PlayerPagerFragment;

/**
 * Created by Amar on 8/16/2017.
 */

public class AddPlayerPagerAdapter extends FragmentStatePagerAdapter {
    public AddPlayerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        String playerType="";
        switch (position){
        case 0:
        playerType ="WK";
        break;
        case 1:
            playerType = "BAT";
            break;
        case 2:
            playerType = "BALL";
        break;}
        return AddTeamPlayerPagerFragment.newInstance(playerType,"");
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "WK";
            case 1:
                return "BAT";
            case 2:
                return "BALL";

        }
        return null;
    }
}
