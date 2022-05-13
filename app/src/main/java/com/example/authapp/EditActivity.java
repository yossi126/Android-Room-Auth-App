package com.example.authapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import com.example.authapp.databinding.ActivityEditBinding;
import com.example.authapp.entity.UsersMediaCrossRef;
import com.example.authapp.util.DataViewModel;

public class EditActivity extends AppCompatActivity {

    private ActivityEditBinding binding;
    private DataViewModel dataViewModel;
    private int userId;
    private String userName;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataViewModel = new ViewModelProvider(EditActivity.this).get(DataViewModel.class);
        sharedPreferences = getApplicationContext().getSharedPreferences("UserPref",MODE_PRIVATE);

        userId = sharedPreferences.getInt("UserId",0);

        if (getIntent().hasExtra("From_Media_Activity")) {
            //check if media is in favorite already so the button disappear
            int mediaId = getIntent().getIntExtra("media_id", 0);
            binding.deleteFromFavoriteImageVIew.setVisibility(View.GONE);
            dataViewModel.isExistsUsersMediaCrossRef(userId, mediaId).observe(this, integer -> {
                if (integer.intValue() == 1){
                    binding.addToFavoriteImageVIew.setImageResource(R.drawable.ic_baseline_star_yellow);
                    binding.addToFavoriteImageVIew.setEnabled(false);
                }

                    //binding.addToFavoriteImageVIew.setVisibility(View.GONE);
            });

            binding.addToFavoriteImageVIew.setOnClickListener(view -> {
                UsersMediaCrossRef usersMediaCrossRef = new UsersMediaCrossRef(userId, mediaId);
                dataViewModel.insertUserMediaCrossRef(usersMediaCrossRef);
            });
            displayVideo(getIntent().getStringExtra(MediaActivity.MEDIA_URI));

        } else if (getIntent().hasExtra("from_favorite_activity")) {
            int mediaId = getIntent().getIntExtra("media_id", 0);
            dataViewModel.isExistsUsersMediaCrossRef(userId, mediaId).observe(this, integer -> {
                if (integer.intValue() == 1){
                    //Log.d("TAG", "onCreate: "+integer.intValue());
                    binding.deleteFromFavoriteImageVIew.setVisibility(View.VISIBLE);
                    binding.addToFavoriteImageVIew.setVisibility(View.GONE);
                }
            });
            binding.deleteFromFavoriteImageVIew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UsersMediaCrossRef usersMediaCrossRef = new UsersMediaCrossRef(userId,mediaId);
                    dataViewModel.removeFavorite(usersMediaCrossRef);
                }
            });
            dataViewModel.isExistsUsersMediaCrossRef(userId, mediaId).observe(this, integer -> {
                if (integer.intValue() == 0){
                    binding.deleteFromFavoriteImageVIew.setVisibility(View.GONE);
                    binding.addToFavoriteImageVIew.setVisibility(View.GONE);
                }
            });

            displayVideo(getIntent().getStringExtra("media_uri"));
        }


    }



    public void displayVideo(String uri){
        binding.editVideoView.setVideoURI(Uri.parse(uri));
        MediaController mediaController = new MediaController(EditActivity.this);
        binding.editVideoView.setMediaController(mediaController);
        mediaController.setAnchorView(binding.editVideoView);
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
}