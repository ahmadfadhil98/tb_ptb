package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ListBookingItem{

    @SerializedName("check_out")
    private String checkOut;

    @SerializedName("tarif")
    private Integer tarif;

    @SerializedName("nama")
    private String nama;

    @SerializedName("updated_at")
    private Date updatedAt;

    @SerializedName("check_in")
    private String checkIn;

    @SerializedName("no_rekening")
    private String noRekening;

    @SerializedName("homestay_id")
    private int homestayId;

    @SerializedName("id")
    private int id;

    @SerializedName("nama_bank")
    private String namaBank;

    @SerializedName("token")
    private String token;

    @SerializedName("status")
    private int status;

    public String getCheckOut(){
        return checkOut;
    }

    public Integer getTarif(){
        return tarif;
    }

    public String getNama(){
        return nama;
    }

    public Date getUpdatedAt(){
        return updatedAt;
    }

    public String getCheckIn(){
        return checkIn;
    }

    public String getNoRekening(){
        return noRekening;
    }

    public int getHomestayId(){
        return homestayId;
    }

    public int getId(){
        return id;
    }

    public String getNamaBank(){
        return namaBank;
    }

    public String getToken(){
        return token;
    }

    public int getStatus(){
        return status;
    }
}