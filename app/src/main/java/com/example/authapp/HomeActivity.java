package com.example.authapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.authapp.databinding.ActivityHomeBinding;
import com.example.authapp.util.DataViewModel;
import com.google.android.material.navigation.NavigationBarView;


public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private DataViewModel dataViewModel;
    private int userId;
    private static String userName;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataViewModel = new ViewModelProvider(HomeActivity.this).get(DataViewModel.class);
        dataViewModel.resetMedia();

        sharedPreferences = getApplicationContext().getSharedPreferences("UserPref",MODE_PRIVATE);

        userId = sharedPreferences.getInt("UserId",0);
        userName = sharedPreferences.getString("UserName","");
        StringBuilder sb2 = new StringBuilder();
        sb2.append(userId+" ");
        sb2.append(userName);
        binding.sp.setText(sb2);


        ActionMenuItemView deleteAllItem= findViewById(R.id.deleteAll);
        deleteAllItem.setVisibility(View.GONE);
        binding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.deleteAll:
                        dataViewModel.deleteAllFavoritesByUser();
                        break;
                    case R.id.exitApp:
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });


        binding.bottomNavBar.setSelectedItemId(R.id.home);
        binding.bottomNavBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.media:
                        Intent intentM = new Intent(HomeActivity.this,MediaActivity.class);
                        startActivity(new Intent(intentM));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.favorite:
                        Intent intent = new Intent(HomeActivity.this,FavoriteActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return true;
            }
        });

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        binding.bottomNavBar.setSelectedItemId(R.id.home);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d("TAG", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d("TAG", "onStop: ");
    }
}