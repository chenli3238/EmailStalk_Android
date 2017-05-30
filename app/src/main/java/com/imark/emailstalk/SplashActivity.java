package com.imark.emailstalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.imark.emailstalk.Infrastructure.AppCommon;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    Boolean login = AppCommon.getInstance(SplashActivity.this).isUserLogIn();
                    if (login) {
                        if(SplashActivity.this.getIntent().getExtras().getString("message-id")!=null){
                            Intent intent = new Intent(SplashActivity.this, EmailDetailActivity.class);
                            intent.putExtra("MessageId", SplashActivity.this.getIntent().getExtras().getString("message-id"));
                            intent.putExtra("Type", "Notification");
                            startActivity(intent);
                            finish();
                        }else {
                            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                            finish();
                        }
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
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
