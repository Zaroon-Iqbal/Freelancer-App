package com.freelancer.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.freelancer.databinding.ActivityContractorProfileBinding;
import com.freelancer.ui.booking.PickListingDate;
import com.freelancer.ui.profile.portfolio.HorizontalRecyclerAdpater;
import com.freelancer.ui.profile.portfolio.PortfolioActivity;
import com.freelancer.ui.profile.portfolio.RecyclerViewInterface;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ContractorProfile extends AppCompatActivity implements RecyclerViewInterface {
    TextView name;
    TextView address;
    TextView about;
    ImageView pic;
    TextView link;
    Button message;
    Button appt;
    RecyclerView recycler;
    ArrayList<String> list;
    HorizontalRecyclerAdpater adapter;
    StorageReference storageRef;
    FirebaseFirestore fireStore;
    DocumentReference userDocRef;
    FirebaseUser user;
    Task<Uri> task = null;

    TextView social1;
    TextView social2;
    TextView social3;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        ActivityContractorProfileBinding binding = ActivityContractorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        social1 = binding.social1;
        social2 = binding.social2;
        social3 = binding.social3;

        name = binding.businessName;
        address = binding.businessAddress;
        about = binding.about;
        pic = binding.businessPic;
        link = binding.portfolioLink;
        message = binding.messageButton;
        appt = binding.appointmentButton;
        recycler = binding.horizontalPortfolioPreview;
        recycler.setLayoutManager(new LinearLayoutManager(ContractorProfile.this, LinearLayoutManager.HORIZONTAL, false));
        list = new ArrayList<>();
        fillList(list);
        fireStore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userDocRef = fireStore.collection("UsersExample").document("ConsumersExample").collection("ConsumerData")
                .document(user.getUid());
        fillBusinessInfo();

        appt.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PickListingDate.class)));

        link.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PortfolioActivity.class));
        });

        //appt.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/@HairHood"))));
    }

    private void fillBusinessInfo() {
        userDocRef.addSnapshotListener((document, e) -> {
            if (e != null) {
                Log.e("ERROR---", "Listen failed", e);
                return;
            }
            if (document != null && document.exists()) {
                storageRef = FirebaseStorage.getInstance().getReference();
                name.setText(document.getString("BusinessName"));
                address.setText(document.getString("Location"));
                about.setText(document.getString("About"));
                if (document.contains("ProfilePic")) {
                    StorageReference imageRef = storageRef.child(document.getString("ProfilePic"));
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        Glide.with(ContractorProfile.this).load(uri).into(pic);
                    });
                }
            } else Log.d("FAILED---", "No such document");
        });

        CollectionReference collection = userDocRef.collection("Socials");
        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Log.e("ERROR","Listen failed",error);
                    return;
                }
                if(value != null){
                    List<DocumentSnapshot> list = value.getDocuments();
//                    social1.setText(list.get(0).getString("URL"));
//                    social2.setText(list.get(1).getString("URL"));
//                    social3.setText(list.get(2).getString("URL"));
                    }
                }
        });
    }

    private void fillList(ArrayList<String> list) {
        storageRef = FirebaseStorage.getInstance().getReference().child("PortfolioEx");
        AtomicInteger index = new AtomicInteger();

        storageRef.list(7).addOnSuccessListener(listResult -> {
            for(StorageReference item: listResult.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    list.add(uri.toString());
                    if (index.get() == listResult.getItems().size() - 1) {
                        adapter = new HorizontalRecyclerAdpater(list, this);
                        recycler.setAdapter(adapter);
                    }
                    index.getAndIncrement();
                });
            }
        });
    }

    @Override
    public void onItemClicked(int pos) {
        Intent intent = new Intent(ContractorProfile.this, EnlargeImage.class);
        intent.putExtra("URI",list.get(pos));
        startActivity(intent);
    }
}