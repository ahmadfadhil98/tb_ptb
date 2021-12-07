package com.example.aplikasiptb.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DUser{

    @SerializedName("detail_user")
    private List<DetailUserItem> detailUser;

    @SerializedName("idUser")
    private int idUser;

    public List<DetailUserItem> getDetailUser(){
        return detailUser;
    }

    public int getIdUser() {
        return idUser;
    }
}