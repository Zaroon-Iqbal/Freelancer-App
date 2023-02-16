package com.freelancer.ui.registration.result;

import com.freelancer.data.validation.ErroneousField;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.Arrays;

public class AuthFailure extends AuthResult {
    private final FirebaseAuthException exception;
    private final AuthErrorMessage authErrorMessage;

    public AuthFailure(FirebaseAuthException exception) {
        super(false);
        this.exception = exception;
        authErrorMessage = AuthErrorMessage.fromString(exception.getErrorCode());
    }

    public FirebaseAuthException getException() {
        return exception;
    }

    public AuthErrorMessage getAuthErrorMessage() {
        return authErrorMessage;
    }

    public enum AuthErrorMessage {
        ERROR_INVALID_EMAIL("The email address is invalid!", ErroneousField.EMAIL),
        ERROR_EMAIL_ALREADY_IN_USE("That email is already in use!", ErroneousField.EMAIL),
        ERROR_WEAK_PASSWORD("Your selected password is too weak!", ErroneousField.PASSWORD),
        DEFAULT("", ErroneousField.DEFAULT);

        private final String errorMessage;
        private final ErroneousField field;

        AuthErrorMessage(String errorMessage, ErroneousField field) {
            this.errorMessage = errorMessage;
            this.field = field;
        }

        static AuthErrorMessage fromString(String errorCode) {
            return Arrays.stream(values())
                    .filter(error -> error.name().equalsIgnoreCase(errorCode))
                    .findFirst()
                    .orElse(AuthErrorMessage.DEFAULT);
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public ErroneousField getField() {
            return field;
        }
    }
}
