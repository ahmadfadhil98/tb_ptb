package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class UnitBookingItem{

    @SerializedName("nama")
    private String nama;

    @SerializedName("harga")
    private int harga;

    public String getNama(){
        return nama;
    }

    public int getHarga(){
        return harga;
    }
}