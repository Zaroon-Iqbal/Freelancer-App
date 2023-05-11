package com.freelancer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.freelancer.databinding.ActivityBannerAdBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;

public class BannerAdActivity extends AppCompatActivity {
    ActivityBannerAdBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBannerAdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // setContentView(R.layout.activity_banner_ad);

        loadBannerAd();

    }

    private void loadBannerAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        binding.adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.i("Admob", "Error : " + loadAdError);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.i("Admob", "adLoaded ");
            }
        });
    }
}