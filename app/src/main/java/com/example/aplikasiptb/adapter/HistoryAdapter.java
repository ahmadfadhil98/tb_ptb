package com.example.aplikasiptb.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiptb.R;
import com.example.aplikasiptb.model.HistoryBooking;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView nama,tgl,status;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namaHome);
            tgl = itemView.findViewById(R.id.tglTrans);
            status = itemView.findViewById(R.id.statusBook);
        }
    }

    ArrayList<HistoryBooking> listHistory = new ArrayList<>();

    public void setListHistory(ArrayList<HistoryBooking> listHistory) {
        this.listHistory = listHistory;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history,parent,false);
        HistoryAdapter.HistoryViewHolder viewHolder = new HistoryAdapter.HistoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        HistoryBooking historyBooking = listHistory.get(position);
        holder.nama.setText(historyBooking.nama);
        holder.tgl.setText(historyBooking.tgl);

        if(historyBooking.status==1){
            holder.status.setText("Proses Pembayaran");
            holder.status.setTextColor(Color.BLUE);
        }else if(historyBooking.status==2){
            holder.status.setText("Disetujui");
            holder.status.setTextColor(Color.GREEN);
        }else {
            holder.status.setText("Dibatalkan");
            holder.status.setTextColor(Color.RED);
        }

    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }


}
