package com.freelancer.ui.booking;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Map;

public class JobListing implements Serializable {
    String content;
    String timestamp;
    String businessName;
    String address;
    String price;
    String type;
    Map<String,Object> map;

    public JobListing(Map<String,Object> map, String name, String address){
        this.map = map;
        this.content = (String) ((Map)map.get("jobInfo")).get("description");
        this.businessName = name;
        this.address = address;
        this.price = (String) ((Map)map.get("jobInfo")).get("basePrice");
        if(((Map)map.get("jobInfo")).containsKey("Appt Time"))
        {
            Date date = ((Timestamp)((Map)map.get("jobInfo")).get("Appt Time")).toDate();
            Calendar calendar1 = new GregorianCalendar();
            calendar1.setTime(date);
            int ind = calendar1.get(Calendar.AM_PM);
            String ampm = (ind == 0) ? "AM":"PM";
            Formatter formatter = new Formatter();
            formatter = formatter.format("%tl:%tM",calendar1,calendar1);
            String time = formatter.toString()+ampm;
            timestamp = time;
        }
    }
}
