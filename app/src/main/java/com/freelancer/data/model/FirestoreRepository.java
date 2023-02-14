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
 *
 * Contributors: Zaroon Iqbal, Spencer Carlson
 */
public class FirestoreRepository {

    private FirebaseFirestore firestoreDatabase;//Variable needed to get Firestore methods.
    private DatabaseReference appointmentData;//part of the firestore library
    private Application application;

    public FirestoreRepository(Application application) {
        firestoreDatabase = FirebaseFirestore.getInstance();
        this.application = application;
    }


    /**
     *   this is how data will be stored into the real time database from a button that is clicked for
     *     creating the appointment
     * @param title, title of the appointment
     * @param start, the appointment start time
     * @param end, the appointment end time
     * contributors: Zaroon Iqbal, Spencer Carlson
     */
    public void saveAppointment(String title, Date start, Date end) {
        Map<String, Object> appointmentData = new HashMap<>();//Hashmap used to store key value pairs

        appointmentData.put("title", title);//storing appropriate data under the correct header
        appointmentData.put("start", start);
        appointmentData.put("end", end);

        firestoreDatabase.collection("appointments")
                .add(appointmentData)// Storing the appointment hasmap of data int he database.
                //Checking to verify if the data was correctly stored or not
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
     *Contributor: Zaroon Iqbal
     */
    public void retrieveAppointment(String collection, String document, String field )
    {
        DocumentReference doc = firestoreDatabase.collection(collection).document(document);//getting the specifed collection and document
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) { //if successfully retrieved
                if(documentSnapshot.exists())
                {
                    //displayes the gathered data, will be displayed differently in the future
                    Toast.makeText(application.getApplicationContext()  ,documentSnapshot.getString(field), Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {//If the data wasnt able to be found
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
     *
     * Realtime database method testing that might not be used in the future.
     */
    private void dateClicked() {
        String selectedDate = "";
        appointmentData.child(selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
            //similar idea of testing is used, for storing when the date clicked has changed.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    //appointment.setText(snapshot.getValue().toString());
                } else {
                    //appointment.setText("empty");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
