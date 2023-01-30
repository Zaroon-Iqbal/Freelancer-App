package com.example.freelancer.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/*
import com.example.freelancer.R;
import com.example.freelancer.ui.login.LoginViewModel;
import com.example.freelancer.ui.login.LoginViewModelFactory;
import com.example.freelancer.databinding.ActivityLoginBinding;
*/

import com.freelancer.R;
import com.freelancer.databinding.ActivityLoginBinding;
import com.freelancer.ui.login.LoginViewModel;
import com.freelancer.ui.login.RegisterConsumer;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private TextView register;
    private Button login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //setContentView(binding.getRoot());

        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        login = findViewById(R.id.login);
        login.setOnClickListener(this);


    }



    @Override
    public void onClick(View view) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Hello", Toast.LENGTH_SHORT);
        switch (view.getId()){
            case R.id.register:
                toast.show();
                startActivity(new Intent(LoginActivity.this, RegisterConsumer.class));
                break;

        }
    }
}