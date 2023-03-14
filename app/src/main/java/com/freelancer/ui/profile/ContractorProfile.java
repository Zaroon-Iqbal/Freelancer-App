package com.freelancer.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;
import com.freelancer.databinding.ActivityContractorProfileBinding;
import com.freelancer.ui.profile.portfolio.PortfolioActivity;

public class ContractorProfile extends AppCompatActivity {
    Button message;
    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        ActivityContractorProfileBinding binding = ActivityContractorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        message = findViewById(R.id.messageButton);

        message.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), PortfolioActivity.class));
        });
    }
}
