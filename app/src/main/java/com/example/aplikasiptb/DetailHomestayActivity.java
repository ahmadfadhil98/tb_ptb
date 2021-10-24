package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailHomestayActivity extends AppCompatActivity {

    TextView urlWeb,textNohp;
    ImageView iconBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_homestay);

        iconBack = findViewById(R.id.backImg);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        urlWeb = (TextView) findViewById(R.id.textUrl);
        urlWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlWeb.getText().toString();
                Uri webUri = Uri.parse(url);

                Intent webIntent = new Intent();
                webIntent.setAction(Intent.ACTION_VIEW);
                webIntent.setData(webUri);
                if (webIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(webIntent);
                }

            }
        });

        textNohp = (TextView) findViewById(R.id.textPhone);
        textNohp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nohHp = "tel:"+textNohp.getText().toString();
                Uri noHpUri = Uri.parse(nohHp);

                Intent dialIntent = new Intent();
                dialIntent.setAction(Intent.ACTION_DIAL);
                dialIntent.setData(noHpUri);
                if (dialIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(dialIntent);
                }else{
                    Toast.makeText(DetailHomestayActivity.this, "Aplikasi Untuk melakukan Dial tidak ada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void toReview(View view){
        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
        finish();
    }

    public void toBooking(View view){
        Intent intent = new Intent(this, BookingActivity.class);
        startActivity(intent);
        finish();
    }

    public void toMapArah(View view){
        Intent intent = new Intent(this, ArahActivity.class);
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