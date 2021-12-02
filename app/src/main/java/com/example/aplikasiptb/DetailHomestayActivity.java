package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.adapter.FasilitasAdapter;
import com.example.aplikasiptb.model.Fasilitas;
import com.example.aplikasiptb.model.FasilitasHomestayItem;
import com.example.aplikasiptb.model.FasilitasHomestayList;
import com.example.aplikasiptb.retrofit.PortalClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailHomestayActivity extends AppCompatActivity {

    TextView urlWeb,textNohp,textNamaHome,textJenis,textDRating,textAlamat;
    ImageView iconBack,imageHome;
    RecyclerView rvFasilitas;
    FasilitasAdapter fasilitasAdapter;
    Integer idHomestay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_homestay);

        imageHome = findViewById(R.id.imageHome);
        Picasso.get().load(getIntent().getStringExtra("foto")).into(imageHome);

        idHomestay = getIntent().getIntExtra("id",0);
        fasilitasAdapter = new FasilitasAdapter();

        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");

//        Toast.makeText(this,token,Toast.LENGTH_SHORT).show();

        Authent authent = new Authent();
        PortalClient portalClient = authent.setPortalClient(getString(R.string.apiUrlLumen));

        Call<FasilitasHomestayList> call = portalClient.getDFasilitas(token,idHomestay);
        call.enqueue(new Callback<FasilitasHomestayList>() {
            @Override
            public void onResponse(Call<FasilitasHomestayList> call, Response<FasilitasHomestayList> response) {
                FasilitasHomestayList fasilitasHomestayList = response.body();
                ArrayList<Fasilitas> fasilitas = new ArrayList<>();
                if (fasilitasHomestayList!=null){
                    List<FasilitasHomestayItem> fasilitasHomestayItems = fasilitasHomestayList.getFasilitasHomestay();
                    for (FasilitasHomestayItem item : fasilitasHomestayItems){
                        Fasilitas fasilitas1 = new Fasilitas(item.getNama());
                        fasilitas.add(fasilitas1);
                    }
                }
                fasilitasAdapter.setListFasilitas(fasilitas);
            }

            @Override
            public void onFailure(Call<FasilitasHomestayList> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });
//        fasilitasAdapter.setListFasilitas(generateData());

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);

        rvFasilitas = findViewById(R.id.rvFasilitas);
        rvFasilitas.setAdapter(fasilitasAdapter);
        rvFasilitas.setLayoutManager(layoutManager);


        iconBack = findViewById(R.id.backImg);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String website = getIntent().getStringExtra("website");
        urlWeb = findViewById(R.id.textUrl);
        urlWeb.setText(website);

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

        textNamaHome = findViewById(R.id.textNamaHome);
        String namaHome = getIntent().getStringExtra("nama");
        textNamaHome.setText(namaHome);

        textJenis = findViewById(R.id.textJenis);
        String jenis = getIntent().getStringExtra("jenis");
        textJenis.setText(jenis);

        textDRating = findViewById(R.id.textDRating);
        String rating = getIntent().getStringExtra("rating");
        textDRating.setText(rating);

        textAlamat = findViewById(R.id.textAlamat);
        String alamat = getIntent().getStringExtra("alamat");
        textAlamat.setText(alamat);

        String noHp = getIntent().getStringExtra("no_hp");
        textNohp = findViewById(R.id.textPhone);
        textNohp.setText(noHp);
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

    public ArrayList<Fasilitas> generateData(){
        ArrayList<Fasilitas> fasilitasArrayList = new ArrayList<>();
        fasilitasArrayList.add(new Fasilitas("Kolam Renang"));
        fasilitasArrayList.add(new Fasilitas("WIFI"));
        fasilitasArrayList.add(new Fasilitas("Sarapam"));
        return fasilitasArrayList;
    }

    public void toReview(View view){
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("idHomestay",idHomestay);
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