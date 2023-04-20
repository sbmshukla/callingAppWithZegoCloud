package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashScreenActivity extends AppCompatActivity {
MainScreen master=new MainScreen();
MainScreen mainScreen=new MainScreen();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_actvity);




        // on below line we are calling handler to run a task
        // for specific time interval
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (master.getLoggedInUserDetails()!=null){
                    mainScreen.startService(master.getLoggedInUserDetails().getMobileNumber());
                    Intent i = new Intent(SplashScreenActivity.this, CallActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i = new Intent(SplashScreenActivity.this, MainScreen.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 2000);

    }
}