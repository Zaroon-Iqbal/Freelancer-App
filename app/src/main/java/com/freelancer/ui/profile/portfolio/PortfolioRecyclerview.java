package com.freelancer.ui.profile.portfolio;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.freelancer.R;

import java.util.ArrayList;

public class PortfolioRecyclerview extends RecyclerView.Adapter<PortfolioRecyclerview.PortfolioViewHolder>{
    ArrayList<String> gallery;

    RecyclerViewInterface click;

    public PortfolioRecyclerview(ArrayList<String> list, RecyclerViewInterface click){
        gallery = list;
        this.click = click;
    }

    @NonNull
    @Override
    public PortfolioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.portfolio_imageview,parent,false);
        return new PortfolioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PortfolioViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(gallery.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return gallery.size();
    }

    public class PortfolioViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public PortfolioViewHolder(@NonNull View v){
            super(v);
            imageView = (ImageView) v.findViewById(R.id.galleryImage);

            v.setOnClickListener(v1 -> {
                if(click != null){
                    int pos = getBindingAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION)
                        click.onItemClicked(pos);
                }
            });
        }
    }
}
