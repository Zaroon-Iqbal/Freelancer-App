package com.freelancer.data.model;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.freelancer.data.model.booking.BookingModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * The FirestoreRepository is responsible for sending and fetching data from the Firestore Database.
 *
 * The ViewModel uses this data (and other data) to update the view (the activity).
 *
 * The Model-View-ViewModel pattern is visualized here: https://i.stack.imgur.com/3uL9i.png
 */
public class FirestoreRepository {

    private final FirebaseFirestore db;
    private DatabaseReference appointmentData;
    private Application application;
    private static final String TAG = "firestore";


    public FirestoreRepository(Application application) {
        db = FirebaseFirestore.getInstance();
        this.application = application;

    }

    /**
     * This method attempts to add a new booking to the database.
     */
    public void addBooking() {
        BookingModel booking = BookingModel.createSampleBooking();
        db.collection("bookings")
                .add(booking)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(application.getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Successfully added booking to Firestore.");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(application.getApplicationContext()  ,"Fail", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Failed to add booking to Firestore. Error:\n" + e.getMessage());
                });
    }


    /**
     * This Method is used to retrieve data from the firestore database.
     * Notice that currently these specified collection, document, and field elements are hardcoded in
     * for testing and a method of finding out which ones are needed will be implemented in the future.
        @param collection, The specified collection of the firestore
        @param document, The specified document of that colleciton
        @param field, one of the three fields that are needed from: start, end , title
     */
    public void retrieveAppointment(String collection, String document, String field)
    {
        DocumentReference doc = db.collection(collection). document(document);
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
