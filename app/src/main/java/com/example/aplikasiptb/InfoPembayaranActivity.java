package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoPembayaranActivity extends AppCompatActivity {

    ImageView iconBack;
    Integer idHomestay,total;
    String namaBank,noRek,uangDp;
    TextView bankNama,totalBayar,dpUang,rekNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pembayaran);

        idHomestay = getIntent().getIntExtra("idHomestay",0);
        uangDp = getIntent().getStringExtra("uangDp");
        total = getIntent().getIntExtra("total",0);
        namaBank = getIntent().getStringExtra("namaBank");
        noRek = getIntent().getStringExtra("noRek");

        bankNama = findViewById(R.id.namaBank);
        totalBayar = findViewById(R.id.totalBayar);
        dpUang = findViewById(R.id.uangDp);
        rekNo = findViewById(R.id.noRekening);

        bankNama.setText(namaBank);
        totalBayar.setText("Rp. "+total.toString());
        dpUang.setText(uangDp);
        rekNo.setText(noRek);

        iconBack = (ImageView) findViewById(R.id.backImg);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void toDetailHomestay(View view){
        Intent intent = new Intent(this, DetailHomestayActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, BookingActivity.class);
        startActivity(intent);
        finish();
    }
}