package com.freelancer.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelancer.R;
import com.freelancer.ReviewActivity;
import com.freelancer.ui.booking.BookListing;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AppointmentDetails extends AppCompatActivity {
    RecyclerView recyclerView;
    BookingDetails bookingDetails;
    ArrayList<Map<String,Object>> optionList;
    OptionsRecyclerViewAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_book_listing);
        bookingDetails = getIntent().getParcelableExtra("bookingDetails",BookingDetails.class);
        System.out.println(bookingDetails.map);
        TextView title = findViewById(R.id.title);
        TextView description = findViewById(R.id.reason);
        TextView price = findViewById(R.id.price);
        TextView location = findViewById(R.id.location);
        TextView business = findViewById(R.id.business);
        Button cancel = findViewById(R.id.cancelButton);
        cancel.setText("Close");
        Button book = findViewById(R.id.bookButton);
        book.setVisibility(View.GONE);
        Button review = findViewById(R.id.reviewButton);
        review.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.customOptions);

        FirebaseFirestore.getInstance().collection("jobListings")
                        .document((String) bookingDetails.map.get("Job Listing ID"))
                                .get().addOnSuccessListener(documentSnapshot -> {
                                    Map<String,Object> jobInfo = (Map<String, Object>) documentSnapshot.get("jobInfo");
                                    description.setText("Description:"+(String)bookingDetails.map.get("description"));
                                    price.setText("Base Price:"+(jobInfo.get("basePrice")).toString());
                                    location.setText("Location:"+(String) jobInfo.get("jobLocation"));
                                    String temp = "Business:\n"+(String) jobInfo.get("title") + "\n" + (String) bookingDetails.map.get("businessAddress");
                                    business.setText(temp);

                                    if(bookingDetails.options != null && !bookingDetails.options.isEmpty()){
                                        createOptionsList((Map<String,Object>)documentSnapshot.get("customOptions"));
                                        recyclerView.setLayoutManager(new LinearLayoutManager(AppointmentDetails.this));
                                        adapter = new OptionsRecyclerViewAdapter(optionList,bookingDetails);
                                        recyclerView.setAdapter(adapter);
                                    }

                                });
        cancel.setOnClickListener(v -> finish());
        review.setOnClickListener(v -> {
            Intent intent = new Intent(AppointmentDetails.this, ReviewActivity.class);
            intent.putExtra("uid",(String) bookingDetails.map.get("Contractor ID"));
            startActivity(intent);
        });
    }

    private void createOptionsList(Map<String, Object> customOptions) {
        optionList  = new ArrayList<>();
        for(Object obj: customOptions.values()){
            optionList.add((Map<String, Object>) obj);
        }
    }
}

class OptionsRecyclerViewAdapter extends RecyclerView.Adapter<OptionsRecyclerViewAdapter.OptionsViewHolder>{
    ArrayList<Map<String,Object>> list;
    BookingDetails bookingDetails;

    public OptionsRecyclerViewAdapter(ArrayList<Map<String,Object>> list, BookingDetails bookingDetails){
        this.list = list;
        this.bookingDetails = bookingDetails;
    }

    @NonNull
    @Override
    public OptionsRecyclerViewAdapter.OptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_custom_options,parent,false);
        return new OptionsRecyclerViewAdapter.OptionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsRecyclerViewAdapter.OptionsViewHolder holder, int position) {
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
        EditText editText;

        public OptionsViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.fieldName);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            layout = itemView.findViewById(R.id.customOptions);
            textInputLayout = itemView.findViewById(R.id.formLayout);
            editText = itemView.findViewById(R.id.formText);
        }
        void updateView(int pos){
            String type = (String) list.get(pos).get("fieldType");
            String selectionType = (String) list.get(pos).getOrDefault("selectionType","");
            String fieldName;
            //ArrayList<Integer> viewId = new ArrayList<>();
            if(type.equalsIgnoreCase( "BOOLEAN")){
                fieldName = (String) list.get(pos).get("fieldName");
                String choice = (String) bookingDetails.options.getOrDefault(fieldName,"");
                radioGroup.setVisibility(View.VISIBLE);
                radioGroup.setOrientation(LinearLayout.HORIZONTAL);
                RadioButton yes = new RadioButton(itemView.getContext());
                yes.setText("Yes");
                yes.setId(View.generateViewId());
                if(choice.equalsIgnoreCase("yes")){
                    yes.setChecked(true);
                }
                yes.setEnabled(false);
                //viewId.add(yes.getId());
                RadioButton no = new RadioButton(itemView.getContext());
                no.setText("No");
                no.setId(View.generateViewId());
                if(choice.equalsIgnoreCase("no")){
                    no.setChecked(true);
                }
                no.setEnabled(false);
                //viewId.add(no.getId());
                radioGroup.addView(yes);
                radioGroup.addView(no);
                modified = true;
            } else if (type.equalsIgnoreCase( "SELECTION") && selectionType.equalsIgnoreCase("multiple")) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,10,0,0);
                ArrayList<String> choices = ((ArrayList<String>)list.get(pos).get("options"));
                int size = choices.size();
                CheckBox checkBox;
                fieldName = (String) list.get(pos).get("fieldName");
                ArrayList<String> choice = (ArrayList<String>) bookingDetails.options.getOrDefault(fieldName,new ArrayList<String>());
                HashSet<String> set = new HashSet<>(choice);
                for(int i = 0; i < size; i++){
                    checkBox = new CheckBox(itemView.getContext());
                    checkBox.setText(choices.get(i));
                    checkBox.setId(View.generateViewId());
                    if(set.contains(choices.get(i)))
                        checkBox.setChecked(true);
                    checkBox.setEnabled(false);
                    //viewId.add(checkBox.getId());
                    layout.addView(checkBox,params);
                }
                modified = true;
            } else if (type.equalsIgnoreCase( "SELECTION") && selectionType.equalsIgnoreCase("single")) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,10,0,0);
                ArrayList<String> choices = ((ArrayList<String>)list.get(pos).get("options"));
                int size = choices.size();
                radioGroup.setVisibility(View.VISIBLE);
                radioGroup.setOrientation(LinearLayout.VERTICAL);
                RadioButton radioButton;
                fieldName = (String) list.get(pos).get("fieldName");
                String choice = (String) bookingDetails.options.getOrDefault(fieldName,"");
                for(int i =0; i < size; i++){
                    radioButton = new RadioButton(itemView.getContext());
                    radioButton.setText(choices.get(i));
                    radioButton.setId(View.generateViewId());
                    if(choices.get(i).equalsIgnoreCase(choice))
                        radioButton.setChecked(true);
                    radioButton.setEnabled(false);
                    //viewId.add(radioButton.getId());
                    radioGroup.addView(radioButton);
                }
                modified = true;
            } else if (type.equalsIgnoreCase("FREE_FORM")) {
                textInputLayout.setVisibility(View.VISIBLE);
                fieldName = (String) list.get(pos).get("fieldName");
                String choice = (String) bookingDetails.options.getOrDefault(fieldName,"");
                editText.setText(choice);
                editText.setEnabled(false);
                modified = true;
            }

        }
    }

}
