package com.freelancer.calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.freelancer.databinding.ActivityCalendarBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.freelancer.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CalendarActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCalendarBinding binding;

    private CalendarView calendar;
    private String selectedDate;
    private TextView appointment;

    private String y;

    private DatabaseReference appointmentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_calendar);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        calendar = findViewById(R.id.calendarView);
        appointment = findViewById(R.id.data1);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                y = Integer.toString(year);

                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(day);//month starts from 0 so added 1

                dateClicked();
            }
        });

        appointmentData = FirebaseDatabase.getInstance().getReference().child("Appointments");



    }
    /*
    This method will be used for showcasing the correct appointment data

     */
    private void dateClicked()
    {
     appointmentData.child(selectedDate).addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             if (snapshot.getValue() != null){
                 appointment.setText(snapshot.getValue().toString());
             }
             else {
                 appointment.setText("null");
             }

         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });
    }

    /*
    this is how data will be stored into the real time database from a button that is clicked for
    creating the appointment
     */
    public void saveAppointment(View view) {
        String name = appointment.getText().toString();//testing storage of a string
        appointmentData.child("01").setValue(name);//for some reason the selectedDate can't be used
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_calendar);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}