package com.example.aplikasiptb.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DBooking{

    @SerializedName("detail_booking")
    private DetailBooking detailBooking;

    @SerializedName("unit_booking")
    private List<UnitBookingItem> unitBooking;

    public DetailBooking getDetailBooking(){
        return detailBooking;
    }

    public List<UnitBookingItem> getUnitBooking(){
        return unitBooking;
    }
}