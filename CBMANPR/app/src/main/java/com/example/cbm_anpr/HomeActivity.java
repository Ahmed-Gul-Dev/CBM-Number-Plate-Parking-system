package com.example.cbm_anpr;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class HomeActivity extends AppCompatActivity
{
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    Intent j = new Intent(HomeActivity.this, Register.class);
//                    startActivity(j);
                    return true;
                case R.id.navigation_dashboard:
                    Intent k = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(k);
                    return true;
            }
            return false;

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void reg(View v) {
        Intent i = new Intent(HomeActivity.this, Register.class);
        startActivity(i);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(1);
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());

    }



}
