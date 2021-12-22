package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class HomeItem{

    @SerializedName("website")
    private String website;

    @SerializedName("nama")
    private String nama;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("no_hp")
    private String noHp;

    @SerializedName("foto")
    private String foto;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("rating")
    private double rating;

    @SerializedName("id")
    private int id;

    @SerializedName("alamat")
    private String alamat;

    public String getWebsite(){
        return website;
    }

    public String getNama(){
        return nama;
    }

    public String getNoHp(){
        return noHp;
    }

    public String getFoto(){
        return foto;
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

    public String getAlamat(){
        return alamat;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}