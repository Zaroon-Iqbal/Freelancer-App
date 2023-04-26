package com.freelancer.ui.profile.portfolio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelancer.databinding.ActivityPortfolioBinding;
import com.freelancer.ui.profile.EnlargeImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PortfolioActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView recyclerView;
    ArrayList<String> list;
    PortfolioRecyclerview adapter;

    ProgressBar progress;

    final String TAG = "LOG";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityPortfolioBinding binding = ActivityPortfolioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.recycler;
        progress = binding.progressPortfolio;
        progress.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        fillList(list);
        recyclerView.setLayoutManager(new GridLayoutManager(PortfolioActivity.this,2));
    }

    public void fillList(ArrayList<String> list){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference collection = FirebaseFirestore.getInstance().collection("UsersExample")
                .document("ContractorsExample").collection("ContractorData")
                .document(user.getUid()).collection("Portfolio");
        //StorageReference folder = FirebaseStorage.getInstance().getReference();
        //folder = folder.child("PortfolioEx");
        //AtomicInteger index = new AtomicInteger();

        collection.orderBy("Timestamp").get().addOnSuccessListener(queryDocumentSnapshots -> {
            if(!queryDocumentSnapshots.isEmpty()){
                for(DocumentSnapshot documents:queryDocumentSnapshots.getDocuments()){
                    list.add(documents.getString("ProfilePhoto"));
                }
                adapter = new PortfolioRecyclerview(list,PortfolioActivity.this);
                recyclerView.setAdapter(adapter);
                progress.setVisibility(View.GONE);
            }
        });

//        folder.listAll().addOnSuccessListener(listResult -> {
//            for(StorageReference item: listResult.getItems()){
//                item.getDownloadUrl().addOnSuccessListener(uri -> {
//                    list.add(uri.toString());
//                    if(index.get() == listResult.getItems().size()-1){
//                        adapter = new PortfolioRecyclerview(list,this);
//                        recyclerView.setAdapter(adapter);
//                        progress.setVisibility(View.GONE);
//                    }
//                    index.getAndIncrement();
//                }).addOnFailureListener(e -> Log.e("DEBUG---------------","downloadUrl failed",e));
//            }
//        }).addOnFailureListener(e -> Log.e("DEBUG-------------","listall failed",e));
    }

    @Override
    public void onItemClicked(int pos) {
        Intent intent = new Intent(PortfolioActivity.this, EnlargeImage.class);
        intent.putExtra("URI",list.get(pos));
        startActivity(intent);
    }
}