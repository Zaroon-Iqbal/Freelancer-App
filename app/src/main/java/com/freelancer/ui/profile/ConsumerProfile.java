package com.freelancer.ui.profile;

import com.bumptech.glide.Glide;
import com.freelancer.ui.profile.EditConsumerProfile;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.freelancer.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.util.Log;
import android.widget.TextView;




public class ConsumerProfile extends BottomSheetDialogFragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;

    StorageReference storageReference;
    final private String TAG = "LOGGER";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_consumer_profile,container,false);

        Button edit = v.findViewById(R.id.editButton);
        Button message = v.findViewById(R.id.messageButton);
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView rating = v.findViewById(R.id.consumerRating);
        ShapeableImageView profilePic = v.findViewById(R.id.profilePic);
        user = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

//        message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ConsumerProfile.this, Message.class));
//            }
//        });

        //edit.setOnClickListener(v1 -> startActivity(new Intent(getActivity(), EditConsumerProfile.class)));
        edit.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), EditConsumerProfile.class);
            intent.putExtra("documentId",getArguments().getString("documentId"));
            startActivity(intent);
        });

//        DocumentReference userDocRef = db.collection("UsersExample").document("ConsumersExample").collection("ConsumerData")
//                .document(user.getUid());

        DocumentReference userDocRef = db.collection("userListings").document(getArguments().getString("documentId"));

        userDocRef.addSnapshotListener((document, e) -> {
            if(e != null){
                Log.e(TAG,"Listen failed",e);
                return;
            }
            if(document != null && document.exists()){
                if(document.contains("name")) {
                    if(!document.getString("name").isEmpty())
                        name.setText(document.getString("name"));
                }
                if(document.contains("Rating"))
                    rating.setText(document.getString("Rating"));
                if(document.contains("ProfilePic")){
                Glide.with(ConsumerProfile.this).load(document.getString("ProfilePic")).into(profilePic);
                }
            }
            else Log.d(TAG, "No such document");
        });

        return v;
    }
}
