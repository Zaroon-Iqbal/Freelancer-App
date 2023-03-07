package com.freelancer.data.viewmodel;

import android.app.Application;
import android.app.WallpaperColors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.freelancer.data.model.FirebaseAuthRepository;
import com.freelancer.data.validation.ValidationError;
import com.freelancer.data.validation.Validator;
import com.freelancer.ui.registration.result.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.ValidationPath;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A ViewModel for the RegisterContractorView.
 *
 * This class is responsible for managing all data required by the contractor registration view,
 * and for communicating with other parts of the application to acquire that data.
 *
 * A ViewModel should never reference any Activity in it's functioning, it's only purpose is to
 * acquire and store data.
 *
 * An Activity should never perform any logic, or call outside sources to obtain data, it's only
 * purpose is to display the data it receives from the ViewModel.
 */
public class RegisterContractorViewModel extends AndroidViewModel {
    private final FirebaseAuthRepository firebaseAuthRepository;

    public RegisterContractorViewModel(@NonNull Application application) {
        super(application);
        firebaseAuthRepository = new FirebaseAuthRepository(application);
    }

    /**
     * A reference to an observable FirebaseUser LiveData object. See the FirebaseAuthRepository
     * class for more information on the MutableLiveData structure.
     * @return A MutableLiveData object.
     */
    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return firebaseAuthRepository.getFirebaseUserLiveData();
    }

    /**
     * A reference to an observable AuthResult LiveData object. See the FirebaseAuthRepository
     * class for more information on the MutableLiveData structure.
     * @return A MutableLiveData object.
     */
    public MutableLiveData<AuthResult> getAuthResultLiveData() {
        return firebaseAuthRepository.getAuthExceptionMutableLiveData();
    }

    /**
     * Given registration form details, return a set of validation errors.
     *
     * @param firstName The text in the first name field.
     * @param lastName The text in the last name field.
     * @param email The text in the email field.
     * @param password The text in the password field.
     * @param passwordConfirm The text in the password confirmation field.
     * @return A set of form validation errors.
     */
    public Set<ValidationError> getValidationErrors(String firstName, String lastName, String email,
                                                     String password, String passwordConfirm) {
        Map<ValidationError, Boolean> validationErrorMap = new HashMap<>();

        validationErrorMap.put(ValidationError.INVALID_EMAIL, !Validator.isEmailValid(email));
        validationErrorMap.put(ValidationError.EMPTY_FIRST_NAME, firstName.length() == 0);
        validationErrorMap.put(ValidationError.EMPTY_LAST_NAME, lastName.length() == 0);
        validationErrorMap.put(ValidationError.EMPTY_EMAIL, email.length() == 0);
        validationErrorMap.put(ValidationError.EMPTY_PASSWORD, password.length() == 0);
        validationErrorMap.put(ValidationError.WEAK_PASSWORD, password.length() < 6 && password.length() > 0);
        validationErrorMap.put(ValidationError.PASSWORD_MATCH, !password.equals(passwordConfirm));

        return validationErrorMap.entrySet().stream()
                .filter(a -> a.getValue().equals(true))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    /**
     * Given email and password credentials, call on the FirebaseAuthRepository to register an account.
     * @param email The email to register.
     * @param password The password to register.
     */
    public void register(String email, String password) {
        firebaseAuthRepository.register(email, password);
    }
}


