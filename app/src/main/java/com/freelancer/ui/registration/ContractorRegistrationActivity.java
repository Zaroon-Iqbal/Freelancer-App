package com.freelancer.ui.registration;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.R;
import com.freelancer.data.validation.Validator;
import com.freelancer.data.viewmodel.RegisterContractorViewModel;
import com.freelancer.databinding.ActivityContractorRegistrationBinding;

/**
 * Initializes the contractor registration activity.
 */
public class ContractorRegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityContractorRegistrationBinding binding = ActivityContractorRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RegisterContractorViewModel viewModel =
                new ViewModelProvider(this).get(RegisterContractorViewModel.class);

        viewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                Toast.makeText(getApplicationContext(), "Account registration complete", Toast.LENGTH_LONG).show();

            }
        });

        String email = ((EditText)findViewById(R.id.contractor_email_address)).getText().toString();
        String password = ((EditText)findViewById(R.id.contractor_password)).getText().toString();
        String passwordConfirmation = ((EditText)findViewById(R.id.contractor_confirm_password)).getText().toString();

        if(Validator.isEmailValid(email) &&
        Validator.validFormPassword(password, passwordConfirmation)) {
            binding.contractorCreateAccount.setOnClickListener(view ->
                    viewModel.register(email, password));
        } else {
            Toast.makeText(getApplicationContext(), "Invalid form.", Toast.LENGTH_SHORT).show();
        }
    }
}