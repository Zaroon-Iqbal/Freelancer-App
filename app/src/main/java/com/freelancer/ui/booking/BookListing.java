package com.freelancer.ui.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class BookListing extends AppCompatActivity {
    JobListing listing;
    TextView reason;
    TextView price;
    TextView location;
    TextView business;
    Button cancel;
    Button book;
    RecyclerView recyclerView;
    ArrayList<Map<String,Object>> optionList;
    OptionsRecyclerViewAdapter adapter;
    HashMap<Integer,ArrayList<Integer>> ids = new HashMap<>();
    String businessAddress;

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

        CollectionReference ref = FirebaseFirestore.getInstance().collection("userListings");
        ref.whereEqualTo("uid",listing.jobInfo.get("businessId")).addSnapshotListener((value, error) -> {
            if (value != null && !value.isEmpty()){
                businessAddress = value.getDocuments().get(0).getString("Location");
                business.setText("Business: \n" + listing.jobInfo.get("title") + "\n" + businessAddress);
            }
        });

        reason.setText("Description: " + listing.jobInfo.get("description"));
        price.setText("Price: " + listing.jobInfo.get("basePrice"));
        location.setText("Location: " + listing.jobInfo.get("jobLocation"));


        if (!listing.customOptions.isEmpty()) {
            createOptionsList();
            recyclerView.setLayoutManager(new LinearLayoutManager(BookListing.this));
            adapter = new OptionsRecyclerViewAdapter(optionList,ids);
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
        data.put("businessName",listing.jobInfo.get("title"));
        data.put("businessAddress",businessAddress);
        data.put("description",listing.jobInfo.get("description"));
        data.put("Contractor ID", listing.jobInfo.get("businessId"));
        data.put("Consumer ID",FirebaseAuth.getInstance().getCurrentUser().getUid());
        data.put("Book Time",new Date());
        data.put("Job Listing ID", listing.jobInfo.get("Job Listing ID"));
        data.put("approved",true);
        if(optionList != null)
        {
            HashMap<String,Object> customs = new HashMap<>();
            for (int i = 0; i < optionList.size(); i++) {
                View view = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                String type = (String) optionList.get(i).get("fieldType");
                String fieldName = (String) optionList.get(i).get("fieldName");
                String selectionType = (String) optionList.get(i).getOrDefault("selectionType", "");
                if (type.equalsIgnoreCase("FREE_FORM")) {
                    customs.put(fieldName, ((EditText) view.findViewById(R.id.formText)).getText().toString().trim());
                } else if (type.equalsIgnoreCase("selection") && selectionType.equalsIgnoreCase("multiple")) {
                    ArrayList<Integer> idList = ids.get(i);
                    ArrayList<String> options = new ArrayList<>();
                    for (int j = 0; j < idList.size(); j++) {
                        View view1 = view.findViewById(idList.get(j));
                        if (view1 instanceof CheckBox) {
                            if (((CheckBox) view1).isChecked()) {
                                options.add(((CheckBox) view1).getText().toString());
                            }
                        }
                    }
                    customs.put(fieldName, options);
                } else {
                    int a = ((RadioGroup) view.findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
                    String s;
                    if(a > -1)
                        s = ((RadioButton) view.findViewById(a)).getText().toString();
                    else
                        s = "";
                    customs.put(fieldName, s);
                }
            }
            data.put("customOptions",customs);
        }
        System.out.println(data);
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
    HashMap<Integer,ArrayList<Integer>> ids;

    public OptionsRecyclerViewAdapter(ArrayList<Map<String,Object>> list, HashMap<Integer,ArrayList<Integer>>ids){
        this.list = list;
        this.ids = ids;
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
        TextInputLayout textInputLayout;

        public OptionsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.fieldName);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            layout = itemView.findViewById(R.id.customOptions);
            textInputLayout = itemView.findViewById(R.id.formLayout);
        }
        void updateView(int pos){
            String type = (String) list.get(pos).get("fieldType");
            String selectionType = (String) list.get(pos).getOrDefault("selectionType","");
            ArrayList<Integer> viewId = new ArrayList<>();
            if(type.equalsIgnoreCase( "BOOLEAN")){
                radioGroup.setVisibility(View.VISIBLE);
                radioGroup.setOrientation(LinearLayout.HORIZONTAL);
                RadioButton yes = new RadioButton(itemView.getContext());
                yes.setText("Yes");
                yes.setId(View.generateViewId());
                viewId.add(yes.getId());
                RadioButton no = new RadioButton(itemView.getContext());
                no.setText("No");
                no.setId(View.generateViewId());
                viewId.add(no.getId());
                radioGroup.addView(yes);
                radioGroup.addView(no);
                ids.put(pos,viewId);
                modified = true;
            } else if (type.equalsIgnoreCase( "SELECTION") && selectionType.equalsIgnoreCase("multiple")) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,10,0,0);
                ArrayList<String> choices = ((ArrayList<String>)list.get(pos).get("options"));
                int size = choices.size();
                CheckBox checkBox;
                for(int i = 0; i < size; i++){
                    checkBox = new CheckBox(itemView.getContext());
                    checkBox.setText(choices.get(i));
                    checkBox.setId(View.generateViewId());
                    viewId.add(checkBox.getId());
                    layout.addView(checkBox,params);
                }
                ids.put(pos,viewId);
                modified = true;
            } else if (type.equalsIgnoreCase( "SELECTION") && selectionType.equalsIgnoreCase("single")) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,10,0,0);
                ArrayList<String> choices = ((ArrayList<String>)list.get(pos).get("options"));
                int size = choices.size();
                radioGroup.setVisibility(View.VISIBLE);
                radioGroup.setOrientation(LinearLayout.VERTICAL);
                RadioButton radioButton;
                for(int i =0; i < size; i++){
                    radioButton = new RadioButton(itemView.getContext());
                    radioButton.setText(choices.get(i));
                    radioButton.setId(View.generateViewId());
                    viewId.add(radioButton.getId());
                    radioGroup.addView(radioButton);
                }
                ids.put(pos,viewId);
                modified = true;
            } else if (type.equalsIgnoreCase("FREE_FORM")) {
                textInputLayout.setVisibility(View.VISIBLE);
                modified = true;
                ids.put(pos,new ArrayList<>());
            }

        }
    }

}
