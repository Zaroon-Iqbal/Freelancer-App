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
    RecyclerView recycler;
    ArrayList<String> list;
    HorizontalRecyclerAdpater adapter;
    StorageReference storageRef;
    FirebaseFirestore fireStore;
    DocumentReference userDocRef;
    FirebaseUser user;
    String businessName = "";
    String aboutBusiness = "";
    String businessAddress = "";
    String userId;
    LinearLayout linearLayout;
    ArrayList<TextView> textViews;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        ActivityContractorProfileBinding binding = ActivityContractorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = FirebaseAuth.getInstance().getCurrentUser();

        linearLayout = binding.linearLayout;
        name = binding.businessName;
        address = binding.businessAddress;
        about = binding.about;
        pic = binding.businessPic;
        link = binding.portfolioLink;
        message = binding.messageButton;
        appt = binding.appointmentButton;
        edit = binding.editButton;
        back = binding.back;
        recycler = binding.horizontalPortfolioPreview;
        recycler.setLayoutManager(new LinearLayoutManager(ContractorProfile.this, LinearLayoutManager.HORIZONTAL, false));
        list = new ArrayList<>();
        textViews = new ArrayList<>();

        fireStore = FirebaseFirestore.getInstance();

//        userDocRef = fireStore.collection("UsersExample").document("ContractorsExample").collection("ContractorData")
//                .document(user.getUid());
        userDocRef = fireStore.collection("userListings").document(getIntent().getStringExtra("documentId"));
        fillBusinessInfo();

        fillList(list);

        appt.setOnClickListener(v -> {
            //startActivity(new Intent(getApplicationContext(), PickListingDate.class));
            Bundle bundle = new Bundle();
            bundle.putString("documentId", getIntent().getStringExtra("documentId"));
            PickListingDate pick = new PickListingDate();
            pick.setArguments(bundle);
            pick.show(getSupportFragmentManager(),"Pick Listing");
        });

        link.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PortfolioActivity.class);
            intent.putExtra("documentId", getIntent().getStringExtra("documentId"));
            startActivity(intent);
        });

        edit.setOnClickListener(v -> {
            Intent intent = new Intent(ContractorProfile.this, EditContractorProfile.class);
            intent.putExtra("Name",businessName);
            intent.putExtra("Address",businessAddress);
            intent.putExtra("About",aboutBusiness);
            intent.putExtra("documentId", getIntent().getStringExtra("documentId"));
            startActivity(intent);
        });

        back.setOnClickListener(v -> finish());

        //appt.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/@HairHood"))));
    }

    private void fillBusinessInfo() {
        userDocRef.addSnapshotListener((document, e) -> {
            if (e != null) {
                Log.e("ERROR---", "Listen failed", e);
                return;
            }
            if (document != null && document.exists()) {
                //storageRef = FirebaseStorage.getInstance().getReference();
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
                adapter = new HorizontalRecyclerAdpater(list, this);
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