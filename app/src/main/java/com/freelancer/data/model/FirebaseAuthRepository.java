package com.freelancer.data.model;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.freelancer.ui.registration.result.AuthFailure;
import com.freelancer.ui.registration.result.AuthResult;
import com.freelancer.ui.registration.result.AuthSuccess;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

/**
 * FirebaseAuthRepository handles all Firebase-related authentication processes.
 *
 * This class contains MutableLiveData objects, with wrapped values in them.
 *
 * LiveData objects offer a number of advantages compared to storing these wrapped values raw:
 *  - They are observable
 *  - They respect the lifecycle of the view they're associated with.
 *
 * Observability is important because it allows our ViewModel to observe changes to these objects,
 * and in turn update the interface on any change. This ensures the data our UI displays will always
 * match the model. This helps decouple the data layer from the display layer.
 *
 * Life-cycle awareness is important too, as it automatically handles subscribing and unsubscribing
 * depending on the state of the view. For example, if we have a Profile activity, and we decide to
 * recreate it, or change it's configuration (rotating the view, etc.), then we can be sure it will
 * always receive the most up-to-date data.
 *
 * If the view goes into the background and becomes inactive, then the MutableLiveData objects will
 * automatically unsubscribe any subscribers, and resubscribe them once they become active again.
 */
public class FirebaseAuthRepository extends LiveData<FirebaseUser> {
    private final Application application;

    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<FirebaseUser> firebaseUserLiveData;
    private final MutableLiveData<Boolean> loggedOutLiveData;
    private final MutableLiveData<AuthResult> authResult;

    public FirebaseAuthRepository(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.loggedOutLiveData = new MutableLiveData<>();
        this.firebaseUserLiveData = new MutableLiveData<>();
        this.authResult = new MutableLiveData<>();
    }

    /**
     * Given email and password credentials, perform a login attempt.
     * @param email The email.
     * @param password The password.
     */
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

    /**
     * Given email and password credentials, perform a registration attempt.
     * @param email The email.
     * @param password The password.
     */
    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), task -> {
                    if (task.isSuccessful()) {
                        firebaseUserLiveData.postValue(firebaseAuth.getCurrentUser());
                        authResult.postValue(new AuthSuccess(firebaseAuth.getCurrentUser()));
                    } else {
                        FirebaseAuthException exception = (FirebaseAuthException)task.getException();
                        authResult.postValue(new AuthFailure(exception));
                    }
                });
    }

    public MutableLiveData<AuthResult> getAuthExceptionMutableLiveData() {
        return authResult;
    }

    /**
     * Invalidates any stored authentication details and logs the user out.
     *
     * Updates the loggedOutLiveData state for the ViewModel to use.
     */
    public void logOut() {
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

    /**
     * Returns an observable MutableLiveData object, wrapped with a FirebaseUser object.
     *
     * Whenever a successful login occurs, this MutableLiveData object is updated with that
     * FirebaseUser, and upon logout, would be invalidated.
     *
     * Since it's observable, other entities like ViewModels can subscribe to changes, and properly
     * act on them when certain changes occur. For instance, if a ViewModel detects that the
     * FirebaseUser has been invalidated, it can call a method to change the user's activity to the
     * login screen and have them authenticate again.
     *
     * If the ViewModel detects that the FirebaseUser has become valid, then it can perform checks to
     * determine which type of user it is, and change to the appropriate activity.
     *
     * @return MutableLiveData<FirebaseUser> firebaseUserLiveData
     */
    public MutableLiveData<FirebaseUser> getFirebaseUserLiveData() {
        return firebaseUserLiveData;
    }

    /**
     * Returns an observable MutableLiveData object, wrapped with a boolean that provides the user's
     * logged-in state.
     *
     * @return MutableLiveData<Boolean> loggedOutLiveData
     */
    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }
}