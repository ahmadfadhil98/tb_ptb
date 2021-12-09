package com.example.aplikasiptb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    ImageView iconBack;
    Spinner unit_spin,tipe_homestay_spin,pembayaran_spin;
    List<String> plantlist;
    ArrayAdapter<String> spinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        iconBack = findViewById(R.id.backImg);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        unit_spin = findViewById(R.id.unit);
        tipe_homestay_spin = findViewById(R.id.tipe_homestay);
        pembayaran_spin = findViewById(R.id.pembayaran);

        String[] plants = new String[]{
                "Select an item...",
                "California sycamore",
                "Mountain mahogany",
                "Butterfly weed",
                "Carrot weed"
        };

        plantlist = new ArrayList<>(Arrays.asList(plants));

        setSpinner(plantlist);

        spinAdapter.setDropDownViewResource(R.layout.spinner_item_unit);

        unit_spin.setAdapter(spinAdapter);
        tipe_homestay_spin.setAdapter(spinAdapter);
        pembayaran_spin.setAdapter(spinAdapter);
    }

    public void setSpinner(List<String> list){
        spinAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item_unit,list){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
    }

    public void toInfoPembayaran(View view){
        Intent intent = new Intent(this, InfoPembayaranActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DetailHomestayActivity.class);
        startActivity(intent);
        finish();
    }
}