package com.example.aplikasiptb.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PembayaranList{

    @SerializedName("pembayaran")
    private List<PembayaranItem> pembayaran;

    @SerializedName("jumlah")
    private int jumlah;

    public List<PembayaranItem> getPembayaran(){
        return pembayaran;
    }

    public int getJumlah(){
        return jumlah;
    }
}