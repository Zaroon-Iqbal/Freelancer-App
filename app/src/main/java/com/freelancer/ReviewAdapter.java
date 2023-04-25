package com.freelancer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

/**
 * this file is used to bind the data to the view in the recycler view
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    Context context;
    List<Review> reviews;

    public ReviewAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    //used to return the view holder that was created
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_view, parent, false);//inflate the review layout
        return new ReviewViewHolder(view);

    }

    @Override
    //binding the variable data
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.name.setText(reviews.get(position).getName());//bind the properties together
        holder.rate.setRating(reviews.get(position).getRating());
        holder.comment.setText(reviews.get(position).getComment());
        holder.image.setImageResource(reviews.get(position).getImage());

    }

    @Override
    //returning the count
    public int getItemCount() {
        return reviews.size();
    }
}
