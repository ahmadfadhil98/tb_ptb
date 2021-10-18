package com.example.aplikasiptb.model;

import java.util.Date;

public class Review {
//    public String avatar;
    public String nama;
    public String komen;
    public Integer star;
    public String tanggal;

    public Review( String nama, String komen, Integer star, String tanggal) {
//        this.avatar = avatar;
        this.nama = nama;
        this.komen = komen;
        this.star = star;
        this.tanggal = tanggal;
    }
}
