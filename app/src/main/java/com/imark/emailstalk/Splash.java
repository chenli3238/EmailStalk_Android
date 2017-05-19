package com.imark.emailstalk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread t = new Thread() {
            public void run() {
                try {

                    sleep(2000);
                   // startActivity(new Intent(Splash.this,LoginActivity.class));
                    /*if(AppCommon.getInstance(Splash_Activity.this).isUserLogIn()){
                        startActivity(new Intent(Splash_Activity.this, TabActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(Splash_Activity.this,SignUpActivity.class));
                        finish();
                    }*/

                    SharedPreferences prefs = getSharedPreferences("UserLogin", MODE_PRIVATE);
                    Boolean login = prefs.getBoolean("login", false);
                    if (login) {
                        startActivity(new Intent(Splash.this, Home.class));
                        finish();
                    } else {
                        startActivity(new Intent(Splash.this, LoginActivity.class));
                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
       t.start();
    }
}
