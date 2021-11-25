package com.example.aplikasiptb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiptb.DetailHomestayActivity;
import com.example.aplikasiptb.MapActivity;
import com.example.aplikasiptb.R;
import com.example.aplikasiptb.model.Homestay;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomestayAdapter extends RecyclerView.Adapter<HomestayAdapter.HomestayViewHolder> {

//    private Context mContext;
//
//    public HomestayAdapter(Context mContext) {
//        this.mContext = mContext;
//    }

    public class HomestayViewHolder extends RecyclerView.ViewHolder {
        TextView textNamaHomestay,textJenis,textRating;
        ImageView imageHomestay;
        ConstraintLayout parentLayout;

        public HomestayViewHolder(@NonNull View itemView) {
            super(itemView);
            textNamaHomestay = itemView.findViewById(R.id.textNamaHomestay);
            textJenis = itemView.findViewById(R.id.textJenis);
            textRating = itemView.findViewById(R.id.textRating);
            imageHomestay = itemView.findViewById(R.id.imageHomestay);
//            parentLayout = itemView.findViewById(R.id.parent_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickObject.onClick();
                }
            });
        }
    }

    public interface OnHomestayViewHolderClick {
        void onClick();
    }

    OnHomestayViewHolderClick clickObject;

    public void setClickObject(OnHomestayViewHolderClick clickObject) {
        this.clickObject = clickObject;
    }

    ArrayList<Homestay> listHomestay = new ArrayList<>();

    public void setListHomestay(ArrayList<Homestay> listHomestay) {
        this.listHomestay = listHomestay;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomestayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_homestay,parent,false);
        HomestayAdapter.HomestayViewHolder viewHolder = new HomestayAdapter.HomestayViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomestayViewHolder viewHolder, int position) {
        Homestay homestay = listHomestay.get(position);
        viewHolder.textNamaHomestay.setText(homestay.nama);
        viewHolder.textJenis.setText(homestay.jenis);
        viewHolder.textRating.setText(homestay.rating.toString());
        Picasso.get().load(homestay.foto).into(viewHolder.imageHomestay);
//        viewHolder.textRating.setText(homestay.rating);
//        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, DetailHomestayActivity.class);
//                mContext.startActivity(intent);
//            }
//        });


    }


    @Override
    public int getItemCount() {
        return listHomestay.size();
    }


}
