package com.freelancer.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.freelancer.data.model.FirestoreRepository;

/**
 * The JobListing viewModel.
 *
 * This class is responsible for managing all data required by the Job listing creation and management,
 * and for communicating with other parts of the application to acquire that data.
 */
public class JobInfoViewModel extends AndroidViewModel {
    private final FirestoreRepository database;

    public JobInfoViewModel(@NonNull Application application) {
        super(application);
        database = new FirestoreRepository(application);
    }

    public void createJobListing(String Jtitle, String Jdescription, String Jphone, String Jcity, String Jprice, String category, String loc, String type){
        database.createJobListing(Jtitle, Jdescription, Jphone, Jcity, Jprice, category, loc, type);
    }
}
