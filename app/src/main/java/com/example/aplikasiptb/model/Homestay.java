package com.example.aplikasiptb.model;

public class Homestay {
    public Integer id;
    public String nama;
    public String jenis;
    public Integer rating;
    public String foto;

    public Homestay(Integer id,String nama, String jenis, Integer rating,String foto) {
        this.id = id;
        this.nama = nama;
        this.jenis = jenis;
        this.rating = rating;
        this.foto = foto;
    }
}
