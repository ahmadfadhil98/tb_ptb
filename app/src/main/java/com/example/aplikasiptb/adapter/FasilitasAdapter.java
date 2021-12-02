package com.example.aplikasiptb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiptb.R;
import com.example.aplikasiptb.model.Fasilitas;

import java.util.ArrayList;

public class FasilitasAdapter extends RecyclerView.Adapter<FasilitasAdapter.FasilitasViewholder> {

    public class FasilitasViewholder extends RecyclerView.ViewHolder {
        TextView itemFasilitas;

        public FasilitasViewholder(@NonNull View itemView) {
            super(itemView);
            itemFasilitas = itemView.findViewById(R.id.textFasilitasItem);
        }
    }

    ArrayList<Fasilitas> listFasilitas = new ArrayList<>();

    public void setListFasilitas(ArrayList<Fasilitas> listHomestay) {
        this.listFasilitas = listHomestay;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FasilitasViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fasilitas,parent,false);
        FasilitasAdapter.FasilitasViewholder viewholder = new FasilitasAdapter.FasilitasViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull FasilitasViewholder holder, int position) {
        Fasilitas fasilitas = listFasilitas.get(position);
        holder.itemFasilitas.setText(fasilitas.nama);
    }

    @Override
    public int getItemCount() {
        return listFasilitas.size();
    }



//    extends RecyclerView.Adapter<HomestayAdapter.HomestayViewHolder>
}
