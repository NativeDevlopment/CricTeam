package com.cricteam.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cricteam.fragment.CreateTeamFragment;
import com.cricteam.fragment.FindTeamFragment;
import com.cricteam.fragment.PlayerPagerFragment;

/**
 * Created by Amar on 8/16/2017.
 */

public class PlayerPagerAdapter extends FragmentStatePagerAdapter {
    public PlayerPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PlayerPagerFragment.newInstance("","");
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
