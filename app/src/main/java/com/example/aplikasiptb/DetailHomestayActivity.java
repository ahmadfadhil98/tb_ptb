package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.aplikasiptb.adapter.UnitAdapter;
import com.example.aplikasiptb.model.DHome;
import com.example.aplikasiptb.model.DetailHomestay;
import com.example.aplikasiptb.model.Fasilitas;
import com.example.aplikasiptb.model.FasilitasHomeItem;
import com.example.aplikasiptb.model.FasilitasHomestayItem;
import com.example.aplikasiptb.model.FasilitasHomestayList;
import com.example.aplikasiptb.model.HomeItem;
import com.example.aplikasiptb.model.UnitHome;
import com.example.aplikasiptb.model.UnitHomeItem;
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
    RecyclerView rvFasilitas,rvUnit;
    FasilitasAdapter fasilitasAdapter;
    UnitAdapter unitAdapter;
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
        unitAdapter = new UnitAdapter(1);

        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        token = preferences.getString("TOKEN","");

        baseUrl = getString(R.string.apiUrlLumen);
        Authent authent = new Authent();
        portalClient = authent.setPortalClient(baseUrl);

        detailHomestay();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3,GridLayoutManager.HORIZONTAL,false);

        rvFasilitas = findViewById(R.id.rvFasilitas);
        rvUnit = findViewById(R.id.rvUnit);

        rvFasilitas.setAdapter(fasilitasAdapter);
        rvFasilitas.setLayoutManager(gridLayoutManager);

        rvUnit.setAdapter(unitAdapter);
        rvUnit.setLayoutManager(layoutManager);

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

    public void detailHomestay(){
        Call<DetailHomestay> call = portalClient.setDHome(token,idHomestay);
        updateViewProgress(1);
        call.enqueue(new Callback<DetailHomestay>() {
            @Override
            public void onResponse(Call<DetailHomestay> call, Response<DetailHomestay> response) {
                DetailHomestay detailHomestay = response.body();
                ArrayList<Fasilitas> fasilitasList = new ArrayList<>();
                ArrayList<UnitHome> unitHomes = new ArrayList<>();
                if(detailHomestay!=null){
                    urlWeb.setText(detailHomestay.getDetailHomestay().getWebsite());
                    urlWeb.setText(detailHomestay.getDetailHomestay().getWebsite());
                    textAlamat.setText(detailHomestay.getDetailHomestay().getAlamat());
                    textNamaHome.setText(detailHomestay.getDetailHomestay().getNama());
                    textDRating.setText(String.format("%.2f",detailHomestay.getDetailHomestay().getRating()));
                    textJenis.setText(detailHomestay.getDetailHomestay().getJenis());
                    textNohp.setText(detailHomestay.getDetailHomestay().getNoHp());
                    Picasso.get().load(baseUrl+detailHomestay.getDetailHomestay().getFoto()).into(imageHome);
                    Picasso.get().load(baseUrl+detailHomestay.getDetailHomestay().getFoto()).into(imgHomeFull);
                    latitude = detailHomestay.getDetailHomestay().getLatitude();
                    longitude = detailHomestay.getDetailHomestay().getLongitude();

                    List<FasilitasHomeItem> fasilitasHomestayItemList =  detailHomestay.getFasilitasHome();
                    for (FasilitasHomeItem item : fasilitasHomestayItemList){
                        Fasilitas fasilitas = new Fasilitas(item.getNama());
                        fasilitasList.add(fasilitas);
                    }

                    List<UnitHomeItem> unitHomeItemList = detailHomestay.getUnitHome();
                    for (UnitHomeItem item:unitHomeItemList){
                        UnitHome unitHome = new UnitHome(
                                item.getId(),
                                item.getNama(),
                                item.getHarga(),
                                item.getFoto());
                        unitHomes.add(unitHome);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Ada yang salah",Toast.LENGTH_SHORT).show();
                }
                unitAdapter.setListUnit(unitHomes);
                fasilitasAdapter.setListFasilitas(fasilitasList);
                updateViewProgress(3);
            }

            @Override
            public void onFailure(Call<DetailHomestay> call, Throwable t) {
                updateViewProgress(2);
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void toReview(View view){
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
        Intent intent = new Intent(this, NavigationActivity.class);
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