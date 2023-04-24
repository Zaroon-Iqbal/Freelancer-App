package com.freelancer.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.freelancer.databinding.ActivityEditContractorBinding;

public class EditContractorProfile extends AppCompatActivity {
    @Override
    public void onCreate(@NonNull Bundle savedInstance) {
        super.onCreate(savedInstance);
        ActivityEditContractorBinding binding = ActivityEditContractorBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
    }
}
