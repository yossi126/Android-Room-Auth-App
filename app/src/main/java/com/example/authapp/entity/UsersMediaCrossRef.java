package com.example.authapp.entity;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(primaryKeys = {"userId","mediaId"})
public class UsersMediaCrossRef {

    private int userId;
    private int mediaId;

    public UsersMediaCrossRef() {
    }

    @Ignore
    public UsersMediaCrossRef(int userId, int mediaId) {
        this.userId = userId;
        this.mediaId = mediaId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String toString() {
        return "UsersMediaCrossRef{" +
                "userId=" + userId +
                ", mediaId=" + mediaId +
                '}';
    }
}
