package com.freelancer.ui.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelancer.R;
import com.freelancer.ui.profile.EditConsumerProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class BookListing extends AppCompatActivity {
    JobListing listing;
    TextView time;
    TextView reason;
    TextView price;
    TextView location;
    TextView business;
    Button cancel;
    Button book;
    RecyclerView recyclerView;
    ArrayList<Map<String,Object>> optionList;
    OptionsRecyclerViewAdapter adapter;
    HashSet<String> selection = new HashSet<>();
    HashMap<String,Integer> choice = new HashMap<>();

//    public BookListing(JobListing listing){
//        this.listing = listing;
//
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_book_listing);
        listing = getIntent().getParcelableExtra("Job Listing", JobListing.class);

        reason = findViewById(R.id.reason);
        price = findViewById(R.id.price);
        location = findViewById(R.id.location);
        business = findViewById(R.id.business);
        cancel = findViewById(R.id.cancelButton);
        book = findViewById(R.id.bookButton);
        recyclerView = findViewById(R.id.customOptions);

        reason.setText("Description: " + listing.jobInfo.get("description"));
        price.setText("Price: " + listing.jobInfo.get("basePrice"));
        location.setText("Location: " + listing.jobInfo.get("jobLocation"));
        business.setText("Business: \n" + listing.businessName + "\n" + listing.address);

        if (!listing.customOptions.isEmpty()) {
            createOptionsList();
            recyclerView.setLayoutManager(new LinearLayoutManager(BookListing.this));
            adapter = new OptionsRecyclerViewAdapter(optionList, selection, choice);
            recyclerView.setAdapter(adapter);
        }
        book.setOnClickListener(v1 -> bookAppointment());
        cancel.setOnClickListener(v12 -> finish());
    }

    private void createOptionsList() {
        optionList  = new ArrayList<>();
        for(Object obj: listing.customOptions.values()){
            optionList.add((Map<String, Object>) obj);
        }
    }

    private void bookAppointment() {
        CollectionReference collection = FirebaseFirestore.getInstance().collection("bookings");
        Map<String,Object> data = new HashMap<>();
        ArrayList<String> arr = new ArrayList<>(selection);
        data.put("Selection choices",arr);
        data.put("Boolean choices",choice);
        data.put("Contractor ID", FirebaseAuth.getInstance().getCurrentUser().getUid());
        data.put("Consumer ID","DHsQ6UhQMLPqthv5us9Y4ByIN4u1");
        data.put("Book Time",new Date());
        data.put("Job Listing ID", listing.map.get("Job Listing ID"));
        collection.add(data).addOnSuccessListener(documentReference -> {
            Toast.makeText(this,"Job Listing booked",Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(this,"Job Listing could not be booked",Toast.LENGTH_SHORT).show();
            finish();
        });
    }

}

class OptionsRecyclerViewAdapter extends RecyclerView.Adapter<OptionsRecyclerViewAdapter.OptionsViewHolder>{
    ArrayList<Map<String,Object>> list;
    HashMap<String,Integer> choice;
    HashSet<String> selection;

    public OptionsRecyclerViewAdapter(ArrayList<Map<String,Object>> list,HashSet<String> selection,HashMap<String,Integer> choice){
        this.selection = selection;
        this.choice = choice;
        this.list = list;
    }

    @NonNull
    @Override
    public OptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_custom_options,parent,false);
        return new OptionsRecyclerViewAdapter.OptionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsViewHolder holder, int position) {
        holder.textView.setText((String) list.get(position).get("fieldName"));
        if(!holder.modified){
            holder.updateView(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class OptionsViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        RadioGroup radioGroup;
        boolean modified = false;

        LinearLayout layout;

        public OptionsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.fieldName);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            layout = itemView.findViewById(R.id.customOptions);
        }
        void updateView(int pos){
            String type = (String) list.get(pos).get("fieldType");
            if(type.equalsIgnoreCase( "BOOLEAN")){
                String key = (String) list.get(pos).get("fieldName");
                choice.put(key,-1);
                radioGroup.setVisibility(View.VISIBLE);
                radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                    RadioButton radioButton = itemView.findViewById(checkedId);
                    if(radioButton.getText().toString().equalsIgnoreCase("Yes"))
                        choice.replace((String) list.get(pos).get("fieldName"),1);
                    else
                        choice.replace((String) list.get(pos).get("fieldName"),0);
                    System.out.println(radioButton.getText() + " button was selected!!");
                });
                modified = true;
            } else if (type.equalsIgnoreCase( "SELECTION")) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,10,0,0);
                ArrayList<String> choices = ((ArrayList<String>)list.get(pos).get("options"));
                int size = choices.size();
                CheckBox checkBox;
                for(int i = 0; i < size; i++){
                    checkBox = new CheckBox(itemView.getContext());
                    checkBox.setText(choices.get(i));
                    checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if(isChecked) {
                            System.out.println(buttonView.getText() + " was selected");
                            selection.add(buttonView.getText().toString());
                        }
                        else
                            selection.remove(buttonView.getText().toString());
                    });
                    layout.addView(checkBox,params);
                }
                modified = true;
            }
        }
    }

}
