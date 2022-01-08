package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class FasilitasHomeItem{

    @SerializedName("nama")
    private String nama;

    @SerializedName("jumlah")
    private int jumlah;

    public String getNama(){
        return nama;
    }

    public int getJumlah(){
        return jumlah;
    }
}