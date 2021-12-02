package com.example.aplikasiptb.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class FasilitasHomestayList{

    @SerializedName("fasilitas_homestay")
    private List<FasilitasHomestayItem> fasilitasHomestay;

    public List<FasilitasHomestayItem> getFasilitasHomestay(){
        return fasilitasHomestay;
    }
}