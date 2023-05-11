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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    CollectionReference collection;
    ArrayList<JobListing> list;
    ListingRecyclerViewAdapter adapter;
    String uid;
    String name;
    String address;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_listing_date,container,false);

        firestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        uid = getArguments().getString("uid");
        recyclerView = v.findViewById(R.id.jobListing);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fillRecycler();

        return v;
    }

    private void fillRecycler() {
        collection = firestore.collection("jobListings");

        collection.whereEqualTo("userId",uid).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if(!queryDocumentSnapshots.isEmpty()){
                for(DocumentSnapshot document: queryDocumentSnapshots.getDocuments()){
                    Map<String,Object> map = document.getData();
                    map.put("Job Listing ID", document.getId());
                    list.add(new JobListing(map));
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
        holder.content.setText((String)listings.get(position).jobInfo.get("description"));
        holder.price.setText("Base Price - $"+(listings.get(position).jobInfo.get("basePrice")).toString());
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    public class ListingViewHolder extends RecyclerView.ViewHolder{
        TextView content;
        TextView price;

        public ListingViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            price = itemView.findViewById(R.id.item_number);

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

