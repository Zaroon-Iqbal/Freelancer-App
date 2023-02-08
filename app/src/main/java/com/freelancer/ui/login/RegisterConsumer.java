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

public class RegisterConsumer extends AppCompatActivity {

    private RegisterConsumerViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.freelancer.databinding.ActivityRegisterConsumerBinding binding = ActivityRegisterConsumerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(RegisterConsumerViewModel.class);

        viewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                showToast("Account registration complete.");
            } else {
                showToast("Couldn't find a Firebase user.");
            }
        });

        final Button createAccount = binding.registerButton;
        final TextView name = binding.entirename;
        final TextView email = binding.consumerEmail;
        final TextView password = binding.userPassword;
        final TextView confirmPass = binding.confirmPass;
        final TextView haveAccount = binding.Account;

        /*
            Goes back to logging in if the consumer already has an account/
         */
        haveAccount.setOnClickListener(v ->
                startActivity(new Intent(RegisterConsumer.this, LoginActivity.class)));

        createAccount.setOnClickListener(view ->
                viewModel.register(email.getText().toString(), password.getText().toString()));
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
    }
}