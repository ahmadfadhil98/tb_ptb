package com.example.aplikasiptb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiptb.R;
import com.example.aplikasiptb.model.UnitHome;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.UnitViewHolder> {

    public class UnitViewHolder extends RecyclerView.ViewHolder {
        TextView namaUnit,hargaUnit,qtyUnit;
        Integer idunit;
        ImageView imageUnit;
        UnitHome unitHome;

        public UnitViewHolder(@NonNull View itemView) {
            super(itemView);
            namaUnit = itemView.findViewById(R.id.textNamaUnit);
            hargaUnit = itemView.findViewById(R.id.textHargaUnit);
            qtyUnit = itemView.findViewById(R.id.textQtyUnit);

        }
    }

    ArrayList<UnitHome> listUnit = new ArrayList<>();

    public void setListUnit(ArrayList<UnitHome> listUnit){
        this.listUnit = listUnit;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_unithomestay,parent,false);
        UnitAdapter.UnitViewHolder viewHolder = new UnitAdapter.UnitViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UnitViewHolder holder, int position) {
        UnitHome unitHome = listUnit.get(position);
        holder.namaUnit.setText(unitHome.nama);
        holder.hargaUnit.setText(unitHome.harga.toString());
        holder.idunit = unitHome.id;
//        Picasso.get().load(unitHome.foto).into(holder.imageUnit);
    }

    @Override
    public int getItemCount() {
        return listUnit.size();
    }


}
