package com.freelancer.joblisting.creation.custom.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;

public class CustomFieldFormViewModel extends AndroidViewModel {
    private final ArrayList<Fragment> fragments;

    public CustomFieldFormViewModel(@NonNull Application application) {
        super(application);
        fragments = new ArrayList<>();
    }

    public ArrayList<Fragment> getFragments() {
        return fragments;
    }

    public int getFragmentsSize() {
        return fragments.size();
    }

    public boolean removeFragment(Fragment fragment) {
        return fragments.remove(fragment);
    }


    public boolean addFragment(Fragment fragment) {
        return fragments.add(fragment);
    }
}
