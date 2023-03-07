package com.freelancer.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.freelancer.R;
import com.freelancer.ui.profile.ConsumerProfile;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageView image = findViewById(R.id.freelancerlogo2);

        image.setOnClickListener(v -> {
            ConsumerProfile consumer = new ConsumerProfile();
            consumer.show(getSupportFragmentManager(),"ConsumerProfile");
        });
    }
}