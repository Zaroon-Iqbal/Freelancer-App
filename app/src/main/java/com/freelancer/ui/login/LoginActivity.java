package com.freelancer.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;
import com.freelancer.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            //TODO: User is authenticated already, log in and go to homepage.
        }

        final Button loginButton = binding.login;
        loginButton.setOnClickListener(this);

        final Button register = binding.register;
        if (register != null) {
            register.setOnClickListener(this);
        }
    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterConsumer.class));
                break;

            case R.id.login:
                firebaseAuth.signInWithEmailAndPassword(binding.username.getText().toString(), binding.password.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                showToast("Sign-in successful!");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                //TODO: Login successful. Go to the homepage.
                            } else {
                                showToast(task.getException().getMessage());
                                //TODO: Login unsuccessful. Inform user of error.
                            }
                        });
                break;
            default:
                break;
        }
    }
}