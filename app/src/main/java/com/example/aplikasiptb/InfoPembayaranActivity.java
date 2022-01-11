package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.model.DBooking;
import com.example.aplikasiptb.model.DetailBooking;
import com.example.aplikasiptb.model.UnitBookingItem;
import com.example.aplikasiptb.retrofit.PortalClient;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoPembayaranActivity extends AppCompatActivity {

    ImageView iconBack;
    Integer idHomestay,idBooking,total,homestayId,idHome;
    TextView bankNama,totalBayar,dpUang,rekNo,refNo;
    TextView textAtm,iBank,mBank;
    TextInputLayout tilAtm,tiliBank,tilmBank;

    String token,baseUrl;
    PortalClient portalClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pembayaran);

        idHomestay = getIntent().getIntExtra("idHomestay",0);
        homestayId = getIntent().getIntExtra("homestayId",0);

        if(idHomestay!=0){
            idHome = idHomestay;
        }else{
            idHome = homestayId;
        }

        idBooking = getIntent().getIntExtra("idBooking",0);

        bankNama = findViewById(R.id.namaBank);
        totalBayar = findViewById(R.id.totalBayar);
        dpUang = findViewById(R.id.uangDp);
        rekNo = findViewById(R.id.noRek);
        refNo = findViewById(R.id.referensiNo);

        textAtm = findViewById(R.id.textAtm);
        iBank = findViewById(R.id.textIBank);
        mBank = findViewById(R.id.textMBank);

        tilAtm = findViewById(R.id.petunjukAtm);
        tiliBank = findViewById(R.id.petunjukIBanking);
        tilmBank = findViewById(R.id.petunjukMBanking);

        textAtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tilAtm.getVisibility()==View.VISIBLE){
                    tilAtm.setVisibility(View.GONE);
                }else{
                    tilAtm.setVisibility(View.VISIBLE);
                }
            }
        });

        iBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tiliBank.getVisibility()==View.VISIBLE){
                    tiliBank.setVisibility(View.GONE);
                }else{
                    tiliBank.setVisibility(View.VISIBLE);
                }
            }
        });

        mBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tilmBank.getVisibility()==View.VISIBLE){
                    tilmBank.setVisibility(View.GONE);
                }else{
                    tilmBank.setVisibility(View.VISIBLE);
                }
            }
        });

        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        token = preferences.getString("TOKEN","");

        baseUrl = getString(R.string.apiUrlLumen);

        Authent authent = new Authent();
        portalClient = authent.setPortalClient(baseUrl);

        Call<DBooking> call = portalClient.getDBook(token,idBooking);
        call.enqueue(new Callback<DBooking>() {
            @Override
            public void onResponse(Call<DBooking> call, Response<DBooking> response) {
                DBooking dBooking = response.body();
                if(dBooking!=null){
                    DetailBooking detailBooking = dBooking.getDetailBooking();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String checkIn = detailBooking.getCheckIn();
                    String checkOut = detailBooking.getCheckOut();

                    long tarifHome = 0;

                    List<UnitBookingItem> unitBookingItems = dBooking.getUnitBooking();
                    for (UnitBookingItem item:unitBookingItems){
                        try {
                            Date dateCheckIn = simpleDateFormat.parse(checkIn);
                            Date dateCheckOut = simpleDateFormat.parse(checkOut);

                            long time_difference = dateCheckOut.getTime() - dateCheckIn.getTime();
                            long hours_difference = (time_difference / (1000*60*60));
                            long tarifLong =  (hours_difference/24) * item.getHarga();

                            tarifHome = tarifLong + tarifHome;

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    long uang_muka = tarifHome/2;

                    totalBayar.setText("Rp. "+Long.toString(tarifHome)+",-");
                    dpUang.setText("Rp. "+Long.toString(uang_muka)+",-");

                    bankNama.setText(detailBooking.getNamaBank());
                    rekNo.setText(detailBooking.getNoRekening());
                    refNo.setText(detailBooking.getToken());

                }else{
                    Toast.makeText(getApplicationContext(),"Tidak ada response",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DBooking> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });


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
        intent.putExtra("idHomestay",idHome);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
        finish();
    }
}