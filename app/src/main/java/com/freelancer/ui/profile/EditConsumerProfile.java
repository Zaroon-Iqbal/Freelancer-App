package com.freelancer.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.freelancer.R;
import com.freelancer.databinding.ActivityEditConsumerBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditConsumerProfile extends AppCompatActivity implements View.OnClickListener{

    Button submit;
    Button updatePic;
    Button cancel;
    EditText nameText;
    Uri imageUri;
    ShapeableImageView photo;

    final String TAG = "LOG";
    DocumentReference userDocRef;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;

    ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new RequestPermission(), isGranted->{
        if(isGranted)
            photoPicker();
        else {
            photoPicker();
            //Toast.makeText(EditConsumerProfile.this, "Need permission to change photo", Toast.LENGTH_SHORT).show();
        }
    });

    ActivityResultLauncher<Intent> pickPhoto = registerForActivityResult(new StartActivityForResult(),uri ->{
        if(uri != null){
            imageUri = uri.getData().getData();
            photo.setImageURI(imageUri);

            //uploadPhoto();
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEditConsumerBinding binding = ActivityEditConsumerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = FirebaseAuth.getInstance().getCurrentUser();
        cancel = binding.cancelBtn;
        submit = binding.submitBtn;
        updatePic = binding.updatePicBtn;
        nameText = binding.name;
        photo = binding.profilePic;

        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
        updatePic.setOnClickListener(this);

        userDocRef = db.collection("UsersExample").document("ConsumersExample").collection("ConsumerData")
                .document(user.getUid());

        userDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    nameText.setText(document.getString("Name"));
                    //if(document.contains("PhotoUri"))
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancelBtn:
                finish();
                break;
            case R.id.submitBtn:
                if(nameText.getText().toString().trim().length() > 0){
                    userDocRef.update("Name",nameText.getText().toString())
                            .addOnSuccessListener(unused -> Log.d(TAG,"Name updated"))
                            .addOnFailureListener(e -> Log.w(TAG,"Name Not updated",e));
                }
                finish();
                break;
            case R.id.updatePicBtn:
                if (ContextCompat.checkSelfPermission(EditConsumerProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    photoPicker();
                }
                else
                    requestPermission();
                break;
        }

    }

    public void photoPicker(){
        pickPhoto.launch(new Intent(MediaStore.ACTION_PICK_IMAGES));
    }

    public void requestPermission(){
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
    }
}
