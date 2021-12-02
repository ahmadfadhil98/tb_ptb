package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class Homestay {
    public Integer id;
    public String nama;
    public String jenis;
    public Integer rating;
    public String foto;
    public String website;
    public String noHp;
    public String alamat;

    public Homestay(Integer id, String nama, String jenis, Integer rating, String foto, String website, String noHp, String alamat) {
        this.id = id;
        this.nama = nama;
        this.jenis = jenis;
        this.rating = rating;
        this.foto = foto;
        this.website = website;
        this.noHp = noHp;
        this.alamat = alamat;
    }

}
