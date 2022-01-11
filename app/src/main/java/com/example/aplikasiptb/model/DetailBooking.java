package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class DetailBooking{

    @SerializedName("check_out")
    private String checkOut;

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