

package com.example.deepu.dogwiteration1.Home;


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
public class home_blank extends Fragment {
    View vw;


    public home_blank() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vw =  inflater.inflate(R.layout.fragment_home_blank, container, false);
        setHasOptionsMenu(true);
        getFragmentManager().beginTransaction().add(R.id.blank_hsscreen,new HomeScreen(),"Home Page").commit();
        getActivity().setTitle("Home Page");

        return vw;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
        }else {
            getActivity().setTitle("Home Page");
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
        getActivity().setTitle("Home Page");

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

}
