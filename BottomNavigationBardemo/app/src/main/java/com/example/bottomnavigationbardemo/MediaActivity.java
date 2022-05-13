package com.example.bottomnavigationbardemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MediaActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);


        bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.media);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(MediaActivity.this,MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.media:
                        return true;
                    case R.id.favorite:
                        startActivity(new Intent(MediaActivity.this,FavoriteActivity.class));
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
        Log.d("TAG", "onRestart: ");
        bottomNavigationView.setSelectedItemId(R.id.media);
        overridePendingTransition(0,0);
    }
}