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

import com.bumptech.glide.Glide;
import com.freelancer.R;
import com.freelancer.databinding.ActivityEditConsumerBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditConsumerProfile extends AppCompatActivity implements View.OnClickListener{

    Button submit;
    Button updatePic;
    Button cancel;
    EditText nameText;
    Uri imageUri;
    ShapeableImageView photo;

    final String TAG = "LOG";
    DocumentReference userDocRef;
    StorageReference storageReference;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user;
    WriteBatch write;
    String location;
    String previousPhoto;

    boolean photoExists = false;

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
        storageReference = FirebaseStorage.getInstance().getReference();
        write = db.batch();

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
                    if(document.contains("ProfilePic")) {
                        previousPhoto = document.getString("PicLocation");
//                        StorageReference imageRef = storageReference.child(previousPhoto);
//                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(EditConsumerProfile.this).load(uri).into(photo));
                        Glide.with(EditConsumerProfile.this).load(document.getString("ProfilePic")).into(photo);
                        photoExists = true;
                    }
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
                    write.update(userDocRef,"Name",nameText.getText().toString());
                }
                if(imageUri != null) {
                    location = user.getUid() + UUID.randomUUID().toString();
                    StorageReference imageRef = storageReference.child(location);
                    imageRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            if (photoExists) {
                                deletePrevious();
                                write.update(userDocRef, "PicLocation", location);
                                write.update(userDocRef,"ProfilePic",uri);
                                commitChanges();
                            } else {
                                Map<String, Object> map = new HashMap<>();
                                map.put("PicLocation", location);
                                map.put("ProfilePic",uri);
                                write.set(userDocRef, map, SetOptions.merge());
                                commitChanges();
                            }
                        });
                    });
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

    public void deletePrevious(){
        StorageReference imageRef = storageReference.child(previousPhoto);
        imageRef.delete().addOnSuccessListener(unused -> Log.d(TAG,"Previous photo deleted"))
                .addOnFailureListener(e -> Log.w(TAG,"Previous photo was not deleted",e));
    }

    public void commitChanges(){
        write.commit().addOnCompleteListener(task -> Toast.makeText(EditConsumerProfile.this,"Profile updated",Toast.LENGTH_SHORT).show());
    }
}
