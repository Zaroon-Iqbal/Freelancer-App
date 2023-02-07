package com.freelancer.data.viewmodel;

import android.app.Application;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.freelancer.data.model.FirebaseAuthRepository;
import com.freelancer.databinding.ActivityRegisterConsumerBinding;
import com.freelancer.ui.login.RegisterConsumer;
import com.google.firebase.auth.FirebaseUser;

/**
 * A ViewModel for the RegisterConsumerView.
 *
 * This class is responsible for managing all data required by the consumer registration view,
 * and for communicating with other parts of the application to acquire that data.
 *
 * A ViewModel should never reference any Activity in it's functioning, it's only purpose is to
 * acquire and store data.
 *
 * An Activity should never perform any logic, or call outside sources to obtain data, it's only
 * purpose is to display the data it receives from the ViewModel.
 *
 * @TODO: Discuss merging these views since they're practically identical.
 */

public class RegisterConsumerViewModel extends AndroidViewModel {
    private final FirebaseAuthRepository firebaseAuthRepository;

    public RegisterConsumerViewModel(@NonNull Application application) {
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
     * Given email and password credentials, call on the FirebaseAuthRepository to register an account.
     * @param email The email to register.
     * @param password The password to register.
     */
    public void register(String email, String password) {
        firebaseAuthRepository.register(email, password);
    }
}