package com.example.aplikasiptb.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiptb.R;
import com.example.aplikasiptb.model.UnitHome;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.UnitViewHolder> {

    ArrayList<UnitHome> listUnit = new ArrayList<>();
    List<Integer> units = new ArrayList<>();
    List<Integer> hargas = new ArrayList<>();
    Integer status = 0;

    public UnitAdapter(Integer status) {
        this.status = status;
    }

    public class UnitViewHolder extends RecyclerView.ViewHolder {
        TextView namaUnit,hargaUnit,qtyUnit;
        Integer idunit;
        ImageView imageUnit;
        FrameLayout frameBg;
        UnitHome unitHome;

        public UnitViewHolder(@NonNull View itemView) {
            super(itemView);
            namaUnit = itemView.findViewById(R.id.textNamaUnit);
            hargaUnit = itemView.findViewById(R.id.textHargaUnit);
            qtyUnit = itemView.findViewById(R.id.textQtyUnit);
            frameBg = itemView.findViewById(R.id.frameUnit);

        }
    }

    public interface OnUnitViewHolderClick{
        void Onclick(List<Integer> unitList,List<Integer> hargaList);
    }

    OnUnitViewHolderClick clickObject;

    public void setClickObject(OnUnitViewHolderClick clickObject) {
        this.clickObject = clickObject;
    }

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
        if(unitHome.foto!=null){
            Picasso.get().load(unitHome.foto).into(holder.imageUnit);
        }
        if (status==2){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.frameBg.getVisibility()==View.GONE){
                        holder.frameBg.setVisibility(View.VISIBLE);
                        units.add(unitHome.id);
                        hargas.add(unitHome.harga);
                    }else{
                        holder.frameBg.setVisibility(View.GONE);
                        units = removeItem(units,unitHome.id);
                        hargas = removeItem(hargas,unitHome.harga);
                    }
                    Log.i("kategoris",units.toString());

                    clickObject.Onclick(units,hargas);

                }
            });
        }


    }

    public static List<Integer> removeItem(List<Integer> list,Integer value){
        Integer index = list.indexOf(value);
        List<Integer> result = new ArrayList<>();

        for (Integer item : list){
            if (value!=item){
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return listUnit.size();
    }


}
