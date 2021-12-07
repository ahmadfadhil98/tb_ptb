package com.example.aplikasiptb;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasiptb.adapter.HomestayAdapter;
import com.example.aplikasiptb.model.Homestay;
import com.example.aplikasiptb.model.HomestayItem;
import com.example.aplikasiptb.model.HomestayList;
import com.example.aplikasiptb.retrofit.PortalClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.aplikasiptb.databinding.ActivityMapBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback,HomestayAdapter.OnHomestayViewHolderClick {

    private GoogleMap mMap;
    private ActivityMapBinding binding;
    private long exitTime = 0;
    EditText search;
    RecyclerView rvHomestay;
    HomestayAdapter homestayAdapter;
    int idUser;
//    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latlngs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        idUser = getIntent().getIntExtra("idUser",0);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        search = (EditText) findViewById(R.id.search);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    findOnMap();
                }
                return false;
            }
        });

        homestayAdapter = new HomestayAdapter();

        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");

//        Toast.makeText(this,token,Toast.LENGTH_SHORT).show();

        Authent authent = new Authent();
        PortalClient portalClient = authent.setPortalClient(getString(R.string.apiUrlLumen));

        Call<HomestayList> call = portalClient.getHomestay(token);
        call.enqueue(new Callback<HomestayList>() {
            @Override
            public void onResponse(Call<HomestayList> call, Response<HomestayList> response) {
                HomestayList homestayList = response.body();
                ArrayList<Homestay> homestays = new ArrayList<>();
                if(homestayList != null){
                    List<HomestayItem> homestayItem = homestayList.getHomestay();
                    for (HomestayItem item: homestayItem){
                        Homestay homestay = new Homestay(
                                item.getId(),
                                item.getNama(),
                                item.getJenis(),
                                item.getRating(),
                                getString(R.string.apiUrlLumen)+item.getFoto()
                        );
                        homestays.add(homestay);
                        LatLng harau = new LatLng(item.getLatitude(), item.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(harau));
                    }
                }
                homestayAdapter.setListHomestay(homestays);
            }

            @Override
            public void onFailure(Call<HomestayList> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });

        homestayAdapter.setClickObject(this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);

        rvHomestay = findViewById(R.id.rvHomestay);
        rvHomestay.setAdapter(homestayAdapter);
        rvHomestay.setLayoutManager(layoutManager);
    }

//    public ArrayList<Homestay> generateData(){
//        ArrayList<Homestay> listHomestay = new ArrayList<>();
//        listHomestay.add(new Homestay("Homestay A",
//                "Penginapan",
//                1));
//        listHomestay.add(new Homestay("Homestay B",
//                "Penginapan",
//                2));
//        listHomestay.add(new Homestay("Homestay C",
//                "Penginapan",
//                3));
//        listHomestay.add(new Homestay("Homestay D",
//                "Penginapan",
//                4));
//        listHomestay.add(new Homestay("Homestay E",
//                "Penginapan",
//                5));
//        return listHomestay;
//    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng harau = new LatLng(-0.105881, 100.6603642);
        mMap.addMarker(new MarkerOptions().position(harau));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(harau,15));
    }

    public void goToLocation(double latitude,double longitude,int zoom){
        LatLng latLng = new LatLng(latitude, longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng,zoom);
        mMap.moveCamera(update);
    }

    public void toProfil(View view){
        Intent intent = new Intent(this, ProfilActivity.class);
        intent.putExtra("idUser",idUser);
        startActivity(intent);
        finish();
    }

    public void toHome(View view){
        Intent intent = new Intent(this, WalkhthroughActivity.class);
        startActivity(intent);
        finish();
    }

    public void findOnMap(){
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> mylist = geocoder.getFromLocationName(search.getText().toString(),1);
            Address address = mylist.get(0);
            String locality = address.getLocality();
            double lat = address.getLatitude();
            double lon = address.getLongitude();
            goToLocation(lat,lon,15);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }

    }

//    @Override
//    public void onClick() {
//        Intent intent = new Intent(this, DetailHomestayActivity.class);
//        startActivity(intent);
//    }

    @Override
    public void onClick(Homestay homestay) {
        Intent intent = new Intent(this, DetailHomestayActivity.class);
        intent.putExtra("idHomestay",homestay.id);
        startActivity(intent);
    }

//    @Override
//    public void onClick(String idhomestay) {
//        Toast.makeText(getApplicationContext(),idhomestay,Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, DetailHomestayActivity.class);
//        startActivity(intent);
//    }
}