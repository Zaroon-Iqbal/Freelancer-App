package com.freelancer.joblisting.creation.custom.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.freelancer.joblisting.creation.custom.model.TemplateFieldModel;

import java.util.ArrayList;
import java.util.Arrays;

public class FieldFormViewModel extends AndroidViewModel {
    private final ArrayList<TemplateFieldModel> customFields;

    public FieldFormViewModel(@NonNull Application application) {
        super(application);
        customFields = new ArrayList<>();
    }

    public ArrayList<TemplateFieldModel> getCustomFields() {
        return customFields;
    }

    public void printModels() {
        Log.i("PRINT", Arrays.toString(customFields.toArray()));
    }

    public void addCustomField(TemplateFieldModel templateFieldModel) {
        customFields.add(templateFieldModel);
    }

    public void removeCustomField(TemplateFieldModel templateFieldModel) {
        Log.i("Field model", "Does the list contain: " + templateFieldModel.toString() + "? "
                + customFields.contains(templateFieldModel));
        Log.i("models", Arrays.toString(customFields.toArray()));
        customFields.remove(templateFieldModel);
    }
}