package com.example.deepu.dogwiteration1.PlantRelevant;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.deepu.dogwiteration1.PlantRelevant.PlantFragment.HouseToxicTabFragment;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantFragment.NonToxicPlantTabFragment;
import com.example.deepu.dogwiteration1.PlantRelevant.PlantFragment.WildToxicPlantTabFragment;
import com.example.deepu.dogwiteration1.R;

/**
 * Created by qingyunhe on 27/08/2017.
 * This is the main page of Plant, user can search in the toolbar. filtered result will show in the list of different tab.
 */

public class PlantFirstPage extends Fragment {

    View vPlant;
    TabLayout tabLayout;
    SearchView searchView;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_plant_fragment, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //search function
            @Override
            public boolean onQueryTextSubmit(String query) {
                switch (tabLayout.getSelectedTabPosition()) {
                    case 0:
                        //get houseToxicTabFragment by tag: viewPager ID and index of it
                        HouseToxicTabFragment houseToxicTabFragment = (HouseToxicTabFragment) getFragmentManager().
                                findFragmentByTag(getFragmentTag(R.id.pager, 0));
                        houseToxicTabFragment.filterList(query);
                        break;
                    case 1:
                        WildToxicPlantTabFragment wildToxicPlantTabFragment = (WildToxicPlantTabFragment) getFragmentManager().
                                findFragmentByTag(getFragmentTag(R.id.pager, 1));
                        wildToxicPlantTabFragment.filterList(query);
                        break;
                    case 2:
                        NonToxicPlantTabFragment nonToxicPlantTabFragment = (NonToxicPlantTabFragment) getFragmentManager().
                                findFragmentByTag(getFragmentTag(R.id.pager, 2));
                        nonToxicPlantTabFragment.filterList(query);
                        break;
                }
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() <= 0) {
                    //Search Text has changed, apply filtering function in different fragment.
                    switch (tabLayout.getSelectedTabPosition()) {
                        case 0:
                            //get houseToxicTabFragment by tag: viewPager ID and index of it
                            HouseToxicTabFragment houseToxicTabFragment = (HouseToxicTabFragment) getFragmentManager().
                                    findFragmentByTag(getFragmentTag(R.id.pager, 0));
                            houseToxicTabFragment.filterList(newText);
                            break;
                        case 1:
                            WildToxicPlantTabFragment wildToxicPlantTabFragment = (WildToxicPlantTabFragment) getFragmentManager().
                                    findFragmentByTag(getFragmentTag(R.id.pager, 1));
                            wildToxicPlantTabFragment.filterList(newText);
                            break;
                        case 2:
                            NonToxicPlantTabFragment nonToxicPlantTabFragment = (NonToxicPlantTabFragment) getFragmentManager().
                                    findFragmentByTag(getFragmentTag(R.id.pager, 2));
                            nonToxicPlantTabFragment.filterList(newText);
                            break;
                    }
                }
                return false;
            }
        });
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) this.getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint("Search name, clinical signs: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        vPlant = inflater.inflate(R.layout.plant_first_page, container, false);

        tabLayout = (TabLayout) vPlant.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("House Poisonous Plant"));
        tabLayout.addTab(tabLayout.newTab().setText("Wild Poisonous Plant"));
        tabLayout.addTab(tabLayout.newTab().setText("Dog Friendly Plant"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) vPlant.findViewById(R.id.pager);
        final PlantPagerAdaptor adapter = new PlantPagerAdaptor
                (getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getActivity().setTitle("Toxic and non-toxic plant");
        return vPlant;
    }

    private String getFragmentTag(int viewPagerId, int fragmentPosition) {
        return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
        }else {
            getActivity().setTitle("Toxic and non-toxic plant");
            getActivity().invalidateOptionsMenu();

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
        getActivity().setTitle("Toxic and non-toxic plant");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        MenuItem item = menu.findItem(R.id.action_search);
//        item.setVisible(true);
//    }
}
