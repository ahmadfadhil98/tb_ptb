package com.example.aplikasiptb.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DBooking{

    @SerializedName("detail_booking")
    private List<DetailBookingItem> detailBooking;

    public List<DetailBookingItem> getDetailBooking(){
        return detailBooking;
    }
}