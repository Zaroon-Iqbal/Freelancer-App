package com.freelancer.ui.registration.result;

public abstract class AuthResult {
    private boolean authenticated;

    public AuthResult(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
