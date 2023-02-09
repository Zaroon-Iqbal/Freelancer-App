package com.freelancer.data.model;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;
import com.freelancer.TestingActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import android.app.Application;

public class FirestoreDatabase  {

    private FirebaseFirestore firestoreDatabase;
    private Application application;

    public void saveAppointment(String date, String service){
        firestoreDatabase = FirebaseFirestore.getInstance();
        Map<String, Object> appointmentData = new HashMap<>();
       Toast.makeText(application.getApplicationContext() , date, Toast.LENGTH_LONG).show();

        appointmentData.put(date, service);

        firestoreDatabase.collection("Appoint")
                .add(appointmentData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(application.getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(application.getApplicationContext()  ,"Fail", Toast.LENGTH_LONG).show();

                    }
                });

    }


}
