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
    String[] uids;
    Date[] dates;

    public CompleteListenerExtension(String[] uids, Date[] dates, int pos){
        position = pos;
        this.uids = uids;
        this.dates = dates;
    }

    @Override
    public void onSuccess(Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CollectionReference portfolio = FirebaseFirestore.getInstance().collection("UsersExample")
                .document("ContractorsExample").collection("ContractorData")
                .document(user.getUid()).collection("Portfolio");
        HashMap<String,Object> data = new HashMap<>();

        data.put("ProfilePhoto",uri);
        data.put("Timestamp",dates[position]);

        portfolio.document(uids[position]).set(data).addOnFailureListener(e ->
                Log.e("ERROR---","Could not create reference for an image"));
    }
}
