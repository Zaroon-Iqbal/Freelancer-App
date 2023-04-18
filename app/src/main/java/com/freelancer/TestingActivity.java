package com.freelancer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.calendar.CalendarActivity;
import com.freelancer.joblisting.CreateJobListingTabbedActivity;
import com.freelancer.joblisting.management.JobListingManagementActivity;
import com.google.firebase.firestore.FirebaseFirestore;

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
        Button newListing = findViewById(R.id.newListing);
        Button calendar = findViewById(R.id.calendarButton);
        Button management = findViewById(R.id.job_listing_management_button);
        Button review = findViewById(R.id.review_Button_test);


        calendar.setOnClickListener(this);
        newListing.setOnClickListener(this);
        management.setOnClickListener(this);
        review.setOnClickListener(this);
        //when the save button is clicked


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

            case R.id.job_listing_management_button:
                Intent jobManagementIntent = new Intent(this, JobListingManagementActivity.class);
                startActivity(jobManagementIntent);
                break;
            case R.id.review_Button_test:
                Intent reviewIntent = new Intent(this, ReviewActivity.class);
                startActivity(reviewIntent);
                break;

            /*case R.id.notification_button:
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
                break;*/
        }
    }
}