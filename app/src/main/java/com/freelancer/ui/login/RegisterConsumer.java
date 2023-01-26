package com.example.freelancer.ui.login;





import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.freelancer.R;


public class RegisterConsumer extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Bye", Toast.LENGTH_SHORT);
        toast.show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_consumer);
        toast.show();
    }
}