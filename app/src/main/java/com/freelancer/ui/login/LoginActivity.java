package com.freelancer.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;
import com.freelancer.TestingActivity;
import com.freelancer.data.viewmodel.LoginViewModel;
import com.freelancer.databinding.ActivityLoginBinding;
import com.freelancer.ui.ChatMessaging.UserModel;
import com.freelancer.ui.bottom_nav.BottomNav;
import com.freelancer.ui.registration.ContractorRegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static Toast toast;
    private static int count = 0;
    private LoginViewModel loginViewModel;
    private EditText email;
    private EditText password;
    private static final String TAG = "firestore";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        loginViewModel = new LoginViewModel(getApplication());

        loginViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                startActivity(new Intent(LoginActivity.this, BottomNav.class));
            }
        });

        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        email = binding.username;
        password = binding.password;

        Button signIn = binding.login;
        signIn.setOnClickListener(this);

        Button consumerRegistration = findViewById(R.id.register);
        consumerRegistration.setOnClickListener(this);

        Button contractorRegistration = findViewById(R.id.register_contractor);
        contractorRegistration.setOnClickListener(this);


        //This will be used for testing purposes of the database/application
        TextView test = findViewById(R.id.testView);
        test.setOnClickListener(view -> {
            count++;
            if (toast != null) {
                toast.cancel();
            }
            if (count == 3) { //by clicking on the freelancer name 5 times you will be navigated
                count = 0;
                startActivity(new Intent(LoginActivity.this, TestingActivity.class));
                toast = Toast.makeText(getApplicationContext(), "Welcome to the secret menu \uD83D\uDE0E \uD83D\uDD25", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            toast = Toast.makeText(getApplicationContext(), "Only " + (3 - count) + " more taps...", Toast.LENGTH_SHORT);
            toast.show();
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_contractor:
                startActivity(new Intent(LoginActivity.this, ContractorRegistrationActivity.class));
                break;

            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterConsumer.class));
                break;

            case R.id.login:
                loginViewModel.login(email.getText().toString(), password.getText().toString());
                break;

            default:
                break;
        }
    }
}