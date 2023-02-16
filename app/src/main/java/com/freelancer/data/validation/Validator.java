package com.freelancer.data.validation;

import android.util.Patterns;

import org.apache.commons.lang3.StringUtils;

/**
 * The Validator class contains static methods for validating fields.
 */
public class Validator {
    public static boolean passwordsMatch(String firstPasswordText, String confirmPasswordText) {
        if(firstPasswordText == null || confirmPasswordText == null) {
            return false;
        }
        return (firstPasswordText.equals(confirmPasswordText));
    }

    // A placeholder username validation check
    public static boolean isEmailValid(String username) {
        if (StringUtils.countMatches(username, '@') != 1 || username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }
}
