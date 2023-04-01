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

import com.freelancer.R;

import java.util.ArrayList;

//This creates an adapter that allows the bidding info to be converted into a custom list view.
//Created by Edward Kuoch
public class ContractorBidAdapter extends ArrayAdapter<ContractorBidInfo> {
    private Context mContext;
    private int mResource;
    public ContractorBidAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ContractorBidInfo> objects) {
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
        ImageView imageView = convertView.findViewById(R.id.bidImageView);
        TextView actName = convertView.findViewById(R.id.bidActivityName);
        TextView startPrice = convertView.findViewById(R.id.bidStartPrice);
        imageView.setImageResource(getItem(position).getImage());
        actName.setText(getItem(position).getActivityName());
        startPrice.setText(getItem(position).getStartingPrice());
        return convertView;
    }
}
