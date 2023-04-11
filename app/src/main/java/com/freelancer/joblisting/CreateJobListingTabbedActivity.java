package com.freelancer.joblisting;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.freelancer.data.viewmodel.JobInfoViewModel;
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
        JobInfoViewModel jobInfoViewModel = new ViewModelProvider(this).get(JobInfoViewModel.class);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    jobInfoViewModel.isModelUpdateNeeded().setValue(true);
                    Log.i("Focus change", "Job listing model: " + jobInfoViewModel.getJobListingModel().toString());
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }
}