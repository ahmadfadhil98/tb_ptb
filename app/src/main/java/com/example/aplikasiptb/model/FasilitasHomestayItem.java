package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class FasilitasHomestayItem{

    @SerializedName("nama")
    private String nama;

    @SerializedName("jumlah")
    private int jumlah;

    @SerializedName("fasilitas_id")
    private int fasilitasId;

    @SerializedName("homestay_id")
    private int homestayId;

    public String getNama(){
        return nama;
    }

    public int getJumlah(){
        return jumlah;
    }

    public int getFasilitasId(){
        return fasilitasId;
    }

    public int getHomestayId(){
        return homestayId;
    }
}