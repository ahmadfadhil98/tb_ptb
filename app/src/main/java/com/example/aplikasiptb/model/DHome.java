package com.example.aplikasiptb.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DHome{

    @SerializedName("home")
    private List<HomeItem> home;

    public List<HomeItem> getHome(){
        return home;
    }
}