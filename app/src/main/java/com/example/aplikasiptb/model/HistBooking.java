package com.example.aplikasiptb.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HistBooking{

    @SerializedName("list_booking")
    private List<ListBookingItem> listBooking;

    public List<ListBookingItem> getListBooking(){
        return listBooking;
    }
}