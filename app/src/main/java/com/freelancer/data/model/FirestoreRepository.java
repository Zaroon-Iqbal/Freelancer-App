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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private final FirebaseFirestore db;
    private DatabaseReference appointmentData;
    private Application application;
    private static final String TAG = "firestore";


    public FirestoreRepository(Application application) {
        db = FirebaseFirestore.getInstance();
        this.application = application;
    }


    /**
     * Calling this method attempts to add a new booking object to the Firestore DB.
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

        db.collection("appointments")
                .add(appointmentData)// Storing the appointment hashmap of data int he database.
                //Checking to verify if the data was correctly stored or not
                .addOnSuccessListener(documentReference -> Toast.makeText(application.getApplicationContext(), "Success", Toast.LENGTH_LONG).show())
                .addOnFailureListener(e -> Toast.makeText(application.getApplicationContext()  ,"Fail", Toast.LENGTH_LONG).show());

    }

    /**NEW addition function for storing job listing of contractors. This method is used to store the
     * job listing information in firebase firestore
     * @param Jtitle , the job title
     * @param Jdescription, description of service
     * @param Jphone, contact phone number
     * @param Jcity, city where service will be provided
     * @param Jprice, base price of the service
     * @param category, category that this service fits into,
     * @param loc, radius of which the service can cover
     * @param type, job location type
     * Contributors: Zaroon Iqbal
     */
    public void createJobListing(String Jtitle, String Jdescription, String Jphone, String Jcity, String Jprice, String category, String loc, String type)
    {
        Map<String, Object> jobListing = new HashMap<>();//Hashmap used to store key value pairs

        jobListing.put("title", Jtitle);//storing appropriate data under the correct header
        jobListing.put("description", Jdescription);
        jobListing.put("city", Jcity);
        jobListing.put("price", Jprice);
        jobListing.put("category", category);
        jobListing.put("radius", loc);
        jobListing.put("locationType", type);

        db.collection("jobListings")
                .add(jobListing)// Storing the appointment hashmap of data int he database.
                //Checking to verify if the data was correctly stored or not
                .addOnSuccessListener(documentReference -> Toast.makeText(application.getApplicationContext(), "Success", Toast.LENGTH_LONG).show())
                .addOnFailureListener(e -> Toast.makeText(application.getApplicationContext()  ,"Fail", Toast.LENGTH_LONG).show());

    }

    /**
     * used to store the users who create an account into the firestore database
     * Currently it is only storing Contractors because it s being called from the
     * contractors registration page
     * @param name
     * @param uuid
     * @param email
     */
    public void createUsersListing(String name, String uuid, String email)
    {
        Map<String, Object> jobListing = new HashMap<>();//Hashmap used to store key value pairs

        jobListing.put("name", name);//storing appropriate data under the correct header
        jobListing.put("uid",uuid) ;
        jobListing.put("email", email);
        db.collection("userListings")
                .add(jobListing)// Storing the appointment hashmap of data int he database.
                //Checking to verify if the data was correctly stored or not
                .addOnSuccessListener(documentReference -> Toast.makeText(application.getApplicationContext(), "Success", Toast.LENGTH_LONG).show())
                .addOnFailureListener(e -> Toast.makeText(application.getApplicationContext()  ,"Fail", Toast.LENGTH_LONG).show());

    }



    /**
     * This Method is used to retrieve data from the firestore database.
     * Notice that currently these specified collection, document, and field elements are hardcoded in
     * for testing and a method of finding out which ones are needed will be implemented in the future.
     * @param collection, The specified collection of the firestore
     * @param document, The specified document of that colleciton
     * @param field, one of the three fields that are needed from: start, end , title
     */
    public void retrieveAppointment(String collection, String document, String field)
    {
        DocumentReference doc = db
                .collection(collection)
                .document(document);
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
