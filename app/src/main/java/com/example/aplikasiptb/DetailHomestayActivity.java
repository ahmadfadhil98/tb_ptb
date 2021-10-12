package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailHomestayActivity extends AppCompatActivity {

    TextView iconBack,urlWeb,textNohp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_homestay);

        iconBack = (TextView) findViewById(R.id.head);
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

//        textNohp = (TextView) findViewById(R.id.nohp);
//        textNohp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String nohHp = "tel:"+textNohp.getText().toString();
//                Uri noHpUri = Uri.parse(nohHp);
//
//                Intent dialIntent = new Intent();
//                dialIntent.setAction(Intent.ACTION_DIAL);
//                dialIntent.setData(noHpUri);
//                startActivity(dialIntent);
//            }
//        });
    }

    public void toReview(View view){
        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
    }

    public void toBooking(View view){
        Intent intent = new Intent(this, BookingActivity.class);
        startActivity(intent);
    }

    public void toMapArah(View view){
        Intent intent = new Intent(this, ArahActivity.class);
        startActivity(intent);
    }
}