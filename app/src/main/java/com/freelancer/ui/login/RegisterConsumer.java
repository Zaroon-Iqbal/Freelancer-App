package com.freelancer.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.freelancer.R;

public class RegisterConsumer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_consumer);
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Bye", Toast.LENGTH_SHORT);
        toast.show();
    }
}