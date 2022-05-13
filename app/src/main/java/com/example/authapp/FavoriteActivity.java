package com.example.authapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.authapp.databinding.ActivityFavoriteBinding;
import com.example.authapp.entity.Media;
import com.example.authapp.entity.relationship.UserWithVideos;
import com.example.authapp.util.DataViewModel;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;
    private DataViewModel dataViewModel;
    private ArrayAdapter arrayAdapter;
    private int userId;
    private static String userName;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_favorite);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataViewModel = new ViewModelProvider(FavoriteActivity.this).get(DataViewModel.class);
        sharedPreferences = getApplicationContext().getSharedPreferences("UserPref",MODE_PRIVATE);
        binding.bottomNavBar.setSelectedItemId(R.id.favorite);
        userId = sharedPreferences.getInt("UserId",0);
        userName = sharedPreferences.getString("UserName","");

        // top menu bar
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
                        Intent intent = new Intent(FavoriteActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        // bottom menu bar
        binding.bottomNavBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(FavoriteActivity.this,HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.media:
                        Intent intentM = new Intent(FavoriteActivity.this,MediaActivity.class);
                        startActivity(intentM);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.favorite:
                        return true;
                }
                return true;
            }
        });



        dataViewModel.getFavoriteByUser(userId).observe(FavoriteActivity.this, new Observer<UserWithVideos>() {
            @Override
            public void onChanged(UserWithVideos userWithVideos) {
                ArrayList<Media> mediaArrayList = new ArrayList<>();
                for (Media vid : userWithVideos.getVideos()){
                    mediaArrayList.add(vid);
                }
                arrayAdapter = new ArrayAdapter(FavoriteActivity.this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,mediaArrayList);
                binding.listViewFavorite.setAdapter(arrayAdapter);

                binding.listViewFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Intent intent = new Intent(FavoriteActivity.this,EditActivity.class);
                        intent.putExtra("from_favorite_activity",true);
                        intent.putExtra("media_id",mediaArrayList.get(position).getMediaId());
                        intent.putExtra("media_uri",mediaArrayList.get(position).getMediaPath());

                        startActivity(intent);

                    }
                });
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        binding.bottomNavBar.setSelectedItemId(R.id.favorite);
        overridePendingTransition(0, 0);
    }
}