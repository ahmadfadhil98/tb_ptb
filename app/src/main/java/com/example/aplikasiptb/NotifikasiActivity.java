package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aplikasiptb.adapter.NotifikasiAdapter;
import com.example.aplikasiptb.model.Notifikasi;

import java.util.ArrayList;

public class NotifikasiActivity extends AppCompatActivity {

    RecyclerView rvNotifikasi;
    NotifikasiAdapter notifikasiAdapter;
    ImageView iconBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        iconBack = findViewById(R.id.backImg);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        notifikasiAdapter = new NotifikasiAdapter();
        notifikasiAdapter.setListNotifikasi(generateData());

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);

        rvNotifikasi = findViewById(R.id.rvNotifikasi);
        rvNotifikasi.setAdapter(notifikasiAdapter);
        rvNotifikasi.setLayoutManager(layoutManager);
    }

    public ArrayList<Notifikasi> generateData(){
        ArrayList<Notifikasi> listNotifikasi = new ArrayList<>();
        listNotifikasi.add(new Notifikasi("Selamat!",
                "19-02-2009",
                "Booking Anda di homestay A Berhasil. Lakukan pembayaran Segera"));
        listNotifikasi.add(new Notifikasi("Selamat!",
                "19-02-2009",
                "Booking Anda di homestay B Berhasil. Lakukan pembayaran Segera"));
        listNotifikasi.add(new Notifikasi("Selamat!",
                "19-02-2009",
                "Booking Anda di homestay C Berhasil. Lakukan pembayaran Segera"));
        listNotifikasi.add(new Notifikasi("Selamat!",
                "19-02-2009",
                "Booking Anda di homestay D Berhasil. Lakukan pembayaran Segera"));
        listNotifikasi.add(new Notifikasi("Selamat!",
                "19-02-2009",
                "Booking Anda di homestay E Berhasil. Lakukan pembayaran Segera"));
        return listNotifikasi;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
        finish();
    }
}