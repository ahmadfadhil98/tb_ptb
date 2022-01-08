package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class UnitHomeItem{

    @SerializedName("nama")
    private String nama;

    @SerializedName("foto")
    private String foto;

    @SerializedName("harga")
    private int harga;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("homestay_id")
    private int homestayId;

    @SerializedName("id")
    private int id;

    @SerializedName("status")
    private int status;

    public String getNama(){
        return nama;
    }

    public String getFoto(){
        return foto;
    }

    public int getHarga(){
        return harga;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public int getHomestayId(){
        return homestayId;
    }

    public int getId(){
        return id;
    }

    public int getStatus(){
        return status;
    }
}