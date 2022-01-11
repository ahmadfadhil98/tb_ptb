package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.adapter.NotifikasiAdapter;
import com.example.aplikasiptb.model.Notifikasi;
import com.example.aplikasiptb.model.NotifikasiItem;
import com.example.aplikasiptb.model.NotifikasiList;
import com.example.aplikasiptb.retrofit.PortalClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifikasiActivity extends AppCompatActivity {

    RecyclerView rvNotifikasi;
    NotifikasiAdapter notifikasiAdapter;
    ImageView iconBack;
    String token, baseUrl;
    PortalClient portalClient;

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
        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        token = preferences.getString("TOKEN","");

        baseUrl = getString(R.string.apiUrlLumen);
        Authent authent = new Authent();
        portalClient = authent.setPortalClient(baseUrl);
        Call<NotifikasiList> call = portalClient.getNotif(token);
        call.enqueue(new Callback<NotifikasiList>() {
            @Override
            public void onResponse(Call<NotifikasiList> call, Response<NotifikasiList> response) {
                NotifikasiList notifikasiList = response.body();
                ArrayList<Notifikasi> notifikasis = new ArrayList<>();
                if(notifikasiList!=null){
                    List<NotifikasiItem> notifikasiItems = notifikasiList.getNotifikasi();
                    for (NotifikasiItem item : notifikasiItems){
                        SimpleDateFormat formatakhir = new SimpleDateFormat("dd-MM-yyyy, hh:mm");
                        String tgl = formatakhir.format(item.getCreatedAt());
                        Notifikasi notifikasi = new Notifikasi(item.getTitle(),tgl,item.getMessage());
                        notifikasis.add(notifikasi);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Tidak ada response", Toast.LENGTH_SHORT).show();
                }
                notifikasiAdapter.setListNotifikasi(notifikasis);
            }

            @Override
            public void onFailure(Call<NotifikasiList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
            }
        });

//        notifikasiAdapter.setListNotifikasi(generateData());

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);

        rvNotifikasi = findViewById(R.id.rvNotifikasi);
        rvNotifikasi.setAdapter(notifikasiAdapter);
        rvNotifikasi.setLayoutManager(layoutManager);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
        finish();
    }
}