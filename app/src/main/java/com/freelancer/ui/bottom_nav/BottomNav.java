package com.freelancer.ui.bottom_nav;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.freelancer.R;
import com.freelancer.TestingActivity;
import com.freelancer.ui.profile.ConsumerProfile;
import com.freelancer.ui.profile.ContractorProfile;
import com.freelancer.ui.profile.EditConsumerProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNav extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frameLayout);
        //Makes the first fragment the homepage
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new HomeFragment()).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            /*
            This allows the users to click on any icon on the bar and take them to
            the corresponding page
            */
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                frameLayout.setVisibility(View.VISIBLE);
                switch (item.getItemId()){
                    //When user clicks on the home icon
                    case R.id.HomeNav:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, new HomeFragment()).commit();
                        return true;
                    //When user clicks on the calendar icon
                    case R.id.CalendarNav:
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.frameLayout, new ConsumerProfile()).commit();
                        ConsumerProfile consumer = new ConsumerProfile();
                        consumer.show(getSupportFragmentManager(),"Consumer Profile");
                        return true;

                    //TODO Connect the rest of the fragments
                }
                return false;
            }
        });
    }
}
