package com.freelancer.placeholder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;
import com.freelancer.data.calendar.CalendarActivity;
import com.freelancer.ui.bidding.BiddingContractorMain;
import com.freelancer.ui.bidding.Customer.CustomerBidMain;
import com.freelancer.ui.login.HomePage;
import com.freelancer.ui.profile.ConsumerProfile;
import com.freelancer.ui.profile.ContractorProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FavoriteActivityPlaceholder extends AppCompatActivity {
    private String type = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_placeholder);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.FavoriteNav);
        Button customerButton = findViewById(R.id.testCustomer);
        Button contractorButton = findViewById(R.id.testContractor);

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
        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoriteActivityPlaceholder.this, CustomerBidMain.class));
            }
        });
        contractorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoriteActivityPlaceholder.this, BiddingContractorMain.class));
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    //When user clicks on the home icon
                    case R.id.HomeNav:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0,0);
                        return true;

                    //When user clicks on the calendar icon
                    case R.id.CalendarNav:
                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    //When user clicks on the favorites icon
                    case R.id.FavoriteNav:
                        return true;

                    //When user clicks on the message icon
                    case R.id.MessageNav:
                        startActivity(new Intent(getApplicationContext(), MessageActivityPlaceholder.class));
                        overridePendingTransition(0,0);
                        return true;

                    //When user clicks on the profile icon
                    case R.id.ProfileNav:
                        if(type.equalsIgnoreCase("contractor"))
                            startActivity(new Intent(getApplicationContext(), ContractorProfile.class));
                        else if (type.equalsIgnoreCase("consumer")) {
                            ConsumerProfile consumer = new ConsumerProfile();
                            consumer.show(getSupportFragmentManager(),"Consumer Profile");
                        }
                        else {
                            startActivity(new Intent(getApplicationContext(), ProfileActivityPlaceholder.class));
                            overridePendingTransition(0, 0);
                        }
                        return true;
                }
                return false;
            }
        });
    }
}