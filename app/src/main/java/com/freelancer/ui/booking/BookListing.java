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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelancer.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Map;

public class BookListing extends BottomSheetDialogFragment {
    JobListing listing;
    TextView time;
    TextView reason;
    TextView price;
    TextView location;
    Button cancel;
    Button book;
    RecyclerView recyclerView;
    ArrayList<Map<String,Object>> optionList;
    OptionsRecyclerViewAdapter adapter;

    public BookListing(JobListing listing){
        this.listing = listing;
        optionList  = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_book_listing,container,false);

        time = v.findViewById(R.id.time);
        reason = v.findViewById(R.id.reason);
        price = v.findViewById(R.id.price);
        location = v.findViewById(R.id.location);
        cancel = v.findViewById(R.id.cancelButton);
        book = v.findViewById(R.id.bookButton);
        recyclerView = v.findViewById(R.id.customOptions);

        time.setText("Time: "+listing.timestamp);
        reason.setText("Reason: "+listing.content);
        price.setText("Price: "+listing.price);
        location.setText("Location: \n"+listing.businessName+"\n"+listing.address);

        if(listing.map.containsKey("customOptions")) {
            createOptionsList();
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new OptionsRecyclerViewAdapter(optionList);
            recyclerView.setAdapter(adapter);
        }

        //cancel.setOnClickListener(v1 -> finish());
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookAppointment();
            }
        });
        return v;
    }

    private void createOptionsList() {
        Map<String,Object> options = (Map<String, Object>) listing.map.get("customOptions");
        for(Object obj:options.values()){
            optionList.add((Map<String, Object>) obj);
        }
    }

    private void bookAppointment() {
    }

}

class OptionsRecyclerViewAdapter extends RecyclerView.Adapter<OptionsRecyclerViewAdapter.OptionsViewHolder>{
    ArrayList<Map<String,Object>> list;

    public OptionsRecyclerViewAdapter(ArrayList<Map<String,Object>> list){
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
                list.get(pos).put(key,-1);
                radioGroup.setVisibility(View.VISIBLE);
                radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                    RadioButton radioButton = itemView.findViewById(checkedId);
                    if(radioButton.getText().toString().equalsIgnoreCase("Yes"))
                        list.get(pos).replace((String) list.get(pos).get("fieldName"),1);
                    else
                        list.get(pos).replace((String) list.get(pos).get("fieldName"),0);
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
                    checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> System.out.println(buttonView.getText() + " was selected"));
                    layout.addView(checkBox,params);
                }
                modified = true;
            }
        }
    }

}
