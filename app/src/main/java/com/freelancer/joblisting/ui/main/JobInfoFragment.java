package com.freelancer.joblisting.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.freelancer.R;
import com.freelancer.data.model.FirestoreRepository;
import com.freelancer.data.viewmodel.CalendarViewModel;
import com.freelancer.data.viewmodel.JobInfoViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**job info fragment made for displaying and retrieving accurate job listing data from contractors
 *
 */

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobInfoFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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

    AutoCompleteTextView viewcat;

    AutoCompleteTextView viewLoc;
    AutoCompleteTextView viewType;

    FirestoreRepository database;

    JobInfoViewModel jobviewModel;

    private String mParam1;
    private String mParam2;

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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobInfoFragment newInstance(String param1, String param2) {
        JobInfoFragment fragment = new JobInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    /**
     * onCreate method for creating the instance
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        jobviewModel = new ViewModelProvider(this).get(JobInfoViewModel.class);
        //instantiating variables to appropriate ones in XML layout file
        viewcat = view.findViewById(R.id.category_items);
        viewLoc = view.findViewById(R.id.radius_items);
        viewType = view.findViewById(R.id.location_items);
        choosePic = view.findViewById(R.id.chooseImage);
        uploadPic = view.findViewById(R.id.uploadImage);
        image = view.findViewById(R.id.fireImage);
        tPhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        image.setImageURI(result);
                        imageU = result;
                    }
                }
        );

        choosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tPhoto.launch("image/*");
            }
        });

        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPicture();
            }
        });

        //adding the job listing categories
        String [] items = {"Accounting/Finance", "Engineering", "Art/Media/Design", "Biotech/Science", "Business", "Customer Service", "Education", "Food/Bev", "General Labor",
                           "Government", "Human Resources", "Legal", "Manufacturing", "Marketing", "Medical/health", "Nonprofit Sector", "Real Estate", "Wholesale/Retail",
                           "Salon/Spa/Fitness", "Security", "Skilled Trade", "Software", "Systems/Networks", "Technical Support", "Transport", "Tv/Film", "Web/Info Design", "Writing/Editting", "other"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(requireContext(), R.layout.category_list, items);
        viewcat.setAdapter(itemAdapter);//storing them in a dropdown menu

        //adding the radius options for dropdown menu
        String [] radius = {"10 miles", "20 miles", "50 miles", "other"};
        ArrayAdapter<String> radiusAdapter = new ArrayAdapter<>(getContext(), R.layout.category_list, radius);
        viewLoc.setAdapter(radiusAdapter);

        //adding the method of business conducting to the drop down menu
        String [] location = {"Virtual", "Mobile", "Business Location", "other"};
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getContext(), R.layout.category_list, location);
        viewType.setAdapter(locationAdapter);

        //variables used for accurately retrieving data from input fields
        title = view.findViewById(R.id.contractor_title);
        description = view.findViewById(R.id.contractor_description);
        phone = view.findViewById(R.id.contractor_phone);
        city = view.findViewById(R.id.contractor_city);
        price = view.findViewById(R.id.contractor_price);
        createService = view.findViewById(R.id.contractor_create_service);


        //on click listener used to check for button click and retrieve data when done so
        createService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //converting the input data to strings
                String jobTitle = title.getText().toString();
                String jobDescription = description.getText().toString();
                String jobPhone = phone.getText().toString();
                String jobCity = city.getText().toString();
                String jobPrice = price.getText().toString();
                String category = viewcat.getText().toString();
                String location = viewLoc.getText().toString();
                String type = viewType.getText().toString();

                //check whether the input data are valid inputs to be stored int he database
                boolean valid = validation(jobTitle, jobDescription, jobPhone, jobCity, jobPrice, category, location, type);
                if(valid)
                {
                    Toast.makeText(view.getContext(), "Data was valid", Toast.LENGTH_SHORT).show();
                    //USER ID is still needed as a field to be entered
                    jobviewModel.createJobListing(jobTitle, jobDescription, jobPhone, jobCity, jobPrice, category, location, type);
                }
                else {
                    Toast.makeText(view.getContext(), "Sorry Check the input fields again", Toast.LENGTH_SHORT).show();

                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void uploadPicture() {

        SimpleDateFormat form = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date current = new Date();
        String file = form.format(current);
        storage = FirebaseStorage.getInstance().getReference("images/" + file);

        storage.putFile(imageU).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.setImageURI(null);
                Toast.makeText(viewcat.getContext(), "successfully uploaded image", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(viewcat.getContext(), "Failed to Upload", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void imageSelect() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }

    /**
     * Method used for input validation
     * @param Jtitle, must not be empty
     * @param Jdescription, must not be empty
     * @param Jphone, must not be empty, and 10 digits long
     * @param Jcity, must not be empty, must be letters
     * @param Jprice, must not be empty
     * @param cat, must not be empty
     * @param loc, must not be empty
     * @param typ,must not be empty
     * @return whether the conditions are met or not
     */
    private Boolean validation(String Jtitle, String Jdescription, String Jphone, String Jcity, String Jprice, String cat, String loc, String typ) {
        if (Jtitle.length() == 0) {
            title.requestFocus();
            title.setError("THE FIELD MUST NOT BE EMPTY");
            return false;
        }
        else if (Jdescription.length() == 0)
        {
            description.requestFocus();
            description.setError("THE FIELD MUST NOT BE EMPTY");
            return false;
        }
        else if(Jphone.length() == 0)
        {
            phone.requestFocus();
            phone.setError("THE FIELD MUST NOT BE EMPTY");
            return false;
        }
        else if (!Jphone.matches("^\\d{10}$"))
        {
            phone.requestFocus();
            phone.setError("A PHONE NUMBER MUST HAVE 10 DIGITS");
            return false;
        }
        else if(Jcity.length() == 0)
        {
            city.requestFocus();
            city.setError("THE FIELD MUST NOT BE EMPTY");
            return false;
        }
        else if(!Jcity.matches("[a-zA-Z]+"))
        {
            city.requestFocus();
            city.setError("ONLY ALPHABETICAL CHARACTERS");
            return false;
        }
        else if(Jprice.length() == 0)
        {
            price.requestFocus();
            price.setError("THE FIELD MUST NOT BE EMPTY");
            return false;
        }
        else if(cat.length() == 0)
        {
            viewcat.requestFocus();
            viewcat.setError("MUST CHOOSE A FIELD");
            return false;
        }
        else if(loc.length() == 0)
        {
            viewLoc.requestFocus();
            viewLoc.setError("MUST CHOOSE A FIELD");
            return false;
        }
        else if(typ.length() == 0)
        {
            viewType.requestFocus();
            viewType.setError("MUST CHOOSE A FIELD");
            return false;
        }
        else {
            return true;
        }
    }

}