package com.freelancer.ui.profile.portfolio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.freelancer.R;

import java.util.ArrayList;

public class HorizontalRecyclerAdpater extends RecyclerView.Adapter<HorizontalRecyclerAdpater.HorizontalViewHolder>{
    ArrayList<String> gallery;

    RecyclerViewInterface click;

    public HorizontalRecyclerAdpater(ArrayList<String> list, RecyclerViewInterface click){
        gallery = list;
        this.click = click;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.horizontal_images,parent,false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(gallery.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return gallery.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public HorizontalViewHolder(@NonNull View v){
            super(v);
            imageView = (ImageView) v.findViewById(R.id.portfolioPreview);

            v.setOnClickListener(v1 -> {
                if(click != null){
                    int pos = getBindingAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION)
                        click.onItemClicked(pos,gallery);
                }
            });
        }
    }
}
