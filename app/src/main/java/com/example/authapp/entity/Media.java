package com.example.authapp.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Media {

    @PrimaryKey()
    private int mediaId;
    private String mediaPath;

    public Media() {
    }
    @Ignore
    public Media(int mediaId, String mediaPath) {
        this.mediaId = mediaId;
        this.mediaPath = mediaPath;
    }


    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public int getMediaId() {
        return mediaId;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    @Override
    public String toString() {
        return "Media{" +
                "mediaId=" + mediaId +
                ", mediaPath='" + mediaPath + '\'' +
                '}';
    }
}
