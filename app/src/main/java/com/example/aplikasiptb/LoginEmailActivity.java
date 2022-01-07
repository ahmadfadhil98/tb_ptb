package com.example.aplikasiptb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

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
    String token,fcm_token;
    Integer idUser;
    TextView warnEmail,warnPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        editUsername = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);

        warnEmail = findViewById(R.id.warnEmail);
        warnPass = findViewById(R.id.warnPassword);

        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                warnEmail.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                warnPass.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        iconBack = findViewById(R.id.backImg);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Authent authent = new Authent();
        portalClient = authent.setPortalClient(getString(R.string.apiUrlLumen));

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    fcm_token = task.getResult();
                }
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

        if(username.equals("")) {
            warnEmail.setText("Email atau Username masih kosong");
            warnEmail.setVisibility(View.VISIBLE);
        }else{
            warnEmail.setVisibility(View.GONE);
        }

        if(password.equals("")){
            warnPass.setText("Password belum masih kosong");
            warnPass.setVisibility(View.VISIBLE);
        }else{
            warnPass.setVisibility(View.GONE);
        }

        if(!password.equals("")&&!username.equals("")){
            Call<Auth> call = portalClient.checkLogin(username,password,fcm_token);
            updateViewProgress(true);
            call.enqueue(new Callback<Auth>() {
                @Override
                public void onResponse(Call<Auth> call, Response<Auth> response) {
                    Auth authClass = response.body();
                    if (authClass != null ){

                        AuthData authData = authClass.getData();
                        token = authData.getToken();
                        idUser = authData.getId();

                        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("TOKEN",token);
                        editor.putInt("IDUSER",idUser);
                        editor.apply();

                        checkDetail();

                    }else{
                        warnEmail.setText("Kombinasi Username dan Password anda salah");
                        warnEmail.setVisibility(View.VISIBLE);
                        warnPass.setText("Kombinasi Username dan Password anda salah");
                        warnPass.setVisibility(View.VISIBLE);
                    }
                    updateViewProgress(false);
                }

                @Override
                public void onFailure(Call<Auth> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Gagal menghubungi server",Toast.LENGTH_SHORT).show();
                    updateViewProgress(false);
                }
            });
        }
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
                                intent.putExtra("srcActivity","LoginEmailActivity");
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