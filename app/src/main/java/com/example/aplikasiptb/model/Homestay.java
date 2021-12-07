package com.example.aplikasiptb.model;

import com.google.gson.annotations.SerializedName;

public class Homestay {
    public Integer id;
    public String nama;
    public String jenis;
    public Double rating;
    public String foto;

    public Homestay(Integer id, String nama, String jenis, Double rating, String foto) {
        this.id = id;
        this.nama = nama;
        this.jenis = jenis;
        this.rating = rating;
        this.foto = foto;
    }

}
