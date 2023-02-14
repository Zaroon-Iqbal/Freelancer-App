package com.freelancer.ui.registration.result;

import com.google.firebase.auth.FirebaseAuthException;

public class AuthFailure extends AuthResult {
    private FirebaseAuthException exception;

    public AuthFailure(FirebaseAuthException exception) {
        super(true);
        this.exception = exception;
    }

    public FirebaseAuthException getException() {
        return exception;
    }
}
