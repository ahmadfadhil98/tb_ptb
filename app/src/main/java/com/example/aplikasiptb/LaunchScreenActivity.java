package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.model.DUser;
import com.example.aplikasiptb.model.DetailUserItem;
import com.example.aplikasiptb.model.Homestay;
import com.example.aplikasiptb.model.HomestayItem;
import com.example.aplikasiptb.model.HomestayList;
import com.example.aplikasiptb.retrofit.PortalClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.chip.Chip;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
    }

    public void toWalkhthrough(View view){
        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        String token = preferences.getString("TOKEN","");

        Authent authent = new Authent();
        PortalClient portalClient = authent.setPortalClient(getString(R.string.apiUrlLumen));

        Call<DUser> call = portalClient.getDUser(token,token);
        updateViewProgress(true);
        call.enqueue(new Callback<DUser>() {
            @Override
            public void onResponse(Call<DUser> call, Response<DUser> response) {
                DUser dUser = response.body();
                if (dUser!=null){
//                    Toast.makeText(getApplicationContext(),"duser ngak kosong",Toast.LENGTH_SHORT).show();
                    List<DetailUserItem> detailUserItems = dUser.getDetailUser();
                    int idUser = dUser.getIdUser();

                    if(detailUserItems.isEmpty()){
//                        Toast.makeText(getApplicationContext(),"duser kosong",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(),Register2Activity.class);
                        intent.putExtra("idUser",idUser);
                        finish();
                        startActivity(intent);
                    }else{
                        for (DetailUserItem item : detailUserItems){
                            if(item.getFoto()==null){
                                Intent intent = new Intent(getApplicationContext(),Register2Activity.class);
                                intent.putExtra("idUser",idUser);
                                finish();
                                startActivity(intent);
                            }else if(item.getNama()==null){
                                Intent intent = new Intent(getApplicationContext(),Register1Activity.class);
                                intent.putExtra("idUser",idUser);
                                finish();
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                                intent.putExtra("idUser",idUser);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                finish();
                                startActivity(intent);
                            }
                        }
                    }
                }else{
                    Intent intent = new Intent(getApplicationContext(),WalkhthroughActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
                updateViewProgress(false);
            }

            @Override
            public void onFailure(Call<DUser> call, Throwable t) {
                updateViewProgress(false);
                Intent intent = new Intent(getApplicationContext(),WalkhthroughActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        });

    }

    public void updateViewProgress(Boolean active){
        Chip chipStart = findViewById(R.id.chipStart);
        ProgressBar progressBar = findViewById(R.id.progressLaunch);
        if (active){
            chipStart.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            chipStart.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

}