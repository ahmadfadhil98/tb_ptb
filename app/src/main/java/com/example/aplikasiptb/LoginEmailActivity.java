package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.model.Auth;
import com.example.aplikasiptb.model.AuthData;
import com.example.aplikasiptb.model.DUser;
import com.example.aplikasiptb.model.DetailUserItem;
import com.example.aplikasiptb.retrofit.PortalClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginEmailActivity extends AppCompatActivity {

    ImageView iconBack;
    EditText editUsername;
    EditText editPassword;
    PortalClient portalClient;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        editUsername = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        iconBack = findViewById(R.id.backImg);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void toMap(View view){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginPhoneActivity.class);
        startActivity(intent);
        finish();
    }

    public void checkLogin(View view){

        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();

        Authent authent = new Authent();
        portalClient = authent.setPortalClient(getString(R.string.apiUrlLumen));

        Call<Auth> call = portalClient.checkLogin(username,password);
        updateViewProgress(true);
        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                Auth authClass = response.body();
                if (authClass != null ){

                    AuthData authData = authClass.getData();
                    token = authData.getToken();
//                    Toast.makeText(getApplicationContext(),token,Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("TOKEN",token);
                    editor.apply();

                    checkDetail();

//                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
//                    startActivity(intent);
//                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Kombinasi Username dan Password anda salah",Toast.LENGTH_SHORT).show();
                }
                updateViewProgress(false);
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal menghubungi server",Toast.LENGTH_SHORT).show();
                updateViewProgress(false);
            }
        });

//        if (username.equals("admin")&& password.equals("admin")){
//            Intent intent = new Intent(this, MapActivity.class);
//            startActivity(intent);
//            finish();
//        }else {
//            Toast.makeText(this,"Password/Username Anda salah",Toast.LENGTH_SHORT).show();
//        }
    }

    public void checkDetail(){
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

                        Intent intent = new Intent(getApplicationContext(),RegisterAvatarActivity.class);
                        intent.putExtra("userIdLaunch",idUser);
                        finish();
                        startActivity(intent);
                    }else{
                        for (DetailUserItem item : detailUserItems){
                            if(item.getFoto()==null){
                                Intent intent = new Intent(getApplicationContext(),RegisterAvatarActivity.class);
                                intent.putExtra("idUserLaunch",idUser);
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
        Button buttonLogin = findViewById(R.id.buttonLogin);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView kebijakan = findViewById(R.id.kebijakan);
        if (active){
            editUsername.setEnabled(false);
            editPassword.setEnabled(false);
            buttonLogin.setVisibility(View.GONE);
            kebijakan.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            editUsername.setEnabled(true);
            editPassword.setEnabled(true);
            buttonLogin.setVisibility(View.VISIBLE);
            kebijakan.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}