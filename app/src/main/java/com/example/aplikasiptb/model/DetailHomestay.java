package com.example.aplikasiptb.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailHomestay{

    @SerializedName("detail_homestay")
    private DetailHomestay detailHomestay;

    @SerializedName("unit_home")
    private List<UnitHomeItem> unitHome;

    @SerializedName("fasilitas_home")
    private List<FasilitasHomeItem> fasilitasHome;

    @SerializedName("website")
    private String website;

    @SerializedName("nama")
    private String nama;

    @SerializedName("no_hp")
    private String noHp;

    @SerializedName("foto")
    private String foto;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("rating")
    private double rating;

    @SerializedName("id")
    private int id;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("longitude")
    private double longitude;

    public DetailHomestay getDetailHomestay(){
        return detailHomestay;
    }

    public List<UnitHomeItem> getUnitHome(){
        return unitHome;
    }

    public List<FasilitasHomeItem> getFasilitasHome(){
        return fasilitasHome;
    }

    public String getWebsite(){
        return website;
    }

    public String getNama(){
        return nama;
    }

    public String getNoHp(){
        return noHp;
    }

    public String getFoto(){
        return foto;
    }

    public double getLatitude(){
        return latitude;
    }

    public String getJenis(){
        return jenis;
    }

    public double getRating(){
        return rating;
    }

    public int getId(){
        return id;
    }

    public String getAlamat(){
        return alamat;
    }

    public double getLongitude(){
        return longitude;
    }
}