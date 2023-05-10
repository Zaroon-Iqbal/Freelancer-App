package com.freelancer.ui.profile.portfolio;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.HashMap;


public class CompleteListenerExtension implements OnSuccessListener<Uri> {

    int position;
    static int tracker = -1;
    String[] uids;
    Date[] dates;
    CollectionReference collection;
    EditPortfolio edit;

    public CompleteListenerExtension(CollectionReference collection, EditPortfolio edit, String[] uids, Date[] dates, int pos){
        position = pos;
        this.uids = uids;
        this.dates = dates;
        this.collection = collection;
        if(tracker == -1)
            tracker = uids.length;
        this.edit = edit;
    }

    @Override
    public void onSuccess(Uri uri) {
        HashMap<String,Object> data = new HashMap<>();

        data.put("ProfilePhoto",uri);
        data.put("Timestamp",dates[position]);

        collection.document(uids[position]).set(data).addOnCompleteListener(task -> {
            --tracker;
            if (!task.isSuccessful())
                Log.e("ERROR---","Could not create reference for an image");
            if (tracker == 0)
                edit.displayPortfolio();
        });
    }
}
