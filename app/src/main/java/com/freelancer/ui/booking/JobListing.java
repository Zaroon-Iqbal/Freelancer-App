package com.freelancer.ui.booking;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class JobListing implements Parcelable {
    Map<String,Object> customOptions = new HashMap<>();
    boolean exists = false;
    Map<String,Object> jobInfo;

    public JobListing(Map<String,Object> map){
        jobInfo = (Map<String, Object>)map.get("jobInfo");
        jobInfo.put("Job Listing ID",map.get("Job Listing ID"));
        jobInfo.put("businessId",map.get("userId"));
        if(map.containsKey("customOptions")) {
            customOptions = (Map<String, Object>) map.get("customOptions");
            exists = true;
        }
    }

    public JobListing(Parcel in){
        jobInfo = new HashMap<>();
        Bundle b1 = in.readBundle();
        for(String string: b1.keySet()){
            String temp = b1.getString(string);
            if(temp != null)
                jobInfo.put(string, temp);
            else
                jobInfo.put(string, b1.getDouble(string));
        }
        exists = in.readBoolean();
        if(exists)
        {
            int size = in.readInt();

            for (int i = 0; i < size; i++){
                String key = in.readString();
                Map<String,Object> temp = new HashMap<>();
                Bundle b2 = in.readBundle();
                for(String string: b2.keySet()){
                    if(string.equalsIgnoreCase("options"))
                        temp.put(string, b2.getStringArrayList(string));
                    else
                        temp.put(string,b2.getString(string));
                }
                customOptions.put(key,temp);
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
        for(Map.Entry<String,Object> entry: jobInfo.entrySet()){

            b1.putString(entry.getKey(), (entry.getValue()).toString());
        }
        dest.writeBundle(b1);
        dest.writeBoolean(exists);
        if(exists){
            dest.writeInt(customOptions.size());
            for(String string: customOptions.keySet()) {
                dest.writeString(string);
                Map<String,Object> map1 = (Map<String, Object>) customOptions.get(string);
                Bundle b2 = new Bundle();
                for(Map.Entry<String,Object> entry: map1.entrySet()){
                    if(entry.getValue() instanceof String)
                        b2.putString(entry.getKey(),(String) entry.getValue());
                    else if(entry.getValue() instanceof ArrayList) {
                        b2.putStringArrayList(entry.getKey(), (ArrayList<String>) entry.getValue());
                    }
                }
                dest.writeBundle(b2);
            }
        }
    }

    public static final Parcelable.Creator<JobListing> CREATOR = new Creator<JobListing>() {
        @Override
        public JobListing createFromParcel(Parcel source) {
            return new JobListing(source);
        }

        @Override
        public JobListing[] newArray(int size) {
            return new JobListing[0];
        }
    };
}
