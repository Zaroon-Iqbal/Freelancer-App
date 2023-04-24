package com.freelancer.ui.booking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelancer.R;
import com.freelancer.databinding.ActivityListingDateBinding;
import com.freelancer.ui.profile.portfolio.RecyclerViewInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PickListingDate extends AppCompatActivity implements RecyclerViewInterface{
    CalendarView calendarView;
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    CollectionReference collection;
    ArrayList<JobListing> list;
    Calendar calendar;
    Date date1;
    Date date2;

    ListingRecyclerViewAdpater adapter;
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        ActivityListingDateBinding binding = ActivityListingDateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();

        calendarView = binding.jobCalendar;
        recyclerView = binding.jobListing;
        recyclerView.setLayoutManager(new LinearLayoutManager(PickListingDate.this));
        calendarView.setMinDate(Calendar.getInstance().getTimeInMillis());
        date1 = new Date(calendarView.getDate());
        calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        date2 = calendar.getTime();
        fillRecycler(date1,date2);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            //view does not update
            LocalTime localTime = LocalTime.now();
            calendar = Calendar.getInstance();
            calendar.set(year,month,dayOfMonth,localTime.getHour(),localTime.getMinute());
            date1 = calendar.getTime();
            Log.i("DATE 1-----",date1.toString());
            calendar.set(Calendar.HOUR_OF_DAY,23);
            calendar.set(Calendar.MINUTE,59);
            date2 = calendar.getTime();
            Log.i("DATE 2------",date2.toString());
            fillRecycler(date1,date2);
        });
        testMaps();
    }

    private void testMaps() {
        DocumentReference documentReference = firestore.collection("jobListings").document("vu2GfJHqQVRkGLBu3TLW");
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()){
                HashMap map = (HashMap) documentSnapshot.get("customOptions");

                Map<String,Object> check = documentSnapshot.getData();

            }
        });
    }

    private void fillRecycler(Date date1, Date date2) {
        list.clear();
        collection = firestore.collection("jobListings");
        collection.whereGreaterThanOrEqualTo(FieldPath.of("jobInfo","Appt Time"),date1)
                .whereLessThanOrEqualTo(FieldPath.of("jobInfo","Appt Time"),date2)
                .orderBy(FieldPath.of("jobInfo","Appt Time")).get().addOnSuccessListener(queryDocumentSnapshots -> {
                    if(!queryDocumentSnapshots.isEmpty()){
                        for(DocumentSnapshot documents:queryDocumentSnapshots.getDocuments()){
                            Map<String,Object> map = documents.getData();
                            list.add(new JobListing(map,"Elite Barber","1011 fake st, LA, CA"));
                        }
                        adapter = new ListingRecyclerViewAdpater(list,this);
                        recyclerView.setAdapter(adapter);
                    }
                    else {
                        Log.e("ERROR ======", "Query was empty");
                        adapter = new ListingRecyclerViewAdpater(list,this);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void onItemClicked(int pos) {
        BookListing listing = new BookListing(list.get(pos));
        listing.show(getSupportFragmentManager(),"Appointment Booking");
    }
}

class ListingRecyclerViewAdpater extends RecyclerView.Adapter<ListingRecyclerViewAdpater.ListingViewHolder>{

    ArrayList<JobListing> listings;
    RecyclerViewInterface click;

    public ListingRecyclerViewAdpater(ArrayList<JobListing> list,RecyclerViewInterface c){
        listings = list;
        click = c;
    }

    @NonNull
    @Override
    public ListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_item,parent,false);
        return new ListingRecyclerViewAdpater.ListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingViewHolder holder, int position) {
        String text = listings.get(position).content + " - $" + listings.get(position).price;
        holder.content.setText(text);
        holder.time.setText(listings.get(position).timestamp);
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    public class ListingViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView content;

        public ListingViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.item_number);
            content = itemView.findViewById(R.id.content);

            itemView.setOnClickListener(v -> {
                if(click != null){
                    int pos = getBindingAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION)
                        click.onItemClicked(pos);
                }
            });
        }
    }
}

