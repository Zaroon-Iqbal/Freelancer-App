package com.freelancer.ui.registration;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.data.validation.ErroneousField;
import com.freelancer.data.validation.ValidationError;
import com.freelancer.data.validation.Validator;
import com.freelancer.data.viewmodel.RegisterContractorViewModel;
import com.freelancer.databinding.ActivityContractorRegistrationBinding;
import com.freelancer.ui.registration.result.AuthFailure;
import com.freelancer.ui.registration.result.AuthSuccess;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.Set;

/**
 * Initializes the contractor registration activity.
 */
public class ContractorRegistrationActivity extends AppCompatActivity {
    private EditText contractorEmailField;
    private EditText contractorPasswordField;
    private EditText contractorConfirmPasswordField;

    private EditText firstNameField;
    private EditText lastNameField;
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
        firstNameField = binding.contractorFirstName;
        lastNameField = binding.contractorLastName;

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
                AuthFailure authFailure = (AuthFailure) authResult;
                ErroneousField field = authFailure.getAuthErrorMessage().getField();
                String errorMessage = authFailure.getAuthErrorMessage().getErrorMessage();

                switch (field) {
                    case EMAIL:
                        emailLayout.setErrorEnabled(true);
                        emailLayout.setError(errorMessage);
                        break;

                    case PASSWORD:
                        passwordLayout.setErrorEnabled(true);
                        passwordLayout.setError(errorMessage);
                        confirmPasswordLayout.setErrorEnabled(true);
                        confirmPasswordLayout.setError(errorMessage);
                        break;
                }
            }
        });

        binding.contractorCreateAccount.setOnClickListener(view -> {
            String firstName = firstNameField.getText().toString();
            String lastName = lastNameField.getText().toString();
            String email = contractorEmailField.getText().toString();
            String password = contractorPasswordField.getText().toString();
            String passwordConfirmation = contractorConfirmPasswordField.getText().toString();

            Set<ValidationError> validationErrors =
                    viewModel.getValidationErrors(firstName, lastName, email, password, passwordConfirmation);

            emailLayout.setErrorEnabled(false);
            firstNameLayout.setErrorEnabled(false);
            lastNameLayout.setErrorEnabled(false);
            passwordLayout.setErrorEnabled(false);
            confirmPasswordLayout.setErrorEnabled(false);

            for (ValidationError error : validationErrors) {
                switch (error.getField()) {
                    case EMAIL:
                        emailLayout.setErrorEnabled(true);
                        emailLayout.setError(error.getErrorMessage());
                        break;
                    case PASSWORD:
                    case PASSWORD_CONFIRM:
                        passwordLayout.setErrorEnabled(true);
                        passwordLayout.setError(error.getErrorMessage());
                        confirmPasswordLayout.setErrorEnabled(true);
                        confirmPasswordLayout.setError(error.getErrorMessage());
                        break;
                    case LAST_NAME:
                        lastNameLayout.setErrorEnabled(true);
                        lastNameLayout.setError(error.getErrorMessage());
                        break;
                    case FIRST_NAME:
                        firstNameLayout.setErrorEnabled(true);
                        firstNameLayout.setError(error.getErrorMessage());
                        break;
                }
            }

            if(validationErrors.size() == 0) {
                viewModel.register(email, password);
            }
        });
    }
}