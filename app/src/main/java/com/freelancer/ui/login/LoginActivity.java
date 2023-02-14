package com.freelancer.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;
import com.freelancer.TestingActivity;
import com.freelancer.calendar.CalendarActivity;
import com.freelancer.data.viewmodel.LoginViewModel;
import com.freelancer.databinding.ActivityLoginBinding;
import com.freelancer.ui.bottom_nav.BottomNav;
import com.freelancer.ui.registration.ContractorRegistrationActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private LoginViewModel loginViewModel;

    private EditText email;
    private EditText password;

    static int count = 0;

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

        Button calendar = findViewById(R.id.go_to_calendar);
        calendar.setOnClickListener(this);

        //This will be used for testing purposes of the database/application
        TextView test = findViewById(R.id.testView);
        test.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                count++;
                if(count == 5)//by clicking on the freelancer name 5 times you will be navigated
                {
                    startActivity(new Intent(LoginActivity.this, TestingActivity.class));

                }
            }
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

            case R.id.go_to_calendar:
                startActivity(new Intent(LoginActivity.this, CalendarActivity.class));
                break;

            default:
                break;
        }
    }
}