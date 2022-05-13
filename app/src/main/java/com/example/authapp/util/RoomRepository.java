package com.example.authapp.util;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import com.example.authapp.R;
import com.example.authapp.dao.AllDao;
import com.example.authapp.entity.Media;
import com.example.authapp.entity.Users;
import com.example.authapp.entity.UsersMediaCrossRef;
import com.example.authapp.entity.relationship.UserWithVideos;
import com.example.authapp.subset.MediaPathColSubset;

import java.util.List;

public class RoomRepository {

    private AllDao allDao;

    public RoomRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        allDao = db.allDao();
    }

    public void insertNewUser(Users user) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                allDao.insertNewUser(user);
            }
        });
    }

    public LiveData<Users> userLoginAuth(String email, String password) {
        return allDao.userLoginAuth(email, password);
    }

    // Media

    public LiveData<List<Media>> getAllMedia() {
        return allDao.getAllMedia();
    }

    //public LiveData<List<MediaPathColSubset>> getMediaPath(){return allDao.getMediaPath();}

    public void resetMedia() {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final boolean isTableEmpty = (allDao.checkIfTableMediaIsEmpty() == 0);
                if (!isTableEmpty) {
                    allDao.deleteAllMedia();
                }
                allDao.insertNewMedia(
                        new Media(1, "android.resource://com.example.authapp/" + R.raw.video1),
                        new Media(2, "android.resource://com.example.authapp/" + R.raw.video2),
                        new Media(3, "android.resource://com.example.authapp/" + R.raw.video3)
                );
            }
        });


    }


    // cross ref
    public void insertUserMediaCrossRef(UsersMediaCrossRef usersMediaCrossRef) {
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                allDao.insertUserMediaCrossRef(usersMediaCrossRef);
            }
        });
    }


    public LiveData<Integer> isExistsUsersMediaCrossRef(int userId, int mediaId) {
        return allDao.isExistsUsersMediaCrossRef(userId, mediaId);
    }

    //favorite
    public LiveData<UserWithVideos> getFavoriteByUser(int id) {
        return allDao.getFavoriteByUser(id);
    }

    public void removeFavorite(UsersMediaCrossRef usersMediaCrossRef){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                allDao.removeFavorite(usersMediaCrossRef);
            }
        });
    }


    public void deleteAllFavoritesByUser(){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                allDao.deleteAllFavoritesByUser();
            }
        });

    }
}
