package com.example.aplikasiptb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiptb.R;
import com.example.aplikasiptb.model.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView textNama,textTanggal,textKomen;
        RatingBar ratingReview;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            textNama = itemView.findViewById(R.id.textNama);
            textTanggal = itemView.findViewById(R.id.textTanggal);
            textKomen = itemView.findViewById(R.id.textKomen);
            ratingReview = itemView.findViewById(R.id.ratingReview);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }
    }

    ArrayList<Review> listReview = new ArrayList<>();

    public void setListReview(ArrayList<Review> listReview) {
        this.listReview = listReview;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.item_review,parent,false);

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review,parent,false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder viewHolder, int position) {
        Review review = listReview.get(position);
        viewHolder.textNama.setText(review.nama);
        viewHolder.textTanggal.setText(review.tanggal);
        viewHolder.textKomen.setText(review.komen);
        viewHolder.ratingReview.setRating(Float.parseFloat(review.star.toString()));
        Picasso.get().load(review.avatar).into(viewHolder.imgAvatar);

    }

    @Override
    public int getItemCount() {
        return listReview.size();
    }


}
