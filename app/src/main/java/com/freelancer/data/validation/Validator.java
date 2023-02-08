package com.freelancer.data.validation;

import android.util.Patterns;

/**
 * The Validator class contains static methods for validating fields.
 */
public class Validator {
    public static boolean validFormPassword(String firstPasswordText, String confirmPasswordText) {
        if(firstPasswordText == null || confirmPasswordText == null) {
            return false;
        }
        return (firstPasswordText.equals(confirmPasswordText) && firstPasswordText.length() > 5);
    }

    // A placeholder username validation check
    public static boolean isEmailValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }
}
