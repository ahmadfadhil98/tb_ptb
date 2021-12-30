package com.example.aplikasiptb.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NotifikasiList{

    @SerializedName("notifikasi")
    private List<NotifikasiItem> notifikasi;

    public List<NotifikasiItem> getNotifikasi(){
        return notifikasi;
    }
}