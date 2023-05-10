package com.freelancer.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.freelancer.databinding.ActivityEditContractorBinding;
import com.freelancer.ui.profile.portfolio.EditPortfolio;
import com.freelancer.ui.profile.portfolio.EditSocialLinks;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class EditContractorProfile extends AppCompatActivity {
    Button submit;
    Button picture;
    Button cancel;
    TextView portfolio;
    TextView socials;
    ImageView image;
    EditText streetText;
    EditText cityText;
    EditText stateText;
    EditText name;
    EditText about;
    FirebaseUser user;
    DocumentReference reference;
    TextInputLayout businessError;
    TextInputLayout streetError;
    TextInputLayout cityError;
    TextInputLayout stateError;
    TextInputLayout aboutError;
    boolean changed = false;
    Uri imageUri;
    HashMap<String,String> data;
    String userName;
    String email;
    String type;
    String profilePic = "";
    String picLocation = "";
    ActivityResultLauncher<Intent> pickPhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), uri -> {
        if (uri != null) {
            changed = true;
            Intent intent = uri.getData();
            if(intent != null)
            {
                imageUri = intent.getData();
                image.setImageURI(imageUri);
            }
        }
    });
    @Override
    public void onCreate(@NonNull Bundle savedInstance) {
        super.onCreate(savedInstance);
        ActivityEditContractorBinding binding = ActivityEditContractorBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        user = FirebaseAuth.getInstance().getCurrentUser();

        picture = binding.updatePicBtn;
        submit = binding.submitBtn;
        portfolio = binding.portfolioBtn;
        socials = binding.socialBtn;
        cancel = binding.cancel;
        image = binding.businessPic;
        streetText = binding.streetAddress;
        cityText = binding.city;
        stateText = binding.state;
        name = binding.businessName;
        about = binding.aboutText;
        businessError = binding.businessLayout;
        streetError = binding.streetLayout;
        cityError = binding.cityLayout;
        stateError = binding.stateLayout;
        aboutError = binding.aboutLayout;

        data = new HashMap<>();

        reference = FirebaseFirestore.getInstance().collection("userListings")
                .document(getIntent().getStringExtra("documentId"));

        fillInFields();

        submit.setOnClickListener(v -> {
            if(storeText()){
                storeImage();
            }
            else
                Toast.makeText(this,"Missing information",Toast.LENGTH_SHORT).show();
        });

        portfolio.setOnClickListener(v -> {
            Intent intent = new Intent(EditContractorProfile.this, EditPortfolio.class);
            intent.putExtra("documentId", getIntent().getStringExtra("documentId"));
            startActivity(intent);
        });

        socials.setOnClickListener(v -> {
            Intent intent = new Intent(EditContractorProfile.this, EditSocialLinks.class);
            intent.putExtra("documentId", getIntent().getStringExtra("documentId"));
            startActivity(intent);
        });

        picture.setOnClickListener(v -> pickPhoto.launch(new Intent(MediaStore.ACTION_PICK_IMAGES)));

        cancel.setOnClickListener(v -> finish());

    }

    private void storeImage() {
        if(changed){
            StorageReference storage = FirebaseStorage.getInstance().getReference();
            String uid = UUID.randomUUID().toString();
            storage.child(uid).putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                    storage.child(uid).getDownloadUrl().addOnSuccessListener(uri ->
                    {
                        data.put("ProfilePic",uri.toString());
                        data.put("PicLocation",uid);
                        if(!picLocation.isEmpty())
                            deletePrevious();
                        reference.set(data);
                        Toast.makeText(this, "Profile Updated",Toast.LENGTH_SHORT).show();
                        finish();

            }));
        }
        else{
            data.put("ProfilePic",profilePic);
            data.put("PicLocation",picLocation);
            reference.set(data);
            Toast.makeText(this, "Profile Updated",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void deletePrevious(){
        StorageReference storage = FirebaseStorage.getInstance().getReference().child(picLocation);
        storage.delete();
    }

    private void fillInFields() {
        reference.get().addOnSuccessListener(documentSnapshot -> {
            type = documentSnapshot.getString("type");
            email = documentSnapshot.getString("email");
            userName = documentSnapshot.getString("name");
            if(documentSnapshot.contains("BusinessName")) {
                if(!documentSnapshot.getString("BusinessName").isEmpty())
                {
                    name.setText(documentSnapshot.getString("BusinessName"));
                }
            }
            if(documentSnapshot.contains("Location")) {
                if(!documentSnapshot.getString("Location").isEmpty())
                {
                    String[] addressComponents = documentSnapshot.getString("Location").split(", ");
                    streetText.setText(addressComponents[0].trim());
                    cityText.setText(addressComponents[1].trim());
                    stateText.setText(addressComponents[2].trim());
                }
            }
            if(documentSnapshot.contains("About")) {
                if(!documentSnapshot.getString("About").isEmpty())
                {
                    about.setText(documentSnapshot.getString("About"));

                }
            }
            if (documentSnapshot.contains("ProfilePic")) {
                if(!documentSnapshot.getString("ProfilePic").isEmpty())
                {
                    profilePic = documentSnapshot.getString("ProfilePic");
                    Glide.with(EditContractorProfile.this).load(profilePic).into(image);
                }
            }
            if(documentSnapshot.contains("PicLocation")){
                if(!documentSnapshot.getString("PicLocation").isEmpty()){
                    picLocation = documentSnapshot.getString("PicLocation");
                }
            }
        });
    }

    private boolean storeText(){
        boolean cont = true;
        String error = "Required";
        String name = this.name.getText().toString().trim();
        String street = streetText.getText().toString().trim();
        String city = cityText.getText().toString().trim();
        String state = stateText.getText().toString().trim();
        String about = this.about.getText().toString().trim();

        if(name.isEmpty()){
            businessError.setError(error);
            cont = false;
        }
        if(street.isEmpty()){
            streetError.setError(error);
            cont = false;
        }
        if(city.isEmpty()){
            cityError.setError(error);
            cont = false;
        }
        if(state.isEmpty()){
            stateError.setError(error);
            cont = false;
        }
        if(this.about.getText().length() > 100){
            aboutError.setError("Exceeds Max Length");
            cont = false;
        }
        else if(cont){
            data.put("BusinessName",name);
            data.put("Location",street + ", " + city + ", " + state);
            data.put("About",about);
            data.put("email",email);
            data.put("name",userName);
            data.put("type",type);
            data.put("uid",user.getUid());
        }
        return cont;
    }
}
