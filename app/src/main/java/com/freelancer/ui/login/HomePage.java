package com.freelancer.ui.login;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.BannerAdActivity;
import com.freelancer.R;
import com.freelancer.Search;
import com.freelancer.TestingActivity;
import com.freelancer.data.calendar.CalendarActivity;
import com.freelancer.databinding.ActivityBannerAdBinding;
import com.freelancer.databinding.ActivityHomePageBinding;
import com.freelancer.placeholder.FavoriteActivityPlaceholder;
import com.freelancer.placeholder.MessageActivityPlaceholder;
import com.freelancer.placeholder.ProfileActivityPlaceholder;
import com.freelancer.ui.profile.ConsumerProfile;
import com.freelancer.ui.profile.ContractorProfile;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    private static int count = 0;
    private static Toast toast;
    String type = "";
    Button b1;
    Button b2;

    ActivityHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home_page);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initAdMob();

        b1 = findViewById(R.id.searchBtn);
        b1.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(android.view.View v) {
                Intent i = new Intent(HomePage.this, Search.class);
                startActivity(i);
            }
        });

        int random = (int) (Math.random() * 3);

        if (random == 1) {
            loadBannerAd();
        }

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
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("UsersExample");

        DocumentReference consumer = collectionReference.document("ConsumersExample").collection("ConsumerData").document(user.getUid());
        DocumentReference contractor = collectionReference.document("ContractorsExample").collection("ContractorData").document(user.getUid());

        consumer.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists())
                    type = "consumer";
            }
        });

        contractor.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists())
                    type = "contractor";
            }
        });

        imageView.setOnClickListener(view -> {
            count++;
            if (toast != null) {
                toast.cancel();
            }
            if (count == 3) { //by clicking on the freelancer name 5 times you will be navigated
                count = 0;
                startActivity(new Intent(this.getApplicationContext(), TestingActivity.class));
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
                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        overridePendingTransition(0, 0);
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
                        if (type.equalsIgnoreCase("contractor"))
                            startActivity(new Intent(getApplicationContext(), ContractorProfile.class));
                        else if (type.equalsIgnoreCase("consumer")) {
                            ConsumerProfile consumer = new ConsumerProfile();
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

