package com.example.deepu.dogwiteration1.BreedActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.deepu.dogwiteration1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BreedBlak extends Fragment {

    View vv;

    public BreedBlak() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vv =  inflater.inflate(R.layout.fragment_breed_blak, container, false);
        setHasOptionsMenu(true);
        getFragmentManager().beginTransaction().add(R.id.hh_screen,new breed_details(),"main Page").commit();
        getActivity().setTitle("Breed Details");
        return  vv;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
        }else {
            getActivity().setTitle("Breed Details");
            getActivity().invalidateOptionsMenu();

        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_plant_fragment, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
        getActivity().setTitle("Breed Details");

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

}
