package com.mc.carrent.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.mc.carrent.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    sleep(3000);

                    //Checking if the user has already logged in or not
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LoginActivity",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    String login = sharedPreferences.getString("userId",null);

                    //If not logged in go to the login activity else to home.
                    if(login == null)
                    {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                    }

                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
