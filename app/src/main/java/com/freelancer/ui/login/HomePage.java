package com.freelancer.ui.login;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.freelancer.R;
import com.freelancer.TestingActivity;
import com.freelancer.calendar.CalendarActivity;
import com.freelancer.databinding.ActivityHomePageBinding;
import com.freelancer.placeholder.FavoriteActivityPlaceholder;
import com.freelancer.placeholder.MessageActivityPlaceholder;
import com.freelancer.placeholder.ProfileActivityPlaceholder;
import com.freelancer.ui.bidding.BiddingContractorMain;
import com.freelancer.ui.bidding.Customer.CustomerBidMain;
import com.freelancer.ui.map.MapActivity;
import com.freelancer.ui.history.AppointmentList;
import com.freelancer.ui.profile.ConsumerProfile;
import com.freelancer.ui.profile.ContractorProfile;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HomePage extends AppCompatActivity {
    private static int count = 0;
    private static Toast toast;
    String type;
    String documentId;

    Button b1;
    Button b2;
    ImageButton ib1;
    ImageButton ib2;
    ImageButton ib3;
    ImageButton ib4;
    ImageButton ib5;
    ImageButton map;
    ImageButton bidding;

    ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_home_page);

        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ib1 = findViewById(R.id.iconContractor1);
        ib2 = findViewById(R.id.iconContractor2);
        ib3 = findViewById(R.id.iconContractor3);
        ib4 = findViewById(R.id.iconContractor4);
        ib5 = findViewById(R.id.iconContractor5);
        map = findViewById(R.id.mapHomeButton);
        bidding = findViewById(R.id.biddingHomeButton);

        Glide.with(this).load("https://img.freepik.com/premium-vector/flat-design-towing-logo-template_23-2149338504.jpg?w=2000").into(ib1);
        Glide.with(this).load("https://cdn.dribbble.com/users/6223082/screenshots/15093380/hair_salon_logo.png").into(ib2);
        Glide.with(this).load("https://static.vecteezy.com/system/resources/previews/005/166/059/original/carpenter-logo-illustration-design-wood-worker-workshop-icon-symbol-free-vector.jpg").into(ib3);
        Glide.with(this).load("https://static.vecteezy.com/system/resources/previews/012/371/334/original/bakery-logo-design-with-flat-style-of-bakery-chef-hat-and-spoon-illustration-vector.jpg").into(ib4);
        Glide.with(this).load("https://st2.depositphotos.com/5486388/8033/v/950/depositphotos_80336468-stock-illustration-carmaintenance-logo-template.jpg").into(ib5);
        Glide.with(this).load("https://cdn-icons-png.flaticon.com/512/1865/1865269.png").into(map);
        Glide.with(this).load("https://cdn-icons-png.flaticon.com/512/204/204180.png").into(bidding);

        initAdMob();

        //int random = (int) (Math.random() * 3);
        int random = 1;

        if (random == 1) {
            loadBannerAd();
        }

        b1 = findViewById(R.id.searchBtn);
        b1.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(android.view.View v) {
                Intent i = new Intent(HomePage.this, Search.class);
                startActivity(i);
            }
        });

        /* b2 = findViewById(R.id.btnLoadBannerAd);
        b2.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(android.view.View v) {
                Intent i = new Intent(HomePage.this, BannerAdActivity.class);
                startActivity(i);
            }
        }); */

        // binding.btnLoadBannerAd.setOnClickListener(view -> {
        // startActivity(new Intent(HomePage.this, BannerAdActivity.class));

        //The bottom navigation bar

        ImageView imageView = findViewById(R.id.freelancerlogo2);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("userListings");
        collectionReference.whereEqualTo("uid", user.getUid()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    type = document.getString("type");
                    documentId = document.getId();
                }
            }
        });

        imageView.setOnClickListener(view -> {
            count++;
            if (toast != null) {
                toast.cancel();
            }
            if (count == 3) { //by clicking on the freelancer name 5 times you will be navigated
                count = 0;
                Intent intent = new Intent(getApplicationContext(), ContractorProfile.class);
                intent.putExtra("uid","D6QSDVdSuObAGtBWKrY7wtMsM7U2");
                startActivity(intent);
                //startActivity(new Intent(this.getApplicationContext(), TestingActivity.class));
//                Bundle bundle = new Bundle();
//                bundle.putString("uid","DHsQ6UhQMLPqthv5us9Y4ByIN4u1");
//                PickListingDate listingDate = new PickListingDate();
//                listingDate.setArguments(bundle);
//                listingDate.show(getSupportFragmentManager(),"Pick Job Listing");
                toast = Toast.makeText(getApplicationContext(), "Welcome to the secret menu \uD83D\uDE0E \uD83D\uDD25", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            toast = Toast.makeText(getApplicationContext(), "Only " + (3 - count) + " more taps...", Toast.LENGTH_SHORT);
            toast.show();
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.HomeNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    //When user clicks on the home icon
                    case R.id.HomeNav:
                        return true;

                    //When user clicks on the calendar icon
                    case R.id.CalendarNav:
//                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
//                        overridePendingTransition(0, 0);
                        AppointmentList appointmentList = new AppointmentList();
                        appointmentList.show(getSupportFragmentManager(),"Appointments");
                        return true;

                    //When user clicks on the favorites icon
                    case R.id.FavoriteNav:
                        startActivity(new Intent(getApplicationContext(), FavoriteActivityPlaceholder.class));
                        overridePendingTransition(0, 0);
                        return true;

                    //When user clicks on the message icon
                    case R.id.MessageNav:
                        startActivity(new Intent(getApplicationContext(), MessageActivityPlaceholder.class));
                        overridePendingTransition(0, 0);
                        return true;

                    //When user clicks on the profile icon
                    case R.id.ProfileNav:
                        if (type.equalsIgnoreCase("contractor")) {
                            Intent intent = new Intent(getApplicationContext(), ContractorProfile.class);
                            intent.putExtra("documentId", documentId);
                            intent.putExtra("documentId",documentId);
                            intent.putExtra("uid",user.getUid());
                            startActivity(intent);
                        } else if (type.equalsIgnoreCase("consumer")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("documentId", documentId);
                            ConsumerProfile consumer = new ConsumerProfile();
                            consumer.setArguments(bundle);
                            consumer.show(getSupportFragmentManager(), "Consumer Profile");
                        } else {
                            startActivity(new Intent(getApplicationContext(), ProfileActivityPlaceholder.class));
                            overridePendingTransition(0, 0);
                        }
                        return true;
                }
                return false;
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, MapActivity.class));
            }
        });

        bidding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectionReference contractor = FirebaseFirestore.getInstance().collection("UsersExample").document("ContractorsExample").collection("ContractorData");
                CollectionReference consumer = FirebaseFirestore.getInstance().collection("UsersExample").document("ConsumersExample").collection("ConsumerData");
                contractor.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equals(user.getUid())) {
                                    startActivity(new Intent(HomePage.this, BiddingContractorMain.class));
                                    break;
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
                startActivity(new Intent(HomePage.this, CustomerBidMain.class));
            }
        });
    }

    private void initAdMob() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.i("Admob", "onInitializationComplete: ");
            }
        });
    }

    private void loadBannerAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        binding.adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.i("Admob", "Error : " + loadAdError);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.i("Admob", "adLoaded ");
            }
        });
    }

}