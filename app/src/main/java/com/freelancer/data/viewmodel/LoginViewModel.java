package com.freelancer.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.freelancer.data.model.FirebaseAuthRepository;
import com.google.firebase.auth.FirebaseUser;

/**
 * The ViewModel for the LoginView handles
 */
public class LoginViewModel extends AndroidViewModel {
    private final FirebaseAuthRepository firebaseAuthRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        firebaseAuthRepository = new FirebaseAuthRepository(application);
    }

    public void login(String email, String password) {
        firebaseAuthRepository.login(email, password);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return firebaseAuthRepository.getFirebaseUserLiveData();
    }

    public boolean userLiveDataExists() {
        return getUserLiveData().getValue() != null
                && Boolean.FALSE.equals(firebaseAuthRepository.getLoggedOutLiveData().getValue());
    }
}