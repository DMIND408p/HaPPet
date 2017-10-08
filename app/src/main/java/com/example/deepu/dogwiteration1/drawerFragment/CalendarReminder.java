package com.example.deepu.dogwiteration1.drawerFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deepu.dogwiteration1.CalendarReminder.CompactCalendarTab;
import com.example.deepu.dogwiteration1.R;

/**
 * Created by deepu on 17/08/2017.
 */

public class CalendarReminder extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calendar_reminder, container, false);
        getFragmentManager().beginTransaction().
                add(R.id.calendar_reminder_main_page, new CompactCalendarTab(), "calendarMainPage").
                commit();
        getActivity().setTitle("Dog Calendar");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){

        }else {
            getActivity().setTitle("Dog Calendar");
            getActivity().invalidateOptionsMenu();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
        getActivity().setTitle("Dog Calendar");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.blank_menu, menu);
    }




}
