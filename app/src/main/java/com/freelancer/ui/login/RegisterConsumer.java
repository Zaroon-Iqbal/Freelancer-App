package com.freelancer.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.R;
import com.freelancer.data.viewmodel.RegisterConsumerViewModel;
import com.freelancer.databinding.ActivityRegisterConsumerBinding;
import com.freelancer.ui.registration.ContractorRegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Consumer registration page
 * Contributors: Spencer Carlson, Zaroon Iqbal
 */
public class RegisterConsumer extends AppCompatActivity {

    private RegisterConsumerViewModel viewModel;//used to connect to the firebaseAuth Repository
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.freelancer.databinding.ActivityRegisterConsumerBinding binding = ActivityRegisterConsumerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //variables of the registration page where the data is gathered from
        final Button createAccount = binding.registerButton;
        final TextView name = binding.entirename;
        final TextView email = binding.consumerEmail;
        final TextView password = binding.userPassword;
        final TextView confirmPass = binding.confirmPass;
        final TextView haveAccount = binding.Account;

        viewModel = new ViewModelProvider(this).get(RegisterConsumerViewModel.class);

        //Used to check if the registration was successful or not using the viewModel
        viewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                showToast("Account registration complete.");
            } else {
                showToast("Couldn't find a Firebase user.");
            }
        });

        /*
            Goes back to logging in if the consumer already has an account
            Used as a return to login page button.
        */
        haveAccount.setOnClickListener(v ->
                startActivity(new Intent(RegisterConsumer.this, LoginActivity.class)));

        //used to send the email and password to the firebaseAuthRepository to register
        createAccount.setOnClickListener(view ->
                viewModel.register(email.getText().toString(), password.getText().toString()));
    }

    /**
     Method used to easily make toast messages
     @param text, the message to be displayed
    */
    private void showToast(String text) {
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
    }
}