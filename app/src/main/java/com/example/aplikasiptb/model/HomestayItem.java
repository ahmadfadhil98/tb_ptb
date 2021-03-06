package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class HomestayItem{

    @SerializedName("nama")
    private String nama;

    @SerializedName("foto")
    private String foto;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("rating")
    private double rating;

    @SerializedName("id")
    private int id;

    @SerializedName("longitude")
    private double longitude;

    public String getNama(){
        return nama;
    }

    public String getFoto(){
        return foto;
    }

    public double getLatitude(){
        return latitude;
    }

    public String getJenis(){
        return jenis;
    }

    public double getRating(){
        return rating;
    }

    public int getId(){
        return id;
    }

    public double getLongitude(){
        return longitude;
    }
}