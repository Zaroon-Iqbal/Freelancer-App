package com.freelancer.data.source;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseUserLiveData extends LiveData<FirebaseUser> {
    private final FirebaseAuth firebaseAuth;


    private final FirebaseAuth.AuthStateListener authStateListener = firebaseAuth -> {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                setValue(firebaseUser);
            };

    public FirebaseUserLiveData(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    protected void onActive() {
        super.onActive();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
