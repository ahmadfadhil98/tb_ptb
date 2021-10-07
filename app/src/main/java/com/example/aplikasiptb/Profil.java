package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Profil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
    }

    public void toDetailProfil(View view){
        Intent intent = new Intent(this, DetailProfil.class);
        startActivity(intent);
    }

    public void toHome(View view){
        Intent intent = new Intent(this, Walkhthrough.class);
        startActivity(intent);
    }

    public void toNotifikasi(View view){
        Intent intent = new Intent(this, Notifikasi.class);
        startActivity(intent);
    }
}