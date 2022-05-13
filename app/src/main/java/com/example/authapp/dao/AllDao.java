package com.example.authapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.authapp.entity.Media;
import com.example.authapp.entity.Users;
import com.example.authapp.entity.UsersMediaCrossRef;
import com.example.authapp.entity.relationship.UserWithVideos;
import com.example.authapp.subset.MediaPathColSubset;

import java.util.List;

@Dao
public interface AllDao {

    // user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewUser(Users user);

    @Query("SELECT * FROM Users WHERE email =:email AND password =:password")
    LiveData<Users> userLoginAuth(String email, String password);


    // media
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewMedia(Media ... media);

    @Query("SELECT * FROM media")
    LiveData<List<Media>> getAllMedia();

//    @Query("SELECT mediaPath FROM media")
//    LiveData<List<MediaPathColSubset>> getMediaPath();

    @Query("SELECT EXISTS (SELECT mediaId FROM Media)")
    int checkIfTableMediaIsEmpty();

    @Query("DELETE FROM Media")
    void deleteAllMedia();


    // user media cross ref
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserMediaCrossRef(UsersMediaCrossRef usersMediaCrossRef);

    @Query("DELETE FROM UsersMediaCrossRef")
    void deleteAllFavoritesByUser();

    @Delete()
    void removeFavorite(UsersMediaCrossRef usersMediaCrossRef);

    @Query("DELETE  FROM UsersMediaCrossRef WHERE userId =:userId AND mediaId =:mediaId")
    void removeFavorite(int userId, int mediaId);

//    @Query("SELECT EXISTS(SELECT * FROM UsersMediaCrossRef WHERE userId =:userId AND mediaId =:mediaId)")
//    int isExistsUsersMediaCrossRef(int userId, int mediaId);

    @Query("SELECT EXISTS(SELECT * FROM UsersMediaCrossRef WHERE userId =:userId AND mediaId =:mediaId)")
    LiveData<Integer> isExistsUsersMediaCrossRef(int userId, int mediaId);


    //join
    @Transaction
    @Query("SELECT * FROM Users WHERE userId=:id")
    LiveData<UserWithVideos> getFavoriteByUser(int id);


    //select u.userName, m.mediaId from Users u inner join UsersMediaCrossRef umcr on u.userId = umcr.userId inner join Media m on umcr.mediaId = m.mediaId

    //select m.mediaPath from Users u inner join UsersMediaCrossRef umcr on u.userId = umcr.userId inner join Media m on umcr.mediaId = m.mediaId where u.userId ='1'

}
