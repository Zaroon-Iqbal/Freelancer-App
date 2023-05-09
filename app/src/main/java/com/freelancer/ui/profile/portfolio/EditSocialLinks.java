package com.freelancer.ui.profile.portfolio;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelancer.R;
import com.freelancer.databinding.ActivityEditSocialsBinding;
import com.freelancer.ui.profile.SocialLinks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditSocialLinks extends AppCompatActivity {
    RecyclerView recyclerView;
    Button done;
    Button cancel;
    TextView add;
    CollectionReference socials;
    ArrayList<SocialLinks> list;
    LinkRecyclerAdapter adapter;
    boolean empty;
    String document;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        ActivityEditSocialsBinding binding = ActivityEditSocialsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.links;
        done = binding.done;
        add = binding.addLink;
        cancel = binding.cancel;
        list = new ArrayList<>();

        socials = FirebaseFirestore.getInstance().collection("UsersExample").document("ContractorsExample").collection("ContractorData")
                .document(getIntent().getStringExtra("businessID")).collection("Socials");

        LinearLayoutManager manager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically(){
                return false;
            }
        };

        recyclerView.setLayoutManager(manager);

        getSocials();

        add.setOnClickListener(v -> {
            list.add(new SocialLinks());
            adapter.notifyItemInserted(list.size()-1);
        });

        done.setOnClickListener(v -> saveLinks());
        cancel.setOnClickListener(v -> finish());
    }

    private void saveLinks() {
        HashMap<String,String> data = new HashMap<>();
        String titles;
        String urls;

        for(int i = 0; i < list.size(); i++){
            titles = ((EditText)recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.title)).getText().toString();
            urls = ((EditText)recyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.url)).getText().toString();
            data.put(titles,urls);
        }

        HashMap<String,HashMap<String,String>> save = new HashMap<>();
        save.put("Links",data);

        if(empty){
            socials.add(save).addOnSuccessListener(documentReference -> Toast.makeText(EditSocialLinks.this,"Links added",Toast.LENGTH_SHORT).show());
        }
        else{
            socials.document(document).set(save).addOnSuccessListener(unused -> Toast.makeText(EditSocialLinks.this,"Links updated",Toast.LENGTH_SHORT).show());
        }
        finish();
    }

    private void getSocials() {
        socials.addSnapshotListener((value, error) -> {
            if(error != null)
                Log.e("ERROR---","Could not get collection");
            else if (value != null && !value.isEmpty()) {
                empty = false;
                for(DocumentSnapshot documents: value.getDocuments()){
                    Map<String,Object> map = (Map<String, Object>) documents.get("Links");
                    document = documents.getId();
                    for(Map.Entry<String,Object> entry : map.entrySet()){
                        list.add(new SocialLinks(entry.getKey(),entry.getValue().toString()));
                    }
                }
                adapter = new LinkRecyclerAdapter(list);
                recyclerView.setAdapter(adapter);
            }
            else{
                empty = true;
                adapter = new LinkRecyclerAdapter(list);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}

class LinkRecyclerAdapter extends RecyclerView.Adapter<LinkRecyclerAdapter.LinkViewHolder>{
    ArrayList<SocialLinks> list;

    public LinkRecyclerAdapter(ArrayList list){
        this.list = list;
    }
    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_social_link,parent,false);
        return new LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        if(list.get(position).title != null)
            holder.title.setText(list.get(position).title);
        if(list.get(position).url != null)
            holder.url.setText(list.get(position).url);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LinkViewHolder extends RecyclerView.ViewHolder{
        EditText title;
        EditText url;
        ImageView delete;
        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            url = itemView.findViewById(R.id.url);
            delete = itemView.findViewById(R.id.delete);

            delete.setOnClickListener(v -> {
                list.remove(getBindingAdapterPosition());
                notifyItemRemoved(getBindingAdapterPosition());
            });
        }
    }
}
