package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class HomestayItem{

    @SerializedName("website")
    private String website;

    @SerializedName("nama")
    private String nama;

    @SerializedName("foto")
    private String foto;

    @SerializedName("no_hp")
    private String noHp;

    @SerializedName("jenis")
    private String jenis;

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

    public String getFoto(){
        return foto;
    }

    public String getNoHp(){
        return noHp;
    }

    public String getJenis(){
        return jenis;
    }

    public int getId(){
        return id;
    }

    public String getAlamat(){
        return alamat;
    }
}