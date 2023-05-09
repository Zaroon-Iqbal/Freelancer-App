package com.freelancer.ui.booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelancer.R;
import com.freelancer.databinding.ActivityListingDateBinding;
import com.freelancer.ui.profile.portfolio.RecyclerViewInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PickListingDate extends BottomSheetDialogFragment implements RecyclerViewInterface{
    CalendarView calendarView;
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    CollectionReference collection;
    ArrayList<JobListing> list;
    Calendar calendar;
    Date date1;
    Date date2;

    ListingRecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_listing_date,container,false);

        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();

        //calendarView = binding.jobCalendar;
        recyclerView = v.findViewById(R.id.jobListing);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fillRecycler();
//        calendarView.setMinDate(Calendar.getInstance().getTimeInMillis());
//        date1 = new Date(calendarView.getDate());
//        calendar = Calendar.getInstance();
//        calendar.setTime(date1);
//        calendar.set(Calendar.HOUR_OF_DAY,23);
//        calendar.set(Calendar.MINUTE,59);
//        date2 = calendar.getTime();
//        fillRecycler(date1,date2);
//        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
//            //view does not update
//            if(calendar.get(Calendar.DAY_OF_MONTH)!= dayOfMonth){
//                calendar.set(year,month,dayOfMonth,0,0);
//            }
//            else {
//                calendar = Calendar.getInstance();
//                calendar.set(year, month, dayOfMonth);
//            }
//            date1 = calendar.getTime();
//            calendar.set(Calendar.HOUR_OF_DAY,23);
//            calendar.set(Calendar.MINUTE,59);
//            date2 = calendar.getTime();
//
//            fillRecycler(date1,date2);
//        });
        return v;
    }

    private void fillRecycler() {
        //list.clear();
        collection = firestore.collection("jobListings");
//        collection.whereGreaterThanOrEqualTo(FieldPath.of("jobInfo","Appt Time"),date1)
//                .whereLessThanOrEqualTo(FieldPath.of("jobInfo","Appt Time"),date2)
//                .orderBy(FieldPath.of("jobInfo","Appt Time")).get().addOnSuccessListener(queryDocumentSnapshots -> {
//                    if(!queryDocumentSnapshots.isEmpty()){
//                        for(DocumentSnapshot documents:queryDocumentSnapshots.getDocuments()){
//                            Map<String,Object> map = documents.getData();
//                            map.put("Job Listing ID", documents.getId());
//                            list.add(new JobListing(map,"Elite Barber","1011 fake st, LA, CA"));
//                        }
//                        adapter = new ListingRecyclerViewAdpater(list,this);
//                        recyclerView.setAdapter(adapter);
//                    }
//                    else {
//                        Log.e("ERROR ======", "Query was empty");
//                        adapter = new ListingRecyclerViewAdpater(list,this);
//                        recyclerView.setAdapter(adapter);
//                    }
//                });
        collection.whereEqualTo("userId","DHsQ6UhQMLPqthv5us9Y4ByIN4u1").get().addOnSuccessListener(queryDocumentSnapshots -> {
            if(!queryDocumentSnapshots.isEmpty()){
                for(DocumentSnapshot document: queryDocumentSnapshots.getDocuments()){
                    Map<String,Object> map = document.getData();
                    map.put("Job Listing ID", document.getId());
                    list.add(new JobListing(map,"Elite Barber","1011 fake st, LA, CA"));
                }
                adapter = new ListingRecyclerViewAdapter(list,this);
                recyclerView.setAdapter(adapter);
            }
            else{
                Log.e("ERROR ======", "Query was empty");
                adapter = new ListingRecyclerViewAdapter(list,this);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onItemClicked(int pos, ArrayList<?> otherList) {
//        BookListing listing = new BookListing(list.get(pos));
//        listing.show(getSupportFragmentManager(),"Appointment Booking");
        Intent intent = new Intent(getActivity(),BookListing.class);
        intent.putExtra("Job Listing", list.get(pos));
        startActivity(intent);
    }
}

class ListingRecyclerViewAdapter extends RecyclerView.Adapter<ListingRecyclerViewAdapter.ListingViewHolder>{

    ArrayList<JobListing> listings;
    RecyclerViewInterface click;

    public ListingRecyclerViewAdapter(ArrayList<JobListing> list,RecyclerViewInterface c){
        listings = list;
        click = c;
    }

    @NonNull
    @Override
    public ListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_item,parent,false);
        return new ListingRecyclerViewAdapter.ListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingViewHolder holder, int position) {
        String text = listings.get(position).content + " - $" + listings.get(position).price;
        holder.content.setText(text);
        //holder.time.setText(listings.get(position).timestamp);
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    public class ListingViewHolder extends RecyclerView.ViewHolder{
        //TextView time;
        TextView content;

        public ListingViewHolder(@NonNull View itemView) {
            super(itemView);
            //time = itemView.findViewById(R.id.item_number);
            content = itemView.findViewById(R.id.content);

            itemView.setOnClickListener(v -> {
                if(click != null){
                    int pos = getBindingAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION)
                        click.onItemClicked(pos,listings);
                }
            });
        }
    }
}

