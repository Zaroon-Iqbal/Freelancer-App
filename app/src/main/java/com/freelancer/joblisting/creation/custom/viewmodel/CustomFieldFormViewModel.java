package com.freelancer.joblisting.creation.custom.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.freelancer.joblisting.creation.custom.model.CustomFieldModel;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomFieldFormViewModel extends AndroidViewModel {
    private final ArrayList<CustomFieldModel> customFields;

    public CustomFieldFormViewModel(@NonNull Application application) {
        super(application);
        customFields = new ArrayList<>();
    }

    public void printModels() {
        Log.i("PRINT", Arrays.toString(customFields.toArray()));
    }

    public void addCustomField(CustomFieldModel customFieldModel) {
        customFields.add(customFieldModel);
    }

    public void removeCustomField(CustomFieldModel customFieldModel) {
        customFields.remove(customFieldModel);
    }
}