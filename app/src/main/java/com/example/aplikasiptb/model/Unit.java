package com.example.aplikasiptb.model;

import java.math.BigInteger;

public class Unit {
    public Integer id;
    public String nama;
    public BigInteger harga;
    public String foto;

    public Unit(Integer id, String nama, BigInteger harga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
    }

    public Integer getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public BigInteger getHarga() {
        return harga;
    }

    public String getFoto() {
        return foto;
    }
}
