package com.example.aplikasiptb.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UnitList{

    @SerializedName("unit")
    private List<UnitItem> unit;

    public List<UnitItem> getUnit(){
        return unit;
    }
}