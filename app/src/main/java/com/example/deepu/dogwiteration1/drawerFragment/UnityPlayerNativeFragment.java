package com.example.deepu.dogwiteration1.drawerFragment;

import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.deepu.dogwiteration1.R;
import com.unity3d.player.UnityPlayer;

/**
 * Created by qingyunhe on 01/09/2017.
 */

public class UnityPlayerNativeFragment extends Fragment {
    private static UnityPlayer mUnityPlayer;     // don't change the name of this variable; referenced from native code
    FrameLayout fl_forUnity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUnityPlayer = new UnityPlayer((ContextWrapper) getActivity().getApplicationContext());
    }

    // Setup activity layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_unity, container, false);
        setHasOptionsMenu(true);
        //Inflate the frame layout from XML
        this.fl_forUnity = (FrameLayout) view.findViewById(R.id.fl_forUnity);

        //Add the mUnityPlayer view to the FrameLayout, and set it to fill all the area available
        this.fl_forUnity.addView(mUnityPlayer.getView(),
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        //Requesting the Focus
        mUnityPlayer.requestFocus();
        getActivity().getWindow().takeSurface(null);
        getActivity().getWindow().setFormat(PixelFormat.RGB_565);

        //The main fix of resolving BLACK SCREEN PLAYER ISSUE
        mUnityPlayer.windowFocusChanged(true);//First fix Line
        // Yes, it's "static" way and should to be more dynamic, anyway, it works well
        return view;
    }

    // Quit Unity
    @Override
    public void onDestroy() {
        mUnityPlayer.quit();
        super.onDestroy();
    }

    // Pause Unity
    @Override
    public void onPause() {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override
    public void onResume() {
        super.onResume();
        mUnityPlayer.resume();
    }

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {

        } else {
            getActivity().setTitle("Body Language");
            getActivity().invalidateOptionsMenu();
        }
    }

}