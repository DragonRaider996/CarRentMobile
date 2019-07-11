package com.mc.carrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottomNavigationHome);
        frameLayout = findViewById(R.id.frameLayoutHome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragmentScreen(new HomeFragment());
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()){

            case R.id.navigation_home:
                fragment = new HomeFragment();
                loadFragmentScreen(fragment);
                return true;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                loadFragmentScreen(fragment);
                return true;
        }
        return false;
    }

    private void loadFragmentScreen(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutHome,fragment);
        transaction.commit();
    }
}
