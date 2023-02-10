package com.freelancer.calendar;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.freelancer.R;
import com.freelancer.data.viewmodel.CalendarViewModel;
import com.freelancer.databinding.ActivityCalendarBinding;

import java.time.Instant;
import java.util.Date;

/**
 * The CalendarActivity renders a calendar view, and upcoming appointments for the contractor or
 * the consumer.
 */
public class CalendarActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private CalendarViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.freelancer.databinding.ActivityCalendarBinding binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new CalendarViewModel(this.getApplication());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_calendar);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        CalendarView calendar = findViewById(R.id.calendarView);
        TextView appointment = findViewById(R.id.data1);

        calendar.setOnDateChangeListener((calendarView, year, month, day) -> {
            viewModel.createAppointment("Hello", Date.from(Instant.now()), Date.from(Instant.now()));
            //viewModel.retrieveAppointment("item");used to test retrieval method.
        });

        //appointmentData = FirebaseDatabase.getInstance().getReference().child("Appointments");
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_calendar);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}