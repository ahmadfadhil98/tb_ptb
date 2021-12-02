package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class ReviewItem{

    @SerializedName("nama")
    private String nama;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("foto")
    private Object foto;

    @SerializedName("komentar")
    private String komentar;

    @SerializedName("rating")
    private int rating;

    public String getNama(){
        return nama;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public Object getFoto(){
        return foto;
    }

    public String getKomentar(){
        return komentar;
    }

    public int getRating(){
        return rating;
    }
}