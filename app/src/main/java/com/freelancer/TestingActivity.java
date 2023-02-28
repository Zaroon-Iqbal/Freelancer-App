package com.freelancer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.calendar.CalendarActivity;
import com.freelancer.joblisting.CreateJobListingTabbedActivity;
import com.freelancer.joblisting.creation.custom.CustomFieldForm;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/*
This activity was creating mainly for testing database funcitonality
Contributors: Zaroon Iqbal, Spencer Carlson
 */
public class TestingActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore firestoreDatabase;

    private EditText testData;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        //git test
        firestoreDatabase = FirebaseFirestore.getInstance();
        testData = findViewById(R.id.testData);//get desired inputs from text box
        Button testButton = findViewById(R.id.testButton);
        Button newListing = findViewById(R.id.newListing);
        Button calendar = findViewById(R.id.calendarButton);
        Button customField = findViewById(R.id.custom_field_button);


        calendar.setOnClickListener(this);
        newListing.setOnClickListener(this);
        customField.setOnClickListener(this);
        //when the save button is clicked
        testButton.setOnClickListener(view -> {

            Map<String, Object> calendarMap = new HashMap<>();
            data = testData.getText().toString();//turn the edittext data into a string
            calendarMap.put("02", data);//storing hashmap
            /*
            This method of storing to database works correctly
             */
            //Check if the data was successfully stored or not
            firestoreDatabase.collection("appointments")
                    .add(calendarMap) //adding data to the appointments collection of database
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(TestingActivity.this, "The Data was stored", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(TestingActivity.this, "The Data was not stored:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newListing:
                Intent listingIntent = new Intent(this, CreateJobListingTabbedActivity.class);
                startActivity(listingIntent);
                break;

            case R.id.calendarButton:
                Intent calendarIntent = new Intent(this, CalendarActivity.class);
                startActivity(calendarIntent);
                break;

            case R.id.custom_field_button:
                Intent customFieldIntent = new Intent(this, CustomFieldForm.class);
                startActivity(customFieldIntent);
                break;
        }
    }
}