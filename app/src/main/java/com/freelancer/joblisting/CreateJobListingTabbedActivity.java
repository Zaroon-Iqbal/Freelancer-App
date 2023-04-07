package com.freelancer.joblisting;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.freelancer.databinding.ActivityCreateJobListingTabbedBinding;
import com.freelancer.joblisting.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class CreateJobListingTabbedActivity extends AppCompatActivity {

    private ActivityCreateJobListingTabbedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateJobListingTabbedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }
}