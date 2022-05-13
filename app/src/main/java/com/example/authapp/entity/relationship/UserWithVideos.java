package com.example.authapp.entity.relationship;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.authapp.entity.Media;
import com.example.authapp.entity.Users;
import com.example.authapp.entity.UsersMediaCrossRef;

import java.util.List;


public class UserWithVideos {
    @Embedded
    private Users user;

    @Relation(
            parentColumn = "userId",
            entityColumn = "mediaId",
            associateBy = @Junction(UsersMediaCrossRef.class)
    )
    private List<Media> Videos;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Media> getVideos() {
        return Videos;
    }

    public void setVideos(List<Media> videos) {
        Videos = videos;
    }
}
