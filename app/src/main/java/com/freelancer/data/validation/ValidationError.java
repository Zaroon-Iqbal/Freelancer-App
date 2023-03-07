package com.freelancer.data.validation;

/**
 * A ValidationError represents an error in a form submission.
 *
 * Each entry contains the erroneous field, and the applicable error message to display to the user.
 */
public enum ValidationError {
    EMPTY_EMAIL(ErroneousField.EMAIL, ""),
    INVALID_EMAIL(ErroneousField.EMAIL, "The email is invalid."),
    EMPTY_PASSWORD(ErroneousField.PASSWORD, ""),
    WEAK_PASSWORD(ErroneousField.PASSWORD, "The password is too weak."),
    EMPTY_FIRST_NAME(ErroneousField.FIRST_NAME, ""),
    EMPTY_LAST_NAME(ErroneousField.LAST_NAME, ""),
    PASSWORD_MATCH(ErroneousField.PASSWORD, "The passwords you've entered don't match.");

    private String errorMessage;
    private final ErroneousField field;

    ValidationError(ErroneousField field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
        if(errorMessage.equals("")) {
            this.errorMessage = "* Required";
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ErroneousField getField() {
        return field;
    }
}
