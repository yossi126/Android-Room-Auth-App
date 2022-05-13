package com.example.authapp.util;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.authapp.entity.Media;
import com.example.authapp.entity.Users;
import com.example.authapp.entity.UsersMediaCrossRef;
import com.example.authapp.entity.relationship.UserWithVideos;
import com.example.authapp.subset.MediaPathColSubset;

import java.util.List;

public class DataViewModel extends AndroidViewModel {

    public RoomRepository roomRepository;

    public DataViewModel(@NonNull Application application) {
        super(application);
        roomRepository = new RoomRepository(application);
    }

    public void insertNewUser(Users user){
        roomRepository.insertNewUser(user);
    }

    public LiveData<Users> userLoginAuth(String email, String password){
        return roomRepository.userLoginAuth(email,password);
    }


    // media

    public LiveData<List<Media>> getAllMedia(){
        return roomRepository.getAllMedia();
    }

    //public LiveData<List<MediaPathColSubset>> getMediaPath(){return roomRepository.getMediaPath();}

    public void resetMedia(){roomRepository.resetMedia();}


    // cross ref
    public void insertUserMediaCrossRef(UsersMediaCrossRef usersMediaCrossRef){
        roomRepository.insertUserMediaCrossRef(usersMediaCrossRef);
    }

    //favorite
    public LiveData<UserWithVideos> getFavoriteByUser(int id){
        return roomRepository.getFavoriteByUser(id);
    }


    public LiveData<Integer> isExistsUsersMediaCrossRef(int userId, int mediaId){
        return roomRepository.isExistsUsersMediaCrossRef(userId,mediaId);
    }

    public void removeFavorite(UsersMediaCrossRef usersMediaCrossRef){
        roomRepository.removeFavorite(usersMediaCrossRef);
    }

    public void deleteAllFavoritesByUser(){
        roomRepository.deleteAllFavoritesByUser();
    }


}
