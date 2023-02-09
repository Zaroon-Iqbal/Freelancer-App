package com.freelancer.data.model;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.app.Application;

import androidx.annotation.NonNull;

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
