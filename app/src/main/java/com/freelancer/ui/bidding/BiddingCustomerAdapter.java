package com.freelancer.ui.bidding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.freelancer.R;

import java.util.ArrayList;

//This creates an adapter that allows the customer bidder info to be converted into a custom list view.
//Created by Edward Kuoch
public class BiddingCustomerAdapter extends ArrayAdapter<BiddingCustomerInfo> {
    private Context mContext;
    private int mResource;
    public BiddingCustomerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<BiddingCustomerInfo> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    //Connects the view list with the bid info object.
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        ImageView imageView = convertView.findViewById(R.id.bidderImageView);
        TextView userName = convertView.findViewById(R.id.bidderUserName);
        TextView bidPrice = convertView.findViewById(R.id.bidderPrice);
        Glide.with(convertView).load(getItem(position).getImage()).into(imageView);
        userName.setText(getItem(position).getUserName());
        bidPrice.setText(getItem(position).getBidPrice());
        return convertView;
    }
}
