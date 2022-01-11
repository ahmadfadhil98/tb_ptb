package com.example.aplikasiptb.model;

public class HistoryBooking {
    public String nama;
    public String tgl;
    public Integer status;
    public Integer tarif;

    public HistoryBooking(String nama, String tgl, Integer status, Integer tarif) {
        this.nama = nama;
        this.tgl = tgl;
        this.status = status;
        this.tarif = tarif;
    }
}
