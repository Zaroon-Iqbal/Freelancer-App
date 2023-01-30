package com.freelancer.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.databinding.ActivityRegisterConsumerBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterConsumer extends AppCompatActivity {

    private ActivityRegisterConsumerBinding binding;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterConsumerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();

        final Button createAccount = binding.registerButton;
        final TextView name = binding.entirename;
        final TextView email = binding.consumerEmail;
        final TextView password = binding.userPassword;
        final TextView confirmPass = binding.confirmPass;
        final TextView haveAccount = binding.Account;
        /*
        Goes back to logging in if the consumer already has an account/
         */
        haveAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(RegisterConsumer.this, LoginActivity.class));
            }
        });

        createAccount.setOnClickListener(view -> {
            if(!validFormPassword(password.getText().toString(), confirmPass.getText().toString())) {
                showToast("The passwords don't meet the minimum requirements.");
            } else {
                firebaseAuth
                        .createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                showToast("Sign up successful!");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                //TODO: Login successful. Go to the homepage.
                            } else {
                                showToast(task.getException().getMessage());
                                //TODO: Login unsuccessful. Inform user of error.
                            }
                        });
            }
        });
    }

    private boolean validFormPassword(String firstPasswordText, String confirmPasswordText) {
        if(firstPasswordText == null || confirmPasswordText == null) {
            return false;
        }
        return (firstPasswordText.equals(confirmPasswordText) && firstPasswordText.length() > 5);
    }

    // A placeholder username validation check
    private boolean isEmailValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
    }
}