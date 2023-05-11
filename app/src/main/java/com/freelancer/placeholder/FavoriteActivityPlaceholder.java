package com.freelancer.placeholder;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;
import com.freelancer.calendar.CalendarActivity;
import com.freelancer.ui.bidding.BiddingContractorMain;
import com.freelancer.ui.bidding.Customer.CustomerBidMain;
import com.freelancer.ui.login.HomePage;
import com.freelancer.ui.profile.ConsumerProfile;
import com.freelancer.ui.profile.ContractorProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivityPlaceholder extends AppCompatActivity {
    private String type = "";
    private ListView listView1;
    private ArrayList<String> arr1;
    private ArrayAdapter<String> adapter1;
    private String temp = "";
    View v1;
    private ListView listView2;
    private ArrayList<String> arr2;
    private ArrayAdapter<String> adapter2;
    private String barber;
    private String mechanic;
    private String groomer;
    private int num1, num2, num3 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_placeholder);

        listView1 = findViewById(R.id.listView);
        //listView1.setVisibility(android.view.View.GONE);
        arr1 = new ArrayList<>();
        arr2 = new ArrayList<>();
        // readDocument(v1);
        barber = "Bob's Barbers" + "\n" + "Haircuts & Beard Trim" + "\t\t$" + "10" + "\t\t" + "15" + " mi";
        mechanic = "Mechanic Michael" + "\n" + "Performs care and maintenance of car" + "\n$" + "75" + "\t\t" + "25" + " mi";
        groomer = "Denny's Dog Grooming" + "\n" + "Dog Groomer" + "\t\t$" + "40" + "\t\t" + "20" + " mi";
        arr1.add(barber);
        arr1.add(mechanic);
        arr1.add(groomer);
        adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr1);
        listView1.setAdapter(adapter1);
        // listView1.setVisibility(android.view.View.VISIBLE);

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
                switch (item.getItemId()) {

                    //When user clicks on the home icon
                    case R.id.HomeNav:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0, 0);
                        return true;

                    //When user clicks on the calendar icon
                    case R.id.CalendarNav:
                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    //When user clicks on the favorites icon
                    case R.id.FavoriteNav:
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

    public void readDocument(View view) {
        FirebaseFirestore.getInstance()
                .collection("jobListings")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            temp = snapshot.getString("title") + "\n" + snapshot.getString("description") + "\t\t$" + snapshot.getDouble("basePrice") + "\t\t" + snapshot.getDouble("radius") + "mi";
                            Log.d(TAG, "onSuccess: " + temp);
                            arr1.add(temp);
                            Log.d(TAG, "onSuccess: " + arr1);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
    }

    public void click1(View view) {
        if (num1 % 2 == 0) {
            listView2 = findViewById(R.id.listVieww);
            //listView1.setVisibility(android.view.View.GONE);
            arr2.add(barber);
            adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr2);
            listView2.setAdapter(adapter2);
            // listView1.setVisibility(android.view.View.VISIBLE);
            Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            num1++;
        }
        else {
            arr2.remove(barber);
            adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr2);
            listView2.setAdapter(adapter2);
            // listView1.setVisibility(android.view.View.VISIBLE);
            Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            num1++;
        }

    }

    public void click2(View view) {
        if (num2 % 2 == 0) {
            listView2 = findViewById(R.id.listVieww);
            //listView1.setVisibility(android.view.View.GONE);
            arr2.add(mechanic);
            adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr2);
            listView2.setAdapter(adapter2);
            // listView1.setVisibility(android.view.View.VISIBLE);
            Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            num2++;
        }
        else {
            arr2.remove(mechanic);
            adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr2);
            listView2.setAdapter(adapter2);
            // listView1.setVisibility(android.view.View.VISIBLE);
            Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            num2++;
        }
    }

    public void click3(View view) {
        if (num3 % 2 == 0) {
            listView2 = findViewById(R.id.listVieww);
            //listView1.setVisibility(android.view.View.GONE);
            arr2.add(groomer);
            adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr2);
            listView2.setAdapter(adapter2);
            // listView1.setVisibility(android.view.View.VISIBLE);
            Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            num3++;
        }
        else {
            arr2.remove(groomer);
            adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr2);
            listView2.setAdapter(adapter2);
            // listView1.setVisibility(android.view.View.VISIBLE);
            Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            num3++;
        }
    }
}