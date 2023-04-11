package com.freelancer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.data.viewmodel.JobInfoViewModel;
import com.freelancer.joblisting.creation.custom.model.TemplateFieldModel;
import com.freelancer.joblisting.creation.custom.viewmodel.FieldFormViewModel;
import com.freelancer.joblisting.creation.model.JobListingModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinalizeJobListing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinalizeJobListing extends Fragment {

    public FinalizeJobListing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FinalizeJobListing.
     */
    // TODO: Rename and change types and number of parameters
    public static FinalizeJobListing newInstance() {
        return new FinalizeJobListing();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finalize_job_listing, container, false);

        Button createJobListing = view.findViewById(R.id.create_job_listing);

        createJobListing.setOnClickListener(view1 -> {
            JobInfoViewModel jobInfoViewModel = new ViewModelProvider(requireActivity()).get(JobInfoViewModel.class);
            FieldFormViewModel formViewModel = new ViewModelProvider(requireActivity()).get(FieldFormViewModel.class);

            ArrayList<TemplateFieldModel> customFields = formViewModel.getCustomFields();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


            JobListingModel jobListingModel = new JobListingModel(jobInfoViewModel.getJobListingModel(), customFields);

            Log.i("UID", firebaseAuth.getCurrentUser().getUid());


            Log.i("Job listing to send: ", jobListingModel.toString());
            jobInfoViewModel.postJobListing(jobListingModel);
        });
        return view;
    }
}