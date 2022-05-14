package com.example.authapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MediaController;

import com.example.authapp.atapter.FavoriteAdapter;

import com.example.authapp.databinding.ActivityFavoriteBinding;
import com.example.authapp.entity.Media;
import com.example.authapp.entity.UsersMediaCrossRef;
import com.example.authapp.entity.relationship.UserWithVideos;
import com.example.authapp.util.DataViewModel;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements FavoriteAdapter.OnItemClickListener{

    private ActivityFavoriteBinding binding;
    private DataViewModel dataViewModel;
    private int userId;
    private String userName;
    private SharedPreferences sharedPreferences;
    private FavoriteAdapter favoriteAdapter;

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



        binding.favoriteRecyclerView.setHasFixedSize(true);
        binding.favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataViewModel.getFavoriteByUser(userId).observe(FavoriteActivity.this, new Observer<UserWithVideos>() {
            @Override
            public void onChanged(UserWithVideos userWithVideos) {
                favoriteAdapter= new FavoriteAdapter(userWithVideos.getVideos(),FavoriteActivity.this,dataViewModel,FavoriteActivity.this);
                binding.favoriteRecyclerView.setAdapter(favoriteAdapter);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        binding.bottomNavBar.setSelectedItemId(R.id.favorite);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onItemClick(String uri) {
            displayVideo(uri);
    }

    @Override
    public void onDeleteClick(int mediaId) {
        UsersMediaCrossRef usersMediaCrossRef = new UsersMediaCrossRef(userId,mediaId);
        dataViewModel.removeFavorite(usersMediaCrossRef);
    }

    public void displayVideo(String uri){
        binding.favoriteVideoView.setVideoURI(Uri.parse(uri));
        MediaController mediaController = new MediaController(FavoriteActivity.this);
        binding.favoriteVideoView.setMediaController(mediaController);
        mediaController.setAnchorView(binding.favoriteVideoView);
    }
}













//    private void isInFavorite() {
//        new Thread(new Runnable() {
//            int userId = HomeActivity.USER_ID_INT;
//            int mediaId = getIntent().getIntExtra("media_id", 0);
//
//            @Override
//            public void run() {
//                int isExists = dataViewModel.isExistsUsersMediaCrossRef(userId, mediaId);
//                if (isExists == 1) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            binding.addToFavoriteImageVIew.setVisibility(View.GONE);
//                            binding.deleteFromFavoriteImageVIew.setVisibility(View.VISIBLE);
//                            //Toast.makeText(EditActivity.this, "user added", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    binding.addToFavoriteImageVIew.setVisibility(View.VISIBLE);
//                    binding.deleteFromFavoriteImageVIew.setVisibility(View.GONE);
//                }
//            }
//        }).start();
//    }