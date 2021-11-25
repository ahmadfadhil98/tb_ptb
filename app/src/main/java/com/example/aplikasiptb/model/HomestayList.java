package com.example.aplikasiptb.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomestayList{

    @SerializedName("homestay")
    private List<HomestayItem> homestay;

    public List<HomestayItem> getHomestay(){
        return homestay;
    }
}