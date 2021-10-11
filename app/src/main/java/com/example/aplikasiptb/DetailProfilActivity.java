package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DetailProfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profil);
    }

    public void toUbahPassword(View view){
        Intent intent = new Intent(this, UbahPassswordActivity.class);
        startActivity(intent);
    }

    public void toEditProfil(View view){
        Intent intent = new Intent(this, UpdateProfilActivity.class);
        startActivity(intent);
    }
}