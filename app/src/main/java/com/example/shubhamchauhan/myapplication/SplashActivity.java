package com.example.shubhamchauhan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread t1 = new Thread(){
            public  void run(){
                try{
                    sleep(5000);
                }
                catch (InterruptedException e)
                {

                }
                finally {
                    LoginActivity.startIntent(SplashActivity.this);
                    finish();
                }
            }
        };
        t1.start();
    }
}
