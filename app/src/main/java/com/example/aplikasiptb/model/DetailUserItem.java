package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class DetailUserItem{

    @SerializedName("jk")
    private int jk;

    @SerializedName("nama")
    private String nama;

    @SerializedName("no_hp")
    private String noHp;

    @SerializedName("tempat_lahir")
    private String tempatLahir;

    @SerializedName("foto")
    private String foto;

    @SerializedName("id")
    private int id;

    @SerializedName("tgl_lahir")
    private String tglLahir;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    public int getJk(){
        return jk;
    }

    public String getNama(){
        return nama;
    }

    public String getNoHp(){
        return noHp;
    }

    public String getTempatLahir(){
        return tempatLahir;
    }

    public String getFoto(){
        return foto;
    }

    public int getId(){
        return id;
    }

    public String getTglLahir(){
        return tglLahir;
    }

    public String getEmail(){
        return email;
    }

    public String getUsername(){
        return username;
    }
}