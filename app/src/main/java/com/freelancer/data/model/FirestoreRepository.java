package com.freelancer.data.model;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * The FirestoreRepository is responsible for sending and fetching data from the Firestore Database.
 *
 * The ViewModel uses this data (and other data) to update the view (the activity).
 *
 * The Model-View-ViewModel pattern is visualized here: https://i.stack.imgur.com/3uL9i.png
 */
public class FirestoreRepository {

    private FirebaseFirestore firestoreDatabase;
    private DatabaseReference appointmentData;
    private Application application;

    public FirestoreRepository(Application application) {
        firestoreDatabase = FirebaseFirestore.getInstance();
        this.application = application;
    }


    /**
     *   this is how data will be stored into the real time database from a button that is clicked for
     *     creating the appointment
     */
    public void saveAppointment(String title, Date start, Date end) {
        Map<String, Object> appointmentData = new HashMap<>();

        appointmentData.put("title", title);
        appointmentData.put("start", start);
        appointmentData.put("end", end);

        firestoreDatabase.collection("appointment")
                .add(appointmentData)
                .addOnSuccessListener(documentReference -> Toast.makeText(application.getApplicationContext(), "Success", Toast.LENGTH_LONG).show())
                .addOnFailureListener(e -> Toast.makeText(application.getApplicationContext()  ,"Fail", Toast.LENGTH_LONG).show());

    }

    /**
     * This Method is used to retrieve data from the firestore database.
     * Notice that currently these specified collection, document, and field elements are hardcoded in
     * for testing and a method of finding out which ones are needed will be implemented in the future.
    @param collection, The specified collection of the firestore
    @param document, The specified document of that colleciton
    @param field, one of the three fields that are needed from: start, end , title
     */
    public void retrieveAppointment(String collection, String document, String field )
    {
        DocumentReference doc = firestoreDatabase.collection(collection). document(document);
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    Toast.makeText(application.getApplicationContext()  ,documentSnapshot.getString(field), Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application.getApplicationContext()  ,"fail", Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * This method will be used for showcasing the correct appointment data
     *
     * (This calls methods for the Realtime Database, not the Firestore Database)
     */
    private void dateClicked() {
        String selectedDate = "";
        appointmentData.child(selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    //appointment.setText(snapshot.getValue().toString());
                } else {
                    //appointment.setText("null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
