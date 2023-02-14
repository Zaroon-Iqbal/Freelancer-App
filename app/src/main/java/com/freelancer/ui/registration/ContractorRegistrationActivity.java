package com.freelancer.ui.registration;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.data.validation.Validator;
import com.freelancer.data.viewmodel.RegisterContractorViewModel;
import com.freelancer.databinding.ActivityContractorRegistrationBinding;
import com.freelancer.ui.registration.result.AuthFailure;
import com.freelancer.ui.registration.result.AuthSuccess;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthException;

/**
 * Initializes the contractor registration activity.
 */
public class ContractorRegistrationActivity extends AppCompatActivity {
    private EditText contractorEmailField;
    private EditText contractorPasswordField;
    private EditText contractorConfirmPasswordField;

    private TextInputLayout emailLayout;
    private TextInputLayout firstNameLayout;
    private TextInputLayout lastNameLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout confirmPasswordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityContractorRegistrationBinding binding =
                ActivityContractorRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailLayout = binding.contractorEmailLayout;
        firstNameLayout = binding.contractorFirstNameLayout;
        lastNameLayout = binding.contractorLastNameLayout;
        passwordLayout = binding.contractorPasswordLayout;
        confirmPasswordLayout = binding.contractorConfirmPasswordLayout;

        contractorEmailField = binding.contractorEmailAddress;
        contractorPasswordField = binding.contractorPassword;
        contractorConfirmPasswordField = binding.contractorConfirmPassword;

        RegisterContractorViewModel viewModel =
                new ViewModelProvider(this).get(RegisterContractorViewModel.class);

        viewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                Toast.makeText(getApplicationContext(), "Account registration complete", Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getAuthResultLiveData().observe(this, authResult -> {
            if(authResult instanceof AuthSuccess) {
                //@TODO: Do something.
            } else if(authResult instanceof AuthFailure) {
                FirebaseAuthException exception = ((AuthFailure) authResult).getException();
                switch (exception.getErrorCode()) {
                    case "ERROR_INVALID_EMAIL":
                        emailLayout.setErrorEnabled(true);
                        emailLayout.setError("That email is invalid!");
                        break;
                    case "ERROR_EMAIL_ALREADY_IN_USE":
                        emailLayout.setErrorEnabled(true);
                        emailLayout.setError("That email is already in use!");
                        break;
                    case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                        emailLayout.setErrorEnabled(true);
                        emailLayout.setError("Credentials already in use!");
                        break;
                    case "ERROR_OPERATION_NOT_ALLOWED":

                        break;
                    case "ERROR_WEAK_PASSWORD":
                        passwordLayout.setErrorEnabled(true);
                        passwordLayout.setError("The password you've entered is too weak!");
                        break;
                    case "ERROR_MISSING_EMAIL":
                        emailLayout.setErrorEnabled(true);
                        emailLayout.setError("The email address is missing!");
                        break;
                }
            } else {

            }
        });

        binding.contractorCreateAccount.setOnClickListener(view -> {
            String email = contractorEmailField.getText().toString();
            String password = contractorPasswordField.getText().toString();
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