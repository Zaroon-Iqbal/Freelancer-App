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
    boolean infoExists = false;
    boolean picExists = false;
    Uri imageUri;
    HashMap<String,String> data;
    ActivityResultLauncher<Intent> pickPhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), uri -> {
        if (uri != null) {
            changed = true;
            imageUri = uri.getData().getData();
            image.setImageURI(imageUri);
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

        fillInFields();

        reference = FirebaseFirestore.getInstance().collection("UsersExample")
                .document("ContractorsExample").collection("ContractorData")
                .document(user.getUid());

        submit.setOnClickListener(v -> {
            if(storeText()){
                storeImage();
            }
            else
                Toast.makeText(this,"Missing information",Toast.LENGTH_SHORT).show();
        });

        portfolio.setOnClickListener(v -> {
            Intent intent = new Intent(EditContractorProfile.this, EditPortfolio.class);
            intent.putExtra("businessID", user.getUid());
            startActivity(intent);
        });

        socials.setOnClickListener(v -> {
            Intent intent = new Intent(EditContractorProfile.this, EditSocialLinks.class);
            intent.putExtra("businessID", user.getUid());
            startActivity(intent);
        });

        picture.setOnClickListener(v -> pickPhoto.launch(new Intent(MediaStore.ACTION_PICK_IMAGES)));

        cancel.setOnClickListener(v -> finish());

    }

    private void storeImage() {
        if(changed){
            StorageReference storage = FirebaseStorage.getInstance().getReference().child("PortfolioEx");
            String uid = UUID.randomUUID().toString();
            storage.child(uid).putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                    storage.child(uid).getDownloadUrl().addOnSuccessListener(uri ->
                    {
                        data.put("ProfilePic",uri.toString());
                        data.put("PicLocation",uid);
                        reference.set(data);
                        if(!getIntent().getStringExtra("Location").isEmpty())
                            deletePrevious();
            }));
        }
        else{
            data.put("ProfilePic",getIntent().getStringExtra("Uri"));
            data.put("PicLocation",getIntent().getStringExtra("Location"));
            reference.set(data);
            Toast.makeText(this, "Profile Updated",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void deletePrevious(){
        StorageReference storage = FirebaseStorage.getInstance().getReference().child("PortfolioEx")
                .child(getIntent().getStringExtra("Locatoin"));
        storage.delete();
        Toast.makeText(this, "Profile Updated",Toast.LENGTH_SHORT).show();
        finish();
    }

    private void fillInFields() {
        String name = getIntent().getStringExtra("Name");
        String address = getIntent().getStringExtra("Address");
        String[] addressComponents;
        if(!address.isEmpty())
            addressComponents = address.split(",");
        else
            addressComponents = new String[3];
        String about = getIntent().getStringExtra("About");
        String uri = getIntent().getStringExtra("Uri");

        if(!name.isEmpty()) {
            this.name.setText(name);
            infoExists = true;
        }
        if(!about.isEmpty())
            this.about.setText(about);
        if(!uri.isEmpty()) {
            Glide.with(EditContractorProfile.this).load(uri).into(image);
            picExists = true;
        }
        if(addressComponents.length > 0){
            streetText.setText(addressComponents[0].trim());
            cityText.setText(addressComponents[1].trim());
            stateText.setText(addressComponents[2].trim());
        }
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
        }
        return cont;
    }
}
