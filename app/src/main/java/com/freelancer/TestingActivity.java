package com.freelancer;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.freelancer.calendar.CalendarActivity;
import com.freelancer.joblisting.CreateJobListingTabbedActivity;
import com.freelancer.joblisting.creation.custom.FieldFormActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/*
This activity was creating mainly for testing database funcitonality
Contributors: Zaroon Iqbal, Spencer Carlson
 */
public class TestingActivity extends AppCompatActivity implements View.OnClickListener {
    private static int count = 0;
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


        Button notificationButton = findViewById(R.id.notification_button);


        notificationButton.setOnClickListener(this);
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
                Intent customFieldIntent = new Intent(this, FieldFormActivity.class);
                startActivity(customFieldIntent);
                break;

            case R.id.notification_button:
                // Create the NotificationChannel, but only on API 26+ because
                // the NotificationChannel class is new and not in the support library
                CharSequence name = "Hello";
                String description = "Description";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel("freelancer channel", name, importance);
                channel.setDescription(description);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "freelancer channel")
                        .setSmallIcon(R.drawable.user_icon)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        //.setContentIntent()
                        .setAutoCancel(true);

                // notificationId is a unique int for each notification that you must define

                notificationManager.notify(count++, builder.build());
                break;
        }
    }
}