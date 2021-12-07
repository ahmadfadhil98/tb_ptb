package com.example.aplikasiptb.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AvatarList{

    @SerializedName("avatar")
    private List<AvatarItem> avatar;

    public List<AvatarItem> getAvatar(){
        return avatar;
    }
}