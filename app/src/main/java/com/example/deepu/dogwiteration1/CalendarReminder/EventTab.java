package com.example.deepu.dogwiteration1.CalendarReminder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deepu.dogwiteration1.R;

/**
 * Created by Marx on 16/09/2017.
 */

public class EventTab extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_tab,container,false);
        return v;
    }
}
