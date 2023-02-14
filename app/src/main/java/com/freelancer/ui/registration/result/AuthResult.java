package com.freelancer.ui.registration.result;

public abstract class AuthResult {
    private boolean authSuccessful;

    public AuthResult(boolean authSuccessful) {
        this.authSuccessful = authSuccessful;
    }
}
