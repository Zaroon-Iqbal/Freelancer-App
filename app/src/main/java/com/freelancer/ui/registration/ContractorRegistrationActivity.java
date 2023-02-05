package com.freelancer.ui.registration;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.databinding.ActivityContractorRegistrationBinding;

public class ContractorRegistrationActivity extends AppCompatActivity {

    private ActivityContractorRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContractorRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}