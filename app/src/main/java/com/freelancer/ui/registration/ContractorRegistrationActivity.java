package com.freelancer.ui.registration;

import android.os.Bundle;

import com.freelancer.databinding.ActivityContractorRegistrationViewBinding;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.freelancer.R;

public class ContractorRegistrationActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityContractorRegistrationViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityContractorRegistrationViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
    }
}