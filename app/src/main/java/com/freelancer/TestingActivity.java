package com.freelancer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.freelancer.data.model.FirestoreRepository;
import com.freelancer.joblisting.CreateJobListingTabbedActivity;
import com.freelancer.ui.login.HomePage;
import com.freelancer.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/*
This activity was creating mainly for testing database funcitonality
Contributors: Zaroon Iqbal, Spencer Carlson
 */
public class TestingActivity extends AppCompatActivity {
    private FirebaseFirestore firestoreDatabase;

    private Button testButton;
    private Button newListing;
    private EditText testData;

    private String data;
    private String calDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        //git test
        firestoreDatabase = FirebaseFirestore.getInstance();
        testData = findViewById(R.id.testData);//get desired inputs from text box
        testButton = findViewById(R.id.testButton);
        newListing = findViewById(R.id.newListing);

        calDate = "01";

        newListing.setOnClickListener(click -> {
            Intent intent = new Intent(this, CreateJobListingTabbedActivity.class);
            startActivity(intent);
        });

        //when the save button is clicked
        testButton.setOnClickListener(view -> {

            Map<String, Object> calendar = new HashMap<>();
            data = testData.getText().toString();//turn the edittext data into a string
            calendar.put("02", data);//storing hashmap
            /*
            This method of storing to database works correctly
             */
            firestoreDatabase.collection("appointments")
                            .add(calendar)//adding data to the appointments collection of database
                                    . addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        //Check if the data was successfully stored or not
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(TestingActivity.this, "The Data was stored", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TestingActivity.this, "The Data was not stored:" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
         //   fdb.saveAppointment(calDate, data);  // not sure why getting a null object referenc error when trying to use this
        });
    }
}