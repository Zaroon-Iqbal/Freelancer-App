package com.freelancer.data.viewmodel;

import android.app.Application;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.freelancer.data.model.FirebaseAuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class RegisterContractorViewModel extends AndroidViewModel {
    private final FirebaseAuthRepository firebaseAuthRepository;

    public RegisterContractorViewModel(@NonNull Application application) {
        super(application);
        firebaseAuthRepository = new FirebaseAuthRepository(application);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return firebaseAuthRepository.getFirebaseUserLiveData();
    }

    public void register(String email, String password) {
        firebaseAuthRepository.register(email, password);
    }
}