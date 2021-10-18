package com.example.aplikasiptb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiptb.R;
import com.example.aplikasiptb.model.Notifikasi;

import java.util.ArrayList;

public class NotifikasiAdapter extends RecyclerView.Adapter<NotifikasiAdapter.NotifikasiViewHolder> {

    public class NotifikasiViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textTgl, textMain;

        public NotifikasiViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textTgl = itemView.findViewById(R.id.textTanggal);
            textMain = itemView.findViewById(R.id.textMain);
        }
    }

    ArrayList<Notifikasi> listNotifikasi = new ArrayList<>();

    public void setListNotifikasi(ArrayList<Notifikasi> listNotifikasi){
        this.listNotifikasi = listNotifikasi;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotifikasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notifikasi,parent,false);
        NotifikasiAdapter.NotifikasiViewHolder viewHolder = new NotifikasiAdapter.NotifikasiViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotifikasiViewHolder viewHolder, int position) {
        Notifikasi notifikasi = listNotifikasi.get(position);
        viewHolder.textMain.setText(notifikasi.main);
        viewHolder.textTgl.setText(notifikasi.tgl);
        viewHolder.textTitle.setText(notifikasi.title);

    }

    @Override
    public int getItemCount() {
        return listNotifikasi.size();
    }


}
