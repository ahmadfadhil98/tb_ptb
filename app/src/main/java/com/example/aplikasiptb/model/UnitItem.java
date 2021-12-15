package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;

public class UnitItem{

    @SerializedName("nama")
    private String nama;

    @SerializedName("harga")
    private int harga;

    @SerializedName("id")
    private int id;

    public String getNama(){
        return nama;
    }

    public int getHarga(){
        return harga;
    }

    public int getId(){
        return id;
    }
}