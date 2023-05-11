package com.freelancer.ui.profile;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.freelancer.R;
import com.freelancer.databinding.ActivityContractorProfileBinding;
import com.freelancer.joblisting.CreateJobListingTabbedActivity;
import com.freelancer.placeholder.MessageActivityPlaceholder;
import com.freelancer.ui.booking.PickListingDate;
import com.freelancer.ui.profile.portfolio.HorizontalRecyclerAdpater;
import com.freelancer.ui.profile.portfolio.PortfolioActivity;
import com.freelancer.ui.profile.portfolio.RecyclerViewInterface;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ContractorProfile extends AppCompatActivity implements RecyclerViewInterface {
    TextView name;
    TextView address;
    TextView about;
    ImageView pic;
    ImageView back;
    TextView link;
    Button message;
    Button appt;
    Button edit;
    Button jobListing;
    RecyclerView recycler;
    RecyclerView reviews;
    ArrayList<String> list;
    HorizontalRecyclerAdpater adapter;
    HorizontalRecyclerAdpater adapter1;
    StorageReference storageRef;
    FirebaseFirestore fireStore;
    DocumentReference userDocRef;
    CollectionReference userCollRef;
    FirebaseUser user;
    String businessName = "";
    String aboutBusiness = "";
    String businessAddress = "";
    String userId;
    String uid;
    String docId;
    LinearLayout linearLayout;
    ArrayList<TextView> textViews;
    boolean same;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        ActivityContractorProfileBinding binding = ActivityContractorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = FirebaseAuth.getInstance().getCurrentUser();

        uid = getIntent().getStringExtra("uid");
        docId = getIntent().getStringExtra("documentId");

        if(uid!=null && uid.equalsIgnoreCase(user.getUid()))
            same = true;
        else
            same = false;

        linearLayout = binding.linearLayout;
        name = binding.businessName;
        address = binding.businessAddress;
        about = binding.about;
        pic = binding.businessPic;
        link = binding.portfolioLink;
        jobListing = binding.jobListing;
        reviews= binding.reviews;
        edit = binding.editButton;
        if(same) {
            jobListing.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
        }
        message = binding.messageButton;
        appt = binding.appointmentButton;
        if(!same){
            message.setVisibility(View.VISIBLE);
            appt.setVisibility(View.VISIBLE);
        }

        back = binding.back;
        recycler = binding.horizontalPortfolioPreview;
        recycler.setLayoutManager(new LinearLayoutManager(ContractorProfile.this, LinearLayoutManager.HORIZONTAL, false));
        reviews.setLayoutManager(new LinearLayoutManager(ContractorProfile.this,RecyclerView.HORIZONTAL,false));
        list = new ArrayList<>();
        textViews = new ArrayList<>();

        fireStore = FirebaseFirestore.getInstance();
        if(same)
        {
            userDocRef = fireStore.collection("userListings").document(docId);
            fillBusinessInfo();

            fillList(list);

            link.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), PortfolioActivity.class);
                intent.putExtra("documentId", docId);
                startActivity(intent);
            });

            edit.setOnClickListener(v -> {
                Intent intent = new Intent(ContractorProfile.this, EditContractorProfile.class);
                intent.putExtra("Name",businessName);
                intent.putExtra("Address",businessAddress);
                intent.putExtra("About",aboutBusiness);
                intent.putExtra("documentId", docId);
                startActivity(intent);
            });
        }
        else {
            userCollRef = fireStore.collection("userListings");
            userCollRef.whereEqualTo("uid",uid).get().addOnSuccessListener(queryDocumentSnapshots -> {
                if(queryDocumentSnapshots!=null && !queryDocumentSnapshots.isEmpty()){
                    DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                    docId = document.getId();
                    userDocRef = fireStore.collection("userListings").document(docId);
                    fillBusinessInfo();

                    fillList(list);

                    link.setOnClickListener(v -> {
                        Intent intent = new Intent(getApplicationContext(), PortfolioActivity.class);
                        intent.putExtra("documentId", docId);
                        startActivity(intent);
                    });

                    edit.setOnClickListener(v -> {
                        Intent intent = new Intent(ContractorProfile.this, EditContractorProfile.class);
                        intent.putExtra("Name",businessName);
                        intent.putExtra("Address",businessAddress);
                        intent.putExtra("About",aboutBusiness);
                        intent.putExtra("documentId", docId);
                        startActivity(intent);
                    });
                }
            });
        }



        jobListing.setOnClickListener(v -> startActivity(new Intent(ContractorProfile.this, CreateJobListingTabbedActivity.class)));

        appt.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("uid",uid);
            PickListingDate pick = new PickListingDate();
            pick.setArguments(bundle);
            pick.show(getSupportFragmentManager(),"Pick Listing");
        });


        back.setOnClickListener(v -> finish());
        message.setOnClickListener(v -> {
            startActivity(new Intent(ContractorProfile.this, MessageActivityPlaceholder.class));
        });
    }

    private void fillBusinessInfo() {
        userDocRef.addSnapshotListener((document, e) -> {
            if (e != null) {
                Log.e("ERROR---", "Listen failed", e);
                return;
            }
            if (document != null && document.exists()) {
                if(document.contains("BusinessName")) {
                    if(!document.getString("BusinessName").isEmpty())
                    {
                        businessName = document.getString("BusinessName");
                        name.setText(businessName);
                    }
                }
                if(document.contains("Location")) {
                    if(!document.getString("Location").isEmpty())
                    {
                        businessAddress = document.getString("Location");
                        address.setText(businessAddress);
                    }
                }
                if(document.contains("About")) {
                    if(!document.getString("About").isEmpty())
                    {
                        aboutBusiness = document.getString("About");
                        about.setText(aboutBusiness);
                    }
                }
                if (document.contains("ProfilePic")) {
                    if(!document.getString("ProfilePic").isEmpty())
                    {
                        Glide.with(ContractorProfile.this).load(document.getString("ProfilePic")).into(pic);
                    }
                }
                userId = document.getString("uid");
            } else Log.d("FAILED---", "No such document");
        });

        CollectionReference collection = userDocRef.collection("Socials");
        collection.addSnapshotListener((value, error) -> {
            if(error != null){
                Log.e("ERROR","Listen failed",error);
                return;
            }
            if(textViews.size() > 0){
                for(int i = 0; i < textViews.size(); i++){
                    linearLayout.removeView(textViews.get(i));
                }
                textViews.clear();
            }
            if(value != null && !value.isEmpty()){
                DocumentSnapshot snap = value.getDocuments().get(0);
                Map<String,Object> data = (Map<String, Object>) snap.get("Links");
                if(!data.isEmpty())
                {
                    Set<String> keys = data.keySet();

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(20, 0, 0, 15);
                    for (String title : keys) {
                        TextView view = new TextView(this);
                        view.setText(title);
                        view.setTextColor(Color.parseColor("#5895A5"));
                        view.setOnClickListener(v -> {
                            System.out.println(data.get(title).toString());
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(data.get(title).toString())));
                        });
                        linearLayout.addView(view, params);
                        textViews.add(view);
                    }
                }
            }
        });
        CollectionReference reviewCollection = fireStore.collection("jobReviews");
        ArrayList<ReviewInfo> reviewInfos = new ArrayList<>();
        reviewCollection.whereEqualTo("uid",uid).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if(queryDocumentSnapshots!=null && !queryDocumentSnapshots.isEmpty()){
                for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()){
                    reviewInfos.add(new ReviewInfo(documentSnapshot.getDouble("starRating"),documentSnapshot.getString("Comment")));
                }
                adapter1 = new HorizontalRecyclerAdpater(reviewInfos,R.layout.fragment_review_card,this);
                reviews.setAdapter(adapter1);
            }
        });
    }

    private void fillList(ArrayList<String> list) {
        storageRef = FirebaseStorage.getInstance().getReference().child(user.getUid());

        CollectionReference reference = userDocRef.collection("Portfolio");
        reference.addSnapshotListener((value, error) -> {
            if(value != null && !value.isEmpty()){
                list.clear();
                List<DocumentSnapshot> pictures = value.getDocuments();
                int length = Math.min((pictures.size()), 5);
                for(int i = 0; i < length; i++){
                    list.add(pictures.get(i).get("ProfilePhoto").toString());
                }
                adapter = new HorizontalRecyclerAdpater(list, R.layout.horizontal_images, this);
                recycler.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onItemClicked(int pos, ArrayList<?> otherList) {
        Intent intent = new Intent(ContractorProfile.this, EnlargeImage.class);
        intent.putExtra("URI",list.get(pos));
        startActivity(intent);
    }
}