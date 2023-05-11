package com.freelancer.data.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.freelancer.R;
import com.freelancer.data.viewmodel.CalendarViewModel;
import com.freelancer.databinding.ActivityCalendarBinding;
import com.freelancer.placeholder.FavoriteActivityPlaceholder;
import com.freelancer.placeholder.MessageActivityPlaceholder;
import com.freelancer.placeholder.ProfileActivityPlaceholder;
import com.freelancer.ui.login.HomePage;
import com.freelancer.ui.profile.ConsumerProfile;
import com.freelancer.ui.profile.ContractorProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

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
    private String type = "";

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("UsersExample");

        DocumentReference consumer = collectionReference.document("ConsumersExample").collection("ConsumerData").document(user.getUid());
        DocumentReference contractor = collectionReference.document("ContractorsExample").collection("ContractorData").document(user.getUid());

        consumer.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists())
                    type = "consumer";
            }
        });

        contractor.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists())
                    type = "contractor";
            }
        });

        //The bottom navigation bar. Added by Edward Kuoch.
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.CalendarNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    //When user clicks on the home icon
                    case R.id.HomeNav:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0,0);
                        return true;

                    //When user clicks on the calendar icon
                    case R.id.CalendarNav:
                        return true;

                    //When user clicks on the favorites icon
                    case R.id.FavoriteNav:
                        startActivity(new Intent(getApplicationContext(), FavoriteActivityPlaceholder.class));
                        overridePendingTransition(0,0);
                        return true;

                    //When user clicks on the message icon
                    case R.id.MessageNav:
                        startActivity(new Intent(getApplicationContext(), MessageActivityPlaceholder.class));
                        overridePendingTransition(0,0);
                        return true;

                    //When user clicks on the profile icon
                    case R.id.ProfileNav:
                        if(type.equalsIgnoreCase("contractor"))
                            startActivity(new Intent(getApplicationContext(), ContractorProfile.class));
                        else if (type.equalsIgnoreCase("consumer")) {
                            ConsumerProfile consumer = new ConsumerProfile();
                            consumer.show(getSupportFragmentManager(),"Consumer Profile");
                        }
                        else {
                            startActivity(new Intent(getApplicationContext(), ProfileActivityPlaceholder.class));
                            overridePendingTransition(0, 0);
                        }
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_calendar);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}