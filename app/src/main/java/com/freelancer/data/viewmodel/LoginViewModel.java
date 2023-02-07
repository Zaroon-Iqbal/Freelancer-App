package com.freelancer.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.freelancer.data.model.FirebaseAuthRepository;
import com.google.firebase.auth.FirebaseUser;

/**
 * A ViewModel for the LoginView.
 *
 * This class is responsible for managing all data required by the login view,
 * and for communicating with other parts of the application to acquire that data.
 *
 * A ViewModel should never reference any Activity in it's functioning, it's only purpose is to
 * acquire and store data.
 *
 * An Activity should never perform any logic, or call outside sources to obtain data, it's only
 * purpose is to display the data it receives from the ViewModel.
 */
public class LoginViewModel extends AndroidViewModel {
    private final FirebaseAuthRepository firebaseAuthRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        firebaseAuthRepository = new FirebaseAuthRepository(application);
    }

    /**
     * Given email and password credentials, call on the FirebaseAuthRepository to login to an account.
     * @param email The email to register.
     * @param password The password to register.
     */
    public void login(String email, String password) {
        firebaseAuthRepository.login(email, password);
    }

    /**
     * A reference to an observable FirebaseUser LiveData object. See the FirebaseAuthRepository
     * class for more information on the MutableLiveData structure.
     * @return A MutableLiveData object.
     */
    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return firebaseAuthRepository.getFirebaseUserLiveData();
    }
}