package com.freelancer.calendar;

import android.os.Bundle;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
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
 * Contributors: Spencer Carlson, Zaroon Iqbal
 */
public class CalendarActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private CalendarViewModel viewModel;

    private String calendarCollection;//used for specified collection in firestore database

    private String calendarDocument;//used for specified document in firestore database

    private String calendarField;//used for the specifed field desired in the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.freelancer.databinding.ActivityCalendarBinding binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new CalendarViewModel(this.getApplication());

        /*
        These are test values for retrieving a specific field from the firestoreDatabase,
        A method for determining which specific is needed will be added in the future.
         */
        calendarCollection = "appointment";
        calendarDocument = "1KTxxZdPa1LnIopJBBb1";
        calendarField = "title";

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_calendar);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        CalendarView calendar = findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener((calendarView, year, month, day) -> {
            viewModel.createAppointment();
            viewModel.retrieveAppointment(calendarCollection, calendarDocument, calendarField);//used to test retrieval method.
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_calendar);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}