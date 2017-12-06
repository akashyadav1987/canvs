package com.canvas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.canvas.common.GlobalReferences;

/**
 * Created by admin on 5/30/2017.
 */
public class SplashActivity extends BaseActivity  {
    private boolean RESPONSE_RECEIVED = false;
    public static Handler myHandler = new Handler();
    private static int TIME_TO_WAIT = 3000;
    private Runnable myRunnable;
    //int countTries =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);
        GlobalReferences.getInstance().baseActivity = this;

        myRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity();

            }
        };
        start();

    }
    public void start() {
        myHandler.postDelayed(myRunnable, TIME_TO_WAIT);
    }

    public void stop() {
        myHandler.removeCallbacks(myRunnable);
    }

    public void restart() {
        myHandler.removeCallbacks(myRunnable);
        myHandler.postDelayed(myRunnable, TIME_TO_WAIT);
    }

    public void startActivity() {
        Intent intent = null;
        intent = new Intent(SplashActivity.this, MotherActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
