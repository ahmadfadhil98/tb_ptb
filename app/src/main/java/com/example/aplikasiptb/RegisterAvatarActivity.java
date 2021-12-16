package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.adapter.AvatarAdapter;
import com.example.aplikasiptb.model.Avatar;
import com.example.aplikasiptb.model.AvatarItem;
import com.example.aplikasiptb.model.AvatarList;
import com.example.aplikasiptb.model.ResponseRegister;
import com.example.aplikasiptb.retrofit.PortalClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAvatarActivity extends AppCompatActivity implements AvatarAdapter.OnAvatarViewHolderClick {

    RecyclerView rvAvatar;
    AvatarAdapter avatarAdapter;
    ImageView backImg;
    Button nextBtn;
    Integer userIdLaunch,userIdRegis,idUserLaunch,idUserProfil,idUser,idUserBack;
    TextView head;
    String baseUrl,foto,token;
    PortalClient portalClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_avatar);

        nextBtn = findViewById(R.id.btnNext);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNext(v);
            }
        });
        head = findViewById(R.id.textHead);

        userIdLaunch = getIntent().getIntExtra("userIdLaunch",0);
        userIdRegis = getIntent().getIntExtra("userIdRegis",0);
        idUserProfil = getIntent().getIntExtra("idUserProfil",0);
        idUserLaunch = getIntent().getIntExtra("idUserLaunch",0);
        idUserBack = getIntent().getIntExtra("idUserBack",0);

        if (idUserProfil!=0){
            head.setText("Ganti Avatar");
        }else{
            head.setText("Pendaftaran");
        }

        avatarAdapter = new AvatarAdapter();

        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        token = preferences.getString("TOKEN","");

        baseUrl = getString(R.string.apiUrlLumen);
        Authent authent = new Authent();
        portalClient = authent.setPortalClient(baseUrl);

        Call<AvatarList> call = portalClient.getAvatar(token);
        call.enqueue(new Callback<AvatarList>() {
            @Override
            public void onResponse(Call<AvatarList> call, Response<AvatarList> response) {
                AvatarList avatarList = response.body();
                ArrayList<Avatar> avatars = new ArrayList<>();
                if(avatarList!=null){
                    List<AvatarItem> avatarItems = avatarList.getAvatar();
                    for (AvatarItem item : avatarItems){
                        Avatar avatar = new Avatar(
                                item.getPath(),baseUrl
                        );
                        avatars.add(avatar);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Tidak ada response",Toast.LENGTH_SHORT).show();
                }
                avatarAdapter.setList(avatars);

            }

            @Override
            public void onFailure(Call<AvatarList> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);

        avatarAdapter.setClickObject(this);

        rvAvatar = findViewById(R.id.rvAvatar);
        rvAvatar.setAdapter(avatarAdapter);
        rvAvatar.setLayoutManager(gridLayoutManager);

        backImg = findViewById(R.id.arrowBack);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    @Override
    public void onClick(String position) {
        foto = position;
//        Toast.makeText(getApplicationContext(),position,Toast.LENGTH_SHORT).show();
    }

    public void toNext(View view){
        if (foto==null){
            Toast.makeText(getApplicationContext(),"Pilih Avatar",Toast.LENGTH_SHORT).show();
        }else{

            if(userIdLaunch!=0){
                idUser = userIdLaunch;
            }else if(userIdRegis!=0){
                idUser = userIdRegis;
            }else if (idUserLaunch!=0){
                idUser = idUserLaunch;
            }else if (idUserProfil!=0){
                idUser = idUserProfil;
            }else if (idUserBack!=0){
                idUser = idUserBack;
            }else{
                Toast.makeText(getApplicationContext(),"idPengguna tidak ada",Toast.LENGTH_SHORT).show();
            }

            if (idUser!=null){

                if(idUserProfil!=0||idUserLaunch!=0||idUserBack!=0){
                    Call<ResponseRegister> call = portalClient.updateAvatar(token,idUser,foto);
                    call.enqueue(new Callback<ResponseRegister>() {
                        @Override
                        public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                            ResponseRegister responseRegister = response.body();
                            if(responseRegister!=null){
                                String message = responseRegister.getMessage();
                                if(message!=null){
                                    if(idUserProfil!=0){
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), DetailProfilActivity.class);
                                        intent.putExtra("idUser",idUser);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Register1Activity.class);
                                        intent.putExtra("idUser",idUser);
                                        startActivity(intent);
                                        finish();
                                    }

                                }

                            }else{
                                Toast.makeText(getApplicationContext(),"Tidak ada response",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseRegister> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
//                    Toast.makeText(getApplicationContext(),"Disini",Toast.LENGTH_SHORT).show();
                    Call<ResponseRegister> call = portalClient.registerAvatar(token,idUser,foto);
                    call.enqueue(new Callback<ResponseRegister>() {
                        @Override
                        public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                            ResponseRegister responseRegister = response.body();
                            if(responseRegister!=null){
                                String message = responseRegister.getMessage();
                                if(message!=null){
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Register1Activity.class);
                                    intent.putExtra("idUser",idUser);
                                    startActivity(intent);
                                    finish();
                                }

                            }else{
                                Toast.makeText(getApplicationContext(),"Tidak ada response",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseRegister> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }

        }


    }

    @Override
    public void onBackPressed() {
        if (userIdLaunch!=0||idUserLaunch!=0){
            Intent intent = new Intent(this, WalkhthroughActivity.class);
            startActivity(intent);
            finish();
        }else if(userIdRegis!=0){
            Intent intent = new Intent(this, Register3Activity.class);
            startActivity(intent);
            finish();
        }else if(idUserProfil!=0){
            Intent intent = new Intent(this, UpdateProfilActivity.class);
            startActivity(intent);
            finish();
        }
    }



}