package com.freelancer.ui.profile;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.freelancer.R;
import com.freelancer.databinding.ActivityEnlargedImageBinding;

public class EnlargeImage extends AppCompatActivity {
    ImageView image;
    ImageView exit;

    String uri;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ActivityEnlargedImageBinding binding = ActivityEnlargedImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        image = binding.enlargeImage;
        exit = binding.close;
        exit.setOnClickListener(v -> finish());

        uri = getIntent().getStringExtra("URI");

        Glide.with(this).load(uri).fitCenter().into(image);
    }
}
