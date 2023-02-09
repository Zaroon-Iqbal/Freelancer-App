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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import android.app.Application;

public class FirestoreDatabase  {

    private FirebaseFirestore firestoreDatabase;
    private Application application;

    public void saveAppointment(String date, EditText service){
        firestoreDatabase.getInstance();
        Map<String, Object> appointmentData = new HashMap<>();
       String data = service.getText().toString();
       Toast.makeText(application.getApplicationContext() , data, Toast.LENGTH_LONG).show();

        appointmentData.put("01", "test");

        firestoreDatabase.collection("Appointment")
                .document("TeST").set(appointmentData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(application.getApplicationContext()  ,"Success", Toast.LENGTH_LONG).show();

                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(application.getApplicationContext()  ,"Fail", Toast.LENGTH_LONG).show();

                    }
                });

    }


}
