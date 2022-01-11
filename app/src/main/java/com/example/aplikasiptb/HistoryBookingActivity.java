package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aplikasiptb.adapter.HistoryAdapter;
import com.example.aplikasiptb.model.HistBooking;
import com.example.aplikasiptb.model.HistoryBooking;
import com.example.aplikasiptb.model.ListBookingItem;
import com.example.aplikasiptb.retrofit.PortalClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryBookingActivity extends AppCompatActivity {

    RecyclerView rvHistory;
    HistoryAdapter historyAdapter;
    PortalClient portalClient;
    String token,baseUrl;
    ImageView iconBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_booking);

        iconBack = findViewById(R.id.backImgHist);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rvHistory = findViewById(R.id.rvHistory);
        historyAdapter = new HistoryAdapter();

        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        token = preferences.getString("TOKEN","");

        baseUrl = getString(R.string.apiUrlLumen);
        Authent authent = new Authent();
        portalClient = authent.setPortalClient(baseUrl);

        Call<HistBooking> call = portalClient.getHistBook(token);
        call.enqueue(new Callback<HistBooking>() {
            @Override
            public void onResponse(Call<HistBooking> call, Response<HistBooking> response) {
                HistBooking histBooking = response.body();
                ArrayList<HistoryBooking> list = new ArrayList<>();
                if(histBooking!=null){

                    List<ListBookingItem> listBookingItems = histBooking.getListBooking();
                    for (ListBookingItem item:listBookingItems){
                        Toast.makeText(getApplicationContext(), item.getNama(), Toast.LENGTH_SHORT).show();
                        SimpleDateFormat formatakhir = new SimpleDateFormat("dd MMMM yyyy");
                        String tglHist = formatakhir.format(item.getUpdatedAt());

                        HistoryBooking historyBooking = new HistoryBooking(item.getNama(),tglHist,item.getStatus());
                        list.add(historyBooking);
                    }
                    historyAdapter.setListHistory(list);
                }else{
                    Toast.makeText(getApplicationContext(), "ADa yang salah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HistBooking> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);

        rvHistory.setAdapter(historyAdapter);
        rvHistory.setLayoutManager(layoutManager);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ProfilActivity.class);
        startActivity(intent);
        finish();
    }
}