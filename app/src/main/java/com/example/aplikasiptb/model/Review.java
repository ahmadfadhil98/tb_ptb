package com.example.aplikasiptb.model;

import java.util.Date;

public class Review {
    public String avatar;
    public String nama;
    public String komen;
    public Double star;
    public String tanggal;

    public Review(String avatar, String nama, String komen, Double star, String tanggal) {
        this.avatar = avatar;
        this.nama = nama;
        this.komen = komen;
        this.star = star;
        this.tanggal = tanggal;
    }
}
