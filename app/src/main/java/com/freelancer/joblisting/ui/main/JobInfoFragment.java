package com.freelancer.joblisting.ui.main;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.R;
import com.freelancer.data.viewmodel.JobInfoViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * job info fragment made for displaying and retrieving accurate job listing data from contractors
 */

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobInfoFragment extends Fragment {
    //variables used for retrieving and storing data
    Button createService;
    Button choosePic;
    Button uploadPic;
    ImageView image;
    ActivityResultLauncher<String> tPhoto;
    StorageReference storage;

    Uri imageU;

    EditText title;
    EditText description;
    EditText phone;
    EditText city;
    EditText price;

    AutoCompleteTextView categoryDropdown;

    TextInputEditText jobRadiusDropdown;
    AutoCompleteTextView jobLocationDropdown;

    JobInfoViewModel jobInfoViewModel;

    String fileFormat;

    String imageURL;

    /**
     * required constructor
     */
    public JobInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JobInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobInfoFragment newInstance() {
        return new JobInfoFragment();
    }

    @Override
    /**
     * onCreate method for creating the instance
     *
     * @param savedInstanceState - the state of the fragment
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    /**
     * main method where variables data is altered and stored in the database
     *
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //view inflater needed for referencing the fragment
        View view = inflater.inflate(R.layout.fragment_job_info, container, false);

        jobInfoViewModel = new ViewModelProvider(requireActivity()).get(JobInfoViewModel.class);

        //instantiating variables to appropriate ones in XML layout file
        categoryDropdown = view.findViewById(R.id.category_items);
        jobRadiusDropdown = view.findViewById(R.id.menu_radius);
        jobLocationDropdown = view.findViewById(R.id.location_items);

        choosePic = view.findViewById(R.id.chooseImage);
        uploadPic = view.findViewById(R.id.uploadImage);
        image = view.findViewById(R.id.fireImage);

        choosePic.setOnClickListener(view12 -> tPhoto.launch("image/*"));
        tPhoto = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                image.setImageURI(result);
                imageU = result;

            }
        });
        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat form = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
                Date current = new Date();
                fileFormat = form.format(current);
                storage = FirebaseStorage.getInstance().getReference("images/" + fileFormat);

                storage.putFile(imageU).addOnSuccessListener((OnSuccessListener<UploadTask.TaskSnapshot>) taskSnapshot -> {
                    storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageURL = uri.toString();
                            Log.i("ImageURL: ", imageURL);
                        }
                    });
                    image.setImageURI(null);
                    Toast.makeText(categoryDropdown.getContext(), "successfully uploaded image", Toast.LENGTH_SHORT).show();

                }).addOnFailureListener((OnFailureListener) e -> Toast.makeText(categoryDropdown.getContext(), "Failed to Upload", Toast.LENGTH_SHORT).show());


            }
        });


        //adding the job listing categories
        String[] categories = {"Accounting/Finance", "Engineering", "Art/Media/Design", "Biotech/Science", "Business",
                "Customer Service", "Education", "Food/Bev", "General Labor",
                "Government", "Human Resources", "Legal", "Manufacturing", "Marketing",
                "Medical/health", "Nonprofit Sector", "Real Estate", "Wholesale/Retail",
                "Salon/Spa/Fitness", "Security", "Skilled Trade", "Software", "Systems/Networks",
                "Technical Support", "Transport", "Tv/Film", "Web/Info Design", "Writing/Editting", "other"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(), R.layout.category_list, categories);
        categoryDropdown.setAdapter(categoryAdapter); //storing categories in a dropdown menu
        
        //adding the method of business conducting to the drop down menu
        String[] jobLocation = {"Virtual", "Client Location", "Business Location"};
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getContext(), R.layout.category_list, jobLocation);
        jobLocationDropdown.setAdapter(locationAdapter);

        //variables used for accurately retrieving data from input fields
        title = view.findViewById(R.id.contractor_title);
        description = view.findViewById(R.id.contractor_description);
        phone = view.findViewById(R.id.contractor_phone);
        city = view.findViewById(R.id.contractor_city);
        price = view.findViewById(R.id.contractor_price);
        //createService = view.findViewById(R.id.contractor_create_service);


        //on click listener used to check for button click and retrieve data when done so
//        createService.setOnClickListener(view1 -> {
//            //converting the input data to strings
//            String jobTitle = title.getText().toString();
//            String jobDescription = description.getText().toString();
//            String jobPhone = phone.getText().toString();
//            String jobCity = city.getText().toString();
//            String jobPrice = price.getText().toString();
//            String category = categoryDropdown.getText().toString();
//            String location = jobRadiusDropdown.getText().toString();
//            String type = jobLocationDropdown.getText().toString();
//
//            //check whether the input data are valid inputs to be stored int he database
//            boolean valid = validation(jobTitle, jobDescription, jobPhone, jobCity, jobPrice, category, location, type);
//            if (valid) {
//                //jobInfoViewModel.createJobListing(jobTitle, jobDescription, jobPhone, jobCity, jobPrice, category, location, type);
//            }
//        });

        jobInfoViewModel.isModelUpdateNeeded().observe(requireActivity(), (updateNeeded) -> {
            if (updateNeeded) {
                String jobTitle = title.getText().toString();
                String jobDescription = description.getText().toString();
                String jobPhone = phone.getText().toString();
                String jobCity = city.getText().toString();
                Double jobPrice = 0.0;
                String category = categoryDropdown.getText().toString();
                Integer jobRadius = 0;
                String type = jobLocationDropdown.getText().toString();
                String imageReference = imageURL;//This is where the link to the recent image link is

                try {
                    jobPrice = Double.parseDouble(price.getText().toString());
                    jobRadius = Integer.parseInt(jobRadiusDropdown.getText().toString());
                } catch (NumberFormatException nfe) {

                }
                jobInfoViewModel.updateJobListing(jobTitle, jobDescription, jobPhone, jobCity, jobPrice, category, jobRadius, type);
                Log.i("Update complete", "Updated the model!");
                jobInfoViewModel.isModelUpdateNeeded().setValue(false);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
/*
    private StorageReference uploadPicture() {
        SimpleDateFormat form = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date current = new Date();
        fileFormat = form.format(current);
        storage = FirebaseStorage.getInstance().getReference("images/" + fileFormat);

        storage.putFile(imageU).addOnSuccessListener((OnSuccessListener<UploadTask.TaskSnapshot>) taskSnapshot -> {
            image.setImageURI(null);
            Toast.makeText(categoryDropdown.getContext(), "successfully uploaded image", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener((OnFailureListener) e -> Toast.makeText(categoryDropdown.getContext(), "Failed to Upload", Toast.LENGTH_SHORT).show());

        return storage;
    }
*/

    /**
     * Method used for input validation
     *
     * @param Jtitle,       must not be empty
     * @param Jdescription, must not be empty
     * @param Jphone,       must not be empty, and 10 digits long
     * @param Jcity,        must not be empty, must be letters
     * @param Jprice,       must not be empty
     * @param cat,          must not be empty
     * @param loc,          must not be empty
     * @param typ,must      not be empty
     * @return whether the conditions are met or not
     */
    private Boolean validation(String Jtitle, String Jdescription, String Jphone, String Jcity, String Jprice, String cat, String loc, String typ) {
        boolean valid = false;
        if (Jtitle.length() == 0) {
            title.requestFocus();
            title.setError("THE FIELD MUST NOT BE EMPTY");
        } else if (Jdescription.length() == 0) {
            description.requestFocus();
            description.setError("THE FIELD MUST NOT BE EMPTY");
        } else if (Jphone.length() == 0) {
            phone.requestFocus();
            phone.setError("THE FIELD MUST NOT BE EMPTY");
        } else if (!Jphone.matches("^\\d{10}$")) {
            phone.requestFocus();
            phone.setError("A PHONE NUMBER MUST HAVE 10 DIGITS");
        } else if (Jcity.length() == 0) {
            city.requestFocus();
            city.setError("THE FIELD MUST NOT BE EMPTY");
        } else if (!Jcity.matches("[a-zA-Z]+")) {
            city.requestFocus();
            city.setError("ONLY ALPHABETICAL CHARACTERS");
        } else if (Jprice.length() == 0) {
            price.requestFocus();
            price.setError("THE FIELD MUST NOT BE EMPTY");
        } else if (cat.length() == 0) {
            categoryDropdown.requestFocus();
            categoryDropdown.setError("MUST CHOOSE A FIELD");
        } else if (loc.length() == 0) {
            jobRadiusDropdown.requestFocus();
            jobRadiusDropdown.setError("MUST CHOOSE A FIELD");
        } else if (typ.length() == 0) {
            jobLocationDropdown.requestFocus();
            jobLocationDropdown.setError("MUST CHOOSE A FIELD");
        } else {
            valid = true;
        }
        return valid;
    }
}