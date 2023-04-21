package com.freelancer;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    TextView name, comment;
    RatingBar rate;
    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imageReview);
        name = itemView.findViewById(R.id.reviewName);
        rate = itemView.findViewById(R.id.reviewRating);
        comment = itemView.findViewById(R.id.reviewComment);
    }
}
