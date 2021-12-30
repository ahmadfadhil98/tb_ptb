package com.example.aplikasiptb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasiptb.model.ResponseRegister;
import com.example.aplikasiptb.retrofit.PortalClient;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register1Activity extends AppCompatActivity {

    ImageView iconBack;
    EditText tglLahir,textNamaUser,textHp,textTmptLahir;
    TextView warNama,warHp,warTmptlahir,warTglLahir;
    Integer idUser;
    RadioGroup radioJk;
    RadioButton radioId;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        idUser = getIntent().getIntExtra("idUser",0);
        iconBack = findViewById(R.id.backImg);
        tglLahir = findViewById(R.id.textTglLahir);
        textNamaUser = findViewById(R.id.textNamaUser);
        textHp = findViewById(R.id.textHp);
        textTmptLahir = findViewById(R.id.textTmptLahir);

        warNama = findViewById(R.id.warningNama);
        warHp = findViewById(R.id.warningHp);
        warTmptlahir = findViewById(R.id.warningTmptLahir);
        warTglLahir = findViewById(R.id.warningtglLahir);

        radioJk = findViewById(R.id.radioJk);

        disableEditText(tglLahir);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        tglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    private void disableEditText(EditText editText) {
//        editText.setFocusable(false);
//        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }

    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tglLahir.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    public void toRegister2(View view){
        Intent intent = new Intent(this, Register2Activity.class);
        startActivity(intent);
        finish();
    }

    public void toLogin(View view){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
        finish();
    }

    public void toMap(View view) throws ParseException {
        String namaUser = textNamaUser.getText().toString();
        String noHp = textHp.getText().toString();
        String tmptLahir = textTmptLahir.getText().toString();
        Integer jk;
        Integer jenisKelamin;

        warNama.setVisibility(View.GONE);
        warHp.setVisibility(View.GONE);
        warTmptlahir.setVisibility(View.GONE);
        warTglLahir.setVisibility(View.GONE);

        if (namaUser.equals("")){
            warNama.setVisibility(View.VISIBLE);
            warNama.setText("Nama Masih Kosong");
        }else if(noHp.equals("")){
            warHp.setVisibility(View.VISIBLE);
            warHp.setText("Nomor Handphone Masih Kosong");
        }else if(tmptLahir.equals("")){
            warTmptlahir.setVisibility(View.VISIBLE);
            warTmptlahir.setText("Tempat Lahir Masih Kosong");
        }else if(tglLahir.equals("")){
            warTglLahir.setVisibility(View.VISIBLE);
            warTglLahir.setText("Tanggal Lahir Masih Kosong");
        }else{
            jk = radioJk.getCheckedRadioButtonId();
            radioId = findViewById(jk);

            if (radioId.getText().equals("Laki-laki")){
                jenisKelamin = 1;
            }else{
                jenisKelamin = 2;
            }

            String tglLahirString = tglLahir.getText().toString();

            SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
            String token = preferences.getString("TOKEN","");

            Authent authent = new Authent();
            PortalClient portalClient = authent.setPortalClient(getString(R.string.apiUrlLumen));

            Call<ResponseRegister> call = portalClient.registerUser(
                    token,
                    idUser,
                    namaUser,
                    jenisKelamin,
                    noHp,
                    tmptLahir,
                    tglLahirString
            );
            updateViewProgress(true);
            call.enqueue(new Callback<ResponseRegister>() {
                @Override
                public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                    ResponseRegister responseRegister = response.body();
                    if(responseRegister!=null){
                        String message =responseRegister.getMessage();
                        List<String> listHp = responseRegister.getNo_hp();
                        if (listHp!=null&& !listHp.isEmpty()){
                            warHp.setVisibility(View.VISIBLE);
                            warHp.setText("Nomor Handphone sudah terdaftar");
                        }else if(message!=null){
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                            intent.putExtra("srcActivity","Register1Activity");
                            intent.putExtra("idUser",idUser);
                            startActivity(intent);
                            finish();

                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Tidak ada response",Toast.LENGTH_SHORT).show();
                    }
                    updateViewProgress(false);
                }

                @Override
                public void onFailure(Call<ResponseRegister> call, Throwable t) {
                    updateViewProgress(false);
                    Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, RegisterAvatarActivity.class);
        intent.putExtra("idUserBack",idUser);
        startActivity(intent);
        finish();
    }

    public void updateViewProgress(Boolean active){
        Button button = findViewById(R.id.buttonDaftar1);
        ProgressBar progressBar = findViewById(R.id.progressRegister1);
        TextView kebijakan = findViewById(R.id.kebijakanText);
        if (active){
            textNamaUser.setEnabled(false);
            textHp.setEnabled(false);
            textTmptLahir.setEnabled(false);
            tglLahir.setEnabled(false);
            radioJk.setEnabled(false);
            button.setVisibility(View.GONE);
            kebijakan.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            textNamaUser.setEnabled(true);
            textHp.setEnabled(true);
            textTmptLahir.setEnabled(true);
            tglLahir.setEnabled(true);
            radioJk.setEnabled(true);
            button.setVisibility(View.VISIBLE);
            kebijakan.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}