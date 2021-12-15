package com.example.aplikasiptb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aplikasiptb.model.DUser;
import com.example.aplikasiptb.model.DetailUserItem;
import com.example.aplikasiptb.model.UnitItem;
import com.example.aplikasiptb.model.UnitList;
import com.example.aplikasiptb.retrofit.PortalClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {

    ImageView iconBack;
    Spinner unit_spin,pembayaran_spin;
    List<String> unitListSting;
    List<Integer> idList,hargaList;
    ArrayAdapter<String> spinAdapter;
    String token,baseUrl,tglCheckIn,tglCheckOut,timeCheckIn,timeCheckOut,inCheck,OutCheck;
    PortalClient portalClient;
    Integer idHomestay,harga;
    EditText nama,checkIn,checkOut,tarif,uangDp;
    SimpleDateFormat simpleDateFormat;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        iconBack = findViewById(R.id.backImg);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nama =  findViewById(R.id.nama);
        checkIn =  findViewById(R.id.checkIn);
        checkOut = findViewById(R.id.checkOut);
        tarif = findViewById(R.id.tarif);
        uangDp = findViewById(R.id.uang_muka);

        idHomestay = getIntent().getIntExtra("idHomestay",0);
        SharedPreferences preferences = getSharedPreferences("com.example.aplikasiptb",MODE_PRIVATE);
        token = preferences.getString("TOKEN","");

        baseUrl = getString(R.string.apiUrlLumen);

        Authent authent = new Authent();
        portalClient = authent.setPortalClient(baseUrl);

        unit_spin = findViewById(R.id.unit);
        pembayaran_spin = findViewById(R.id.pembayaran);

        String[] units = new String[]{
                "Select an item..."
        };

        idList = new ArrayList<>();
        hargaList = new ArrayList<>();
        idList.add(0);
        hargaList.add(0);
        unitListSting = new ArrayList<>(Arrays.asList(units));

        Call<UnitList> call =  portalClient.getUnit(token,idHomestay);
        updateViewProgress(true);
        call.enqueue(new Callback<UnitList>() {
            @Override
            public void onResponse(Call<UnitList> call, Response<UnitList> response) {
                UnitList unitList = response.body();
                if(unitList != null){
                    List<UnitItem> unitItems = unitList.getUnit();
                    for(UnitItem item : unitItems){
                        unitListSting.add(item.getNama());
                        idList.add(item.getId());
                        hargaList.add(item.getHarga());
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Tidak ada response",Toast.LENGTH_SHORT).show();
                }
                updateViewProgress(false);
            }

            @Override
            public void onFailure(Call<UnitList> call, Throwable t) {
                updateViewProgress(false);
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });

        setSpinner(unitListSting);

        spinAdapter.setDropDownViewResource(R.layout.spinner_item_unit);

        unit_spin.setAdapter(spinAdapter);
        unit_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int id_unit = idList.get(position);
                harga = hargaList.get(position);
                Toast.makeText(getApplicationContext(),Integer.toString(harga),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pembayaran_spin.setAdapter(spinAdapter);

        getNamaUser();

        disableEditText(checkIn);
        disableEditText(checkOut);
        disableEditText(tarif);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(harga!=null){
                    showDateDialog(1);
                }

            }
        });
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(harga!=null){
                    showDateDialog(2);
                }
            }
        });



    }

    public void getDifference(){
        inCheck = checkIn.getText().toString();
        OutCheck = checkOut.getText().toString();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm");

        try {
            Date dateCheckIn = simpleDateFormat.parse(inCheck);
            Date dateCheckOut = simpleDateFormat.parse(OutCheck);

            long time_difference = dateCheckOut.getTime() - dateCheckIn.getTime();
            long hours_difference = (time_difference / (1000*60*60));
            long tarifLong =  (hours_difference/24) * harga;
            long uang_muka = tarifLong/2;
            tarif.setText("Rp. "+Long.toString(tarifLong)+",-");
            uangDp.setText("Rp. "+Long.toString(uang_muka)+",-");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void getNamaUser(){
        Call<DUser> call = portalClient.getDUser(token,token);
        updateViewProgress(true);
        call.enqueue(new Callback<DUser>() {
            @Override
            public void onResponse(Call<DUser> call, Response<DUser> response) {
                DUser dUser = response.body();
                if(dUser!=null){
                    List<DetailUserItem> dUsers = dUser.getDetailUser();
                    for (DetailUserItem item : dUsers){
                        nama.setText(item.getNama());
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Tidak ada response",Toast.LENGTH_SHORT).show();
                }
                updateViewProgress(false);
            }

            @Override
            public void onFailure(Call<DUser> call, Throwable t) {
                updateViewProgress(false);
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDateDialog(int checkInOut){

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                if(checkInOut==1){
                    tglCheckIn = dateFormatter.format(newDate.getTime());
                }else{
                    tglCheckOut = dateFormatter.format(newDate.getTime());
                }
                showTimeDialog(checkInOut);

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

    public void showTimeDialog(int checkInOut){
        Calendar newCalendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (checkInOut==1){
                    timeCheckIn = hourOfDay + ":" +minute;
                    checkIn.setText(tglCheckIn+", "+timeCheckIn);
                }else{
                    timeCheckOut = hourOfDay + ":" +minute;
                    checkOut.setText(tglCheckOut+", "+timeCheckOut);
                    getDifference();
                }


            }
        },newCalendar.get(Calendar.HOUR_OF_DAY),newCalendar.get(Calendar.MINUTE),true);

        timePickerDialog.show();
    }

    private void disableEditText(EditText editText) {
//        editText.setFocusable(false);
//        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
    }

    public void setSpinner(List<String> list){
        spinAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item_unit,list){

            @Override
            public boolean isEnabled(int position) {
                if(position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
    }

    public void toInfoPembayaran(View view){
        Intent intent = new Intent(this, InfoPembayaranActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DetailHomestayActivity.class);
        intent.putExtra("idHomestay",idHomestay);
        startActivity(intent);
        finish();
    }

    public void updateViewProgress(Boolean active){
        FrameLayout progress = findViewById(R.id.layoutProgressBooking);
        if (active){
            progress.setVisibility(View.VISIBLE);
        }else{
            progress.setVisibility(View.GONE);
        }
    }
}