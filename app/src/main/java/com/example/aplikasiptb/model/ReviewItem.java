package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ReviewItem{

    @SerializedName("nama")
    private String nama;

    @SerializedName("foto")
    private String foto;

    @SerializedName("updated_at")
    private Date updatedAt;

    @SerializedName("komentar")
    private String komentar;

    @SerializedName("rating")
    private double rating;

    @SerializedName("homestay_id")
    private int homestayId;

    public String getNama(){
        return nama;
    }

    public Date getUpdatedAt(){
        return updatedAt;
    }

    public String getFoto(){
        return foto;
    }

    public String getKomentar(){
        return komentar;
    }

    public double getRating(){
        return rating;
    }

    public int getHomestayId(){
        return homestayId;
    }
}