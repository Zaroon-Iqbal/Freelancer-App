package com.freelancer.ui.registration;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

        ActivityContractorRegistrationBinding binding =
                ActivityContractorRegistrationBinding.inflate(getLayoutInflater());
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

        binding.contractorCreateAccount.setOnClickListener(view -> {
            EditText contractorEmailField = binding.contractorEmailAddress;
            String email = contractorEmailField.getText().toString();

            EditText contractorPasswordField = binding.contractorPassword;
            String password = contractorPasswordField.getText().toString();

            EditText contractorConfirmPasswordField = binding.contractorConfirmPassword;
            String passwordConfirmation = contractorConfirmPasswordField.getText().toString();

            if(Validator.isEmailValid(email) &&
                    Validator.validFormPassword(password, passwordConfirmation)) {
                viewModel.register(email, password);
            } else {
                Toast.makeText(
                        getApplicationContext(), email + ":" + password,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}