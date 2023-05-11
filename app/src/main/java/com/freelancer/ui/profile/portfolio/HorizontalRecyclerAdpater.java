package com.freelancer.ui.profile.portfolio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.freelancer.R;
import com.freelancer.ui.profile.ReviewInfo;

import java.util.ArrayList;

public class HorizontalRecyclerAdpater extends RecyclerView.Adapter<HorizontalRecyclerAdpater.HorizontalViewHolder>{
    ArrayList<?> gallery;

    RecyclerViewInterface click;

    int resource;

    public HorizontalRecyclerAdpater(ArrayList<?> list, int resource,RecyclerViewInterface click){
        gallery = list;
        this.click = click;
        this.resource = resource;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(resource,parent,false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        if (resource == R.layout.horizontal_images)
        {
            Glide.with(holder.itemView.getContext()).load(gallery.get(position)).into(holder.imageView);
        }
        else{
            holder.rating.setText(String.valueOf(((ReviewInfo)gallery.get(position)).rating));
            if (!((ReviewInfo)gallery.get(position)).comment.isEmpty())
            {
                holder.comment.setText(((ReviewInfo) gallery.get(position)).comment);
            }
        }
    }

    @Override
    public int getItemCount() {
        return gallery.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView rating;
        TextView comment;

        public HorizontalViewHolder(@NonNull View v){
            super(v);
            if (resource == R.layout.horizontal_images)
            {
                imageView = (ImageView) v.findViewById(R.id.portfolioPreview);

                v.setOnClickListener(v1 -> {
                    if (click != null) {
                        int pos = getBindingAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION)
                            click.onItemClicked(pos, gallery);
                    }
                });
            }
            else{
                rating = v.findViewById(R.id.rating);
                comment = v.findViewById(R.id.comment);
            }
        }
    }
}
