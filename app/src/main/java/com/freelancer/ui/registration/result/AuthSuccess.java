package com.freelancer.ui.registration.result;


import com.google.firebase.auth.FirebaseUser;

public class AuthSuccess extends AuthResult {
    private FirebaseUser firebaseUser;

    public AuthSuccess(FirebaseUser firebaseUser) {
        super(true);
        this.firebaseUser = firebaseUser;
    }
}
