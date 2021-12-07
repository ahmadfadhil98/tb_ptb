package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class AvatarItem{

    @SerializedName("path")
    private String path;

    public String getPath(){
        return path;
    }
}