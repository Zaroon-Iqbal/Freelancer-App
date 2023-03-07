package com.freelancer.joblisting.creation.custom.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.freelancer.joblisting.creation.custom.CustomFieldType;

public class CustomCheckboxViewModel extends ViewModel {
    private MutableLiveData<String> customCheckboxTitle;
    private CustomFieldType customFieldType;

    public CustomCheckboxViewModel() {
        customFieldType = CustomFieldType.BOOLEAN;
        customCheckboxTitle = new MutableLiveData<>();
    }

    public void erase() {
        Log.i("BOOL VM", "Erased");
    }

    public MutableLiveData<String> getCustomCheckboxTitle() {
        return customCheckboxTitle;
    }
}
