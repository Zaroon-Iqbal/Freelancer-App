package com.freelancer.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelancer.R;
import com.freelancer.ui.booking.BookListing;
import com.freelancer.ui.booking.JobListing;
import com.freelancer.ui.booking.PickListingDate;
import com.freelancer.ui.profile.portfolio.RecyclerViewInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppointmentList extends BottomSheetDialogFragment implements RecyclerViewInterface{
    ArrayList<BookingDetails> books;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_listing_date, container, false);
        TextView title = v.findViewById(R.id.title);
        title.setText("Appointments");
        recyclerView = v.findViewById(R.id.jobListing);
        books = new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference bookings = firestore.collection("bookings");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = user.getUid();

        bookings.whereEqualTo("Consumer ID",id).whereEqualTo("approved",true).addSnapshotListener((value, error) -> {
            if(value != null && !value.isEmpty()){
                for(DocumentSnapshot document: value.getDocuments()) {

                    books.add(new BookingDetails(document.getData()));
                }
                ListingRecyclerViewAdapter adapter = new ListingRecyclerViewAdapter(books,this);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
            }
        });

        return v;
    }

    @Override
    public void onItemClicked(int pos, ArrayList<?> list) {
        Intent intent = new Intent(getActivity(), AppointmentDetails.class);
        intent.putExtra("bookingDetails", books.get(pos));
        startActivity(intent);
    }
}

class ListingRecyclerViewAdapter extends RecyclerView.Adapter<ListingRecyclerViewAdapter.ListingViewHolder>{

    ArrayList<BookingDetails> listings;
    RecyclerViewInterface click;

    public ListingRecyclerViewAdapter(ArrayList<BookingDetails> list,RecyclerViewInterface c){
        listings = list;
        click = c;
    }

    @NonNull
    @Override
    public ListingRecyclerViewAdapter.ListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_item,parent,false);
        return new ListingRecyclerViewAdapter.ListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingRecyclerViewAdapter.ListingViewHolder holder, int position) {
        holder.content.setText((String)listings.get(position).map.get("businessName")
                + "\n" + (String) listings.get(position).map.get("description"));
        holder.price.setText("Completed");
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
