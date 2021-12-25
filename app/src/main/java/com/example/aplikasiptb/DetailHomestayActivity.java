package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.adapter.FasilitasAdapter;
import com.example.aplikasiptb.model.DHome;
import com.example.aplikasiptb.model.Fasilitas;
import com.example.aplikasiptb.model.FasilitasHomestayItem;
import com.example.aplikasiptb.model.FasilitasHomestayList;
import com.example.aplikasiptb.model.HomeItem;
import com.example.aplikasiptb.retrofit.PortalClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailHomestayActivity extends AppCompatActivity {

    TextView urlWeb,textNohp,textNamaHome,textJenis,textDRating,textAlamat;
    ImageView iconBack,imageHome,imgHomeFull;
    RecyclerView rvFasilitas;
    FasilitasAdapter fasilitasAdapter;
    Integer idHomestay;
    PortalClient portalClient;
    String token;
    String baseUrl;
    Double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_homestay);

        imageHome = findViewById(R.id.imageHome);
        urlWeb = findViewById(R.id.textUrl);
        textNamaHome = findViewById(R.id.textNamaHome);
        textJenis = findViewById(R.id.textJenis);
        textDRating = findViewById(R.id.textDRating);
        textAlamat = findViewById(R.id.textAlamat);
        textNohp = findViewById(R.id.textPhone);
        imgHomeFull = findViewById(R.id.imageHomeFull);

        imageHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHomeFull.setVisibility(View.VISIBLE);
            }
        });

        imgHomeFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHomeFull.setVisibility(View.GONE);
            }
        });

        idHomestay = getIntent().getIntExtra("idHomestay",0);

        fasilitasAdapter = new FasilitasAdapter();

        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        token = preferences.getString("TOKEN","");

        baseUrl = getString(R.string.apiUrlLumen);
        Authent authent = new Authent();
        portalClient = authent.setPortalClient(baseUrl);

        setFasilitas();
        setDHomestay();
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

    public void setFasilitas(){
        Call<FasilitasHomestayList> call = portalClient.getDFasilitas(token,idHomestay);
        updateViewProgress(1);
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
                updateViewProgress(3);
            }

            @Override
            public void onFailure(Call<FasilitasHomestayList> call, Throwable t) {
                updateViewProgress(2);
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setDHomestay(){
        Call<DHome> call = portalClient.getHome(token,idHomestay);
        updateViewProgress(1);
        call.enqueue(new Callback<DHome>() {
            @Override
            public void onResponse(Call<DHome> call, Response<DHome> response) {
                DHome dHome = response.body();
                if(dHome!=null){
                    List<HomeItem> homeItems = dHome.getHome();
                    for (HomeItem item : homeItems){
                        urlWeb.setText(item.getWebsite());
                        textAlamat.setText(item.getAlamat());
                        textNamaHome.setText(item.getNama());
                        textDRating.setText(String.format("%.2f",item.getRating()));
                        textJenis.setText(item.getJenis());
                        textNohp.setText(item.getNoHp());
                        Picasso.get().load(baseUrl+item.getFoto()).into(imageHome);
                        Picasso.get().load(baseUrl+item.getFoto()).into(imgHomeFull);
                        latitude = item.getLatitude();
                        longitude = item.getLongitude();
                    }
//                    List<HomestayItem> homestayItem = homestayList.getHomestay();

                }
                updateViewProgress(3);
            }

            @Override
            public void onFailure(Call<DHome> call, Throwable t) {
                updateViewProgress(2);
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void toReview(View view){
//        Toast.makeText(this,idHomestay.toString(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("idHomestay",idHomestay);
        startActivity(intent);
        finish();
    }

    public void toBooking(View view){
        Intent intent = new Intent(this, BookingActivity.class);
        intent.putExtra("idHomestay",idHomestay);
        startActivity(intent);
        finish();
    }

    public void toMapArah(View view){
        Intent intent = new Intent(this, ArahActivity.class);
        intent.putExtra("idHomestay",idHomestay);
        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longitude);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (imgHomeFull.getVisibility()==View.VISIBLE){
            imgHomeFull.setVisibility(View.GONE);
        }else{
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void updateViewProgress(Integer active){
        FrameLayout black = findViewById(R.id.blank);
        ProgressBar progressBar = findViewById(R.id.progressDHome);
        TextView textNoData = findViewById(R.id.textNoData);
        if (active==1){
            black.setVisibility(View.VISIBLE);
            textNoData.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else if(active==2){
            black.setVisibility(View.VISIBLE);
            textNoData.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }else{
            black.setVisibility(View.GONE);
            textNoData.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }
}