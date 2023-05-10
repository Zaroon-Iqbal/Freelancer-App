package com.freelancer.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;
import com.freelancer.TestingActivity;
import com.freelancer.calendar.CalendarActivity;
import com.freelancer.placeholder.FavoriteActivityPlaceholder;
import com.freelancer.placeholder.MessageActivityPlaceholder;
import com.freelancer.placeholder.ProfileActivityPlaceholder;
import com.freelancer.ui.Checklist;
import com.freelancer.ui.bidding.BiddingContractorMain;
import com.freelancer.ui.bidding.Customer.CustomerBidMain;
import com.freelancer.ui.profile.ConsumerProfile;
import com.freelancer.ui.profile.ContractorProfile;
import com.freelancer.ui.profile.EditContractorProfile;
import com.freelancer.ui.profile.portfolio.EditPortfolio;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class HomePage extends AppCompatActivity {
    private static int count = 0;
    private static Toast toast;
    String type;
    String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //The bottom navigation bar

        ImageView imageView = findViewById(R.id.freelancerlogo2);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("userListings");
        collectionReference.whereEqualTo("uid",user.getUid()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if(!queryDocumentSnapshots.isEmpty()){
                for(DocumentSnapshot document: queryDocumentSnapshots.getDocuments()){
                    type = document.getString("type");
                    documentId  = document.getId();
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
                        if(type.equalsIgnoreCase("contractor")) {
                            Intent intent = new Intent(getApplicationContext(), ContractorProfile.class);
                            intent.putExtra("documentId",documentId);
                            startActivity(intent);
                        }
                        else if (type.equalsIgnoreCase("consumer")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("documentId",documentId);
                            ConsumerProfile consumer = new ConsumerProfile();
                            consumer.setArguments(bundle);
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