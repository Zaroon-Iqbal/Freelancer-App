package com.freelancer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.freelancer.data.model.FirestoreDatabase;
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

public class TestingActivity extends AppCompatActivity {

    private FirestoreDatabase fdb;

    private Button testButton;

    private EditText testData;

    private FirebaseFirestore firestoreDatabase;

    private String data;

    private String calDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        //git test
        firestoreDatabase = FirebaseFirestore.getInstance();
        testData = findViewById(R.id.testData);
        testButton = findViewById(R.id.testButton);
        calDate = "01";

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> calendar = new HashMap<>();
                data = testData.getText().toString();
                calendar.put("01", data);
                /*
                This method of storing to database works correctly
                 */
                firestoreDatabase.collection("appointments")
                                .add(calendar)
                                        . addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(TestingActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(TestingActivity.this, "Fail" + e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


             //   fdb.saveAppointment(calDate, data);  // not sure why getting a null object referenc error when trying to use this
            }
        });


    }
}