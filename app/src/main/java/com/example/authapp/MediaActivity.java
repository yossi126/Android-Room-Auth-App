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
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;


import com.example.authapp.atapter.UserCardAdapter;


import com.example.authapp.databinding.ActivityMediaBinding;
import com.example.authapp.entity.Media;
import com.example.authapp.entity.UsersMediaCrossRef;
import com.example.authapp.subset.MediaPathColSubset;
import com.example.authapp.util.DataViewModel;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MediaActivity extends AppCompatActivity implements UserCardAdapter.OnItemClickListener{

    private ActivityMediaBinding binding;
    private DataViewModel dataViewModel;
    public static String MEDIA_URI = "media_uri";
    private SharedPreferences sharedPreferences;
    private int userId;
    private String userName;
    private UserCardAdapter userCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_media);
        binding = ActivityMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataViewModel = new ViewModelProvider(MediaActivity.this).get(DataViewModel.class);
        sharedPreferences = getApplicationContext().getSharedPreferences("UserPref",MODE_PRIVATE);
        userId = sharedPreferences.getInt("UserId",0);

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
                        Intent intent = new Intent(MediaActivity.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        binding.bottomNavBar.setSelectedItemId(R.id.media);
        binding.bottomNavBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intent = new Intent(MediaActivity.this,HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.media:
                        return true;
                    case R.id.favorite:
                        Intent intentM = new Intent(MediaActivity.this,FavoriteActivity.class);
                        startActivity(intentM);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return true;
            }
        });

        binding.recyclerViewMedia.setHasFixedSize(true);
        binding.recyclerViewMedia.setLayoutManager(new LinearLayoutManager(this));
        dataViewModel.getAllMedia().observe(MediaActivity.this, new Observer<List<Media>>() {
            @Override
            public void onChanged(List<Media> media) {
                userCardAdapter = new UserCardAdapter(media,MediaActivity.this,dataViewModel,MediaActivity.this);
                binding.recyclerViewMedia.setAdapter(userCardAdapter);
            }
        });


        // subset not working - later

//        dataViewModel.getMediaPath().observe(MediaActivity.this, new Observer<List<MediaPathColSubset>>() {
//            @Override
//            public void onChanged(List<MediaPathColSubset> mediaPathColSubsets) {
//                for (MediaPathColSubset mp : mediaPathColSubsets){
//                    Log.d("TAG", " "+mp.getMediaPath());
//                }
//
//                //ArrayAdapter arrayAdapter2 = new ArrayAdapter(MediaActivity.this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,mediaPathColSubsets);
//                //binding.listViewForObserver.setAdapter(arrayAdapter2);
//            }
//        });

//        arrayAdapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, movies);
//        binding.listView.setAdapter(arrayAdapter);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        binding.bottomNavBar.setSelectedItemId(R.id.media);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onItemClick(String uri) {
        //Toast.makeText(MediaActivity.this,uri,Toast.LENGTH_SHORT).show();
        displayVideo(uri);
    }


    @Override
    public void onAddClick(int position) {
        UsersMediaCrossRef usersMediaCrossRef = new UsersMediaCrossRef(userId, position);
        dataViewModel.insertUserMediaCrossRef(usersMediaCrossRef);
    }

    public void displayVideo(String uri){
        binding.showVideoView.setVideoURI(Uri.parse(uri));
        MediaController mediaController = new MediaController(MediaActivity.this);
        binding.showVideoView.setMediaController(mediaController);
        mediaController.setAnchorView(binding.showVideoView);
    }
}