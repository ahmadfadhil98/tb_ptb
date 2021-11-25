package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class HomestayItem{

    @SerializedName("nama")
    private String nama;

    @SerializedName("foto")
    private String foto;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("id")
    private int id;

    public String getNama(){
        return nama;
    }

    public String getFoto(){
        return foto;
    }

    public String getJenis(){
        return jenis;
    }

    public int getId(){
        return id;
    }
}