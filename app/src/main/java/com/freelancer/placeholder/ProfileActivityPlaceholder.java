package com.freelancer.placeholder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;
import com.freelancer.calendar.CalendarActivity;
import com.freelancer.ui.login.HomePage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ProfileActivityPlaceholder extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_placeholder);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.ProfileNav);
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
                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    //When user clicks on the favorites icon
                    case R.id.FavoriteNav:
                        return true;

                    //When user clicks on the message icon
                    case R.id.MessageNav:
                        startActivity(new Intent(getApplicationContext(), MessageActivityPlaceholder.class));
                        overridePendingTransition(0,0);
                        return true;

                    //When user clicks on the profile icon
                    case R.id.ProfileNav:
                        startActivity(new Intent(getApplicationContext(), ProfileActivityPlaceholder.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
