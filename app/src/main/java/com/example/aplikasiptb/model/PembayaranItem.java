package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class PembayaranItem{

    @SerializedName("no_rekening")
    private String noRekening;

    @SerializedName("id")
    private int id;

    @SerializedName("nama_bank")
    private String namaBank;

    public String getNoRekening(){
        return noRekening;
    }

    public int getId(){
        return id;
    }

    public String getNamaBank(){
        return namaBank;
    }
}