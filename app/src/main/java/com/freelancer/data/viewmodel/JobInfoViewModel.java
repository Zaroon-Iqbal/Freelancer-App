package com.freelancer.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.freelancer.data.model.FirestoreRepository;
import com.freelancer.joblisting.creation.model.JobInfoModel;
import com.freelancer.joblisting.creation.model.JobListingModel;

/**
 * The JobListing viewModel.
 * <p>
 * This class is responsible for managing all data required by the Job listing creation and management,
 * and for communicating with other parts of the application to acquire that data.
 */
public class JobInfoViewModel extends AndroidViewModel {
    private final FirestoreRepository database;
    private final MutableLiveData<Boolean> modelUpdateNeeded;
    private JobInfoModel jobInfoModel;

    public JobInfoViewModel(@NonNull Application application) {
        super(application);
        modelUpdateNeeded = new MutableLiveData<>(true);
        jobInfoModel = new JobInfoModel();
        database = new FirestoreRepository(application);
    }

    public void postJobListing(JobListingModel jobListingModel) {
        database.createJobListing(jobListingModel);
    }

    public JobInfoModel getJobListingModel() {
        return jobInfoModel;
    }

    public MutableLiveData<Boolean> isModelUpdateNeeded() {
        return modelUpdateNeeded;
    }

    public void updateJobListing(String Jtitle, String Jdescription, String Jphone, String Jcity, Double Jprice, String category, Integer radius, String type) {
        jobInfoModel = new JobInfoModel(Jtitle, Jdescription, Jphone, Jcity, Jprice, category, radius, type);
        //database.createJobListing(Jtitle, Jdescription, Jphone, Jcity, Jprice, category, loc, type);
    }
}