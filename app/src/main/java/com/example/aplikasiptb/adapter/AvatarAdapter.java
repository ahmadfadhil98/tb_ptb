package com.example.aplikasiptb.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiptb.R;
import com.example.aplikasiptb.model.Avatar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>{

    ArrayList<Avatar> list = new ArrayList<>();

    public class AvatarViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        FrameLayout frameSelect;
        int log;
        String path;
        Boolean selected;

        public AvatarViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.avatarIcon);
            frameSelect = itemView.findViewById(R.id.frameSelect);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selected = true;

                    clickObject.onClick(path);

                    if (frameSelect.getVisibility()==View.VISIBLE){
                        frameSelect.setVisibility(View.GONE);
                    }else {
                        frameSelect.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    public interface OnAvatarViewHolderClick {
        void onClick(String path);
    }

    OnAvatarViewHolderClick clickObject;

    public void setClickObject(OnAvatarViewHolderClick clickObject) {
        this.clickObject = clickObject;
    }



    public void setList(ArrayList<Avatar> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AvatarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_avatar,parent,false);
        AvatarAdapter.AvatarViewHolder viewHolder = new AvatarAdapter.AvatarViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Avatar avatar = list.get(position);
        Picasso.get().load(avatar.baseUrl+avatar.path).into(holder.imgAvatar);
        holder.path = avatar.path;
//        holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.frameSelect.getVisibility()==View.VISIBLE){
//                    holder.frameSelect.setVisibility(View.GONE);
////                    holder.getAdapterPosition();
//                }else {
//                    holder.frameSelect.setVisibility(View.VISIBLE);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
