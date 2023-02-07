package com.freelancer.data.model;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthRepository extends LiveData<FirebaseUser> {
    private final Application application;

    private final FirebaseAuth firebaseAuth;
//    private final FirebaseUserLiveData firebaseUserLiveData;
    private final MutableLiveData<FirebaseUser> firebaseUserLiveData;
    private final MutableLiveData<Boolean> loggedOutLiveData;

    public FirebaseAuthRepository(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.loggedOutLiveData = new MutableLiveData<>();
        this.firebaseUserLiveData = new MutableLiveData<>();
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        firebaseUserLiveData.postValue(firebaseAuth.getCurrentUser());
                        //TODO: Login successful. Go to the homepage.
                    } else {
                        Toast.makeText(application.getApplicationContext(),
                                "Login failed. " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        //TODO: Login unsuccessful. Inform user of error.
                    }
                });
    }

    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        firebaseUserLiveData.postValue(firebaseAuth.getCurrentUser());
                    } else {
                        Toast.makeText(application.getApplicationContext(),
                                "Registration Failure: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void logOut() {
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserLiveData() {
        return firebaseUserLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }
}