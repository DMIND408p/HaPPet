package com.example.deepu.dogwiteration1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by qingyunhe on 31/08/2017.
 */

public class SplashScreen extends Activity {
    private static int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.luanching_page);
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // run() method will be executed when 3 seconds have passed
                //Time to start MainActivity
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

}
