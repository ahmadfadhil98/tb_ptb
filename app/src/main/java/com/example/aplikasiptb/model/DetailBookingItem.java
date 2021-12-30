package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class DetailBookingItem{

    @SerializedName("check_out")
    private String checkOut;

    @SerializedName("harga")
    private int harga;

    @SerializedName("check_in")
    private String checkIn;

    @SerializedName("no_rekening")
    private String noRekening;

    @SerializedName("nama_bank")
    private String namaBank;

    @SerializedName("token")
    private String token;

    public String getCheckOut(){
        return checkOut;
    }

    public int getHarga(){
        return harga;
    }

    public String getCheckIn(){
        return checkIn;
    }

    public String getNoRekening(){
        return noRekening;
    }

    public String getNamaBank(){
        return namaBank;
    }

    public String getToken(){
        return token;
    }
}