package com.freelancer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.freelancer.data.model.FirestoreRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


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

    private FirestoreRepository fdb;

    private Button testButton;

    private EditText testData;

    private FirebaseFirestore firestoreDatabase;

    private FirestoreRepository firestoreDatabaseModel;//used to get functions from our Firestore java class

    private String data;

    private String calDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        firestoreDatabaseModel = new FirestoreRepository(getApplication());
        //git test
        firestoreDatabase = FirebaseFirestore.getInstance();
        testData = findViewById(R.id.testData);//get desired inputs from text box
        testButton = findViewById(R.id.testButton);
        calDate = "01";

        //when the save button is clicked
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
            }
        });


    }
}