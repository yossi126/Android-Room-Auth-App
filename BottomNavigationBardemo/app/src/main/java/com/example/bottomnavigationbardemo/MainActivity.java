package com.example.bottomnavigationbardemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;
    Button goToTest;
    Button goToTest2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_bar);
        toolbar = findViewById(R.id.topAppBar);
        goToTest = findViewById(R.id.goToTestBtn);
        goToTest2 = findViewById(R.id.goToTestBtn2);

        goToTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,test.class));
            }
        });
        goToTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ArrayAdapterTest2.class));
            }
        });

        //setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dellAll:
                        Toast.makeText(MainActivity.this,"delete all",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.logout_topbar:
                        Toast.makeText(MainActivity.this,"logout",Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.media:
                        startActivity(new Intent(MainActivity.this,MediaActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.favorite:
                        startActivity(new Intent(MainActivity.this,FavoriteActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return true;
            }
        });
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        bottomNavigationView.setSelectedItemId(R.id.home);
        overridePendingTransition(0,0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPauseHome", "onPause: ");
    }
}