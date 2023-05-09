package com.freelancer.ui.profile.portfolio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toolbar;

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
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class PortfolioActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView recyclerView;
    ArrayList<ImageInfo> list;
    PortfolioRecyclerview adapter;
    MaterialToolbar toolbar;

    ProgressBar progress;

    final String TAG = "LOG";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityPortfolioBinding binding = ActivityPortfolioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.recycler;
        progress = binding.progressPortfolio;
        toolbar = binding.topAppBar;
        progress.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        fillList(list);
        recyclerView.setLayoutManager(new GridLayoutManager(PortfolioActivity.this,2));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void fillList(ArrayList<ImageInfo> list){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference collection = FirebaseFirestore.getInstance().collection("UsersExample")
                .document("ContractorsExample").collection("ContractorData")
                .document(user.getUid()).collection("Portfolio");

        collection.orderBy("Timestamp", Query.Direction.DESCENDING).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if(queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()){
                for(DocumentSnapshot documents:queryDocumentSnapshots.getDocuments()){
                    list.add(new ImageInfo(documents.getString("ProfilePhoto")));
                }
                adapter = new PortfolioRecyclerview(list,PortfolioActivity.this);
                recyclerView.setAdapter(adapter);
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemClicked(int pos, ArrayList<?> otherList) {
        Intent intent = new Intent(PortfolioActivity.this, EnlargeImage.class);
        intent.putExtra("URI",list.get(pos).uri);
        startActivity(intent);
    }
}
