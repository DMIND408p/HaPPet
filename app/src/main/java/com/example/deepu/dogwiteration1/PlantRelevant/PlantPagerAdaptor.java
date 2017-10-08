package com.example.deepu.dogwiteration1.PlantRelevant;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.deepu.dogwiteration1.PlantRelevant.PlantFragment.HouseToxicTabFragment;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantFragment.NonToxicPlantTabFragment;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantFragment.WildToxicPlantTabFragment;

/**
 * Created by qingyunhe on 20/08/2017.
 */

public class PlantPagerAdaptor extends FragmentPagerAdapter {
    int mNumOfTabs;
    HouseToxicTabFragment houseToxicTabFragment;
    WildToxicPlantTabFragment wildToxicPlantTabFragment;
    NonToxicPlantTabFragment nonToxicPlantTabFragment;

    public PlantPagerAdaptor(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                houseToxicTabFragment = new HouseToxicTabFragment();
                return houseToxicTabFragment;
            case 1:
                wildToxicPlantTabFragment = new WildToxicPlantTabFragment();
                return wildToxicPlantTabFragment;
            case 2:
                nonToxicPlantTabFragment = new NonToxicPlantTabFragment();
                return nonToxicPlantTabFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
