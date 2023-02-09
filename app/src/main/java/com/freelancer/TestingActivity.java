package com.freelancer;

import androidx.appcompat.app.AppCompatActivity;
import com.freelancer.data.model.FirestoreDatabase;
import com.google.firebase.firestore.FirebaseFirestore;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TestingActivity extends AppCompatActivity {

    private FirestoreDatabase fdb;

    private Button testButton;

    private EditText testData;

    private FirebaseFirestore firestoreDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        //git test

        testData = findViewById(R.id.testData);
        testButton = findViewById(R.id.testButton);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fdb.saveAppointment("01", testData);
            }
        });


    }
}