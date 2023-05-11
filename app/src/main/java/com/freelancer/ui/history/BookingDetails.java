package com.freelancer.ui.history;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.freelancer.ui.booking.JobListing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingDetails implements Parcelable {
    Map<String,Object> map;
    Map<String,Object> options;

    public BookingDetails(Map<String,Object>map){
        this.map = map;
        if(map.containsKey("customOptions"))
            options = (Map<String, Object>) map.get("customOptions");
        else
            options = new HashMap<>();
    }

    public BookingDetails(Parcel in){
        Bundle bundle = in.readBundle();
        map = new HashMap<>();

        for(String string: bundle.keySet()){
            String check = bundle.getString(string);
            if(check != null)
                map.put(string,check);
            else
                map.put(string,bundle.getBoolean(string));
        }

        boolean exists = in.readBoolean();
        if(exists){
            options = new HashMap<>();
            bundle = in.readBundle();

            for(String string: bundle.keySet()){
                String check = bundle.getString(string);
                if(check != null)
                    options.put(string,check);
                else
                    options.put(string,bundle.getStringArrayList(string));
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        Bundle b1 = new Bundle();
        for(Map.Entry<String,Object> entry: map.entrySet()){
            if(entry.getValue() instanceof String)
                b1.putString(entry.getKey(),(String) entry.getValue());
            if (entry.getValue() instanceof Boolean)
                b1.putBoolean(entry.getKey(),(Boolean)entry.getValue());
        }
        dest.writeBundle(b1);

        boolean exists = map.containsKey("customOptions");
        dest.writeBoolean(exists);
        if(exists){
            b1 = new Bundle();
            for(Map.Entry<String,Object> entry: options.entrySet()){
                if(entry.getValue() instanceof String)
                    b1.putString(entry.getKey(),(String) entry.getValue());
                else
                    b1.putStringArrayList(entry.getKey(),(ArrayList<String>) entry.getValue());
            }
            dest.writeBundle(b1);
        }
    }

    public static final Parcelable.Creator<BookingDetails> CREATOR = new Creator<BookingDetails>() {
        @Override
        public BookingDetails createFromParcel(Parcel source) {
            return new BookingDetails(source);
        }

        @Override
        public BookingDetails[] newArray(int size) {
            return new BookingDetails[0];
        }
    };
}
