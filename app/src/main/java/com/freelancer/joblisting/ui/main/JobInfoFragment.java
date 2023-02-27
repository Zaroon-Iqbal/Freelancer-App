package com.freelancer.joblisting.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.freelancer.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JobInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    Button createService;
    EditText title;
    EditText description;
    EditText phone;
    EditText city;
    EditText price;

    AutoCompleteTextView viewcat;

    AutoCompleteTextView viewLoc;
    AutoCompleteTextView viewType;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_info, container, false);

        viewcat = view.findViewById(R.id.category_items);
        viewLoc = view.findViewById(R.id.radius_items);
        viewType = view.findViewById(R.id.location_items);

        String [] items = {"Accounting/Finance", "Engineering", "Art/Media/Design", "Biotech/Science", "Business", "Customer Service", "Education", "Food/Bev", "General Labor",
                           "Government", "Human Resources", "Legal", "Manufacturing", "Marketing", "Medical/health", "Nonprofit Sector", "Real Estate", "Wholesale/Retail",
                           "Salon/Spa/Fitness", "Security", "Skilled Trade", "Software", "Systems/Networks", "Technical Support", "Transport", "Tv/Film", "Web/Info Design", "Writing/Editting", "other"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(requireContext(), R.layout.category_list, items);
        viewcat.setAdapter(itemAdapter);

        String [] radius = {"10 miles", "20 miles", "50 miles", "other"};
        ArrayAdapter<String> radiusAdapter = new ArrayAdapter<>(getContext(), R.layout.category_list, radius);
        viewLoc.setAdapter(radiusAdapter);

        String [] location = {"Virtual", "Mobile", "Business Location", "other"};
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getContext(), R.layout.category_list, location);
        viewType.setAdapter(locationAdapter);

        title = view.findViewById(R.id.contractor_title);
        description = view.findViewById(R.id.contractor_description);
        phone = view.findViewById(R.id.contractor_phone);
        city = view.findViewById(R.id.contractor_city);
        price = view.findViewById(R.id.contractor_price);
        createService = view.findViewById(R.id.contractor_create_service);

        createService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jobTitle = title.getText().toString();
                String jobDescription = description.getText().toString();
                String jobPhone = phone.getText().toString();
                String jobCity = city.getText().toString();
                String jobPrice = price.getText().toString();
                String category = viewcat.getText().toString();
                String location = viewLoc.getText().toString();
                String type = viewType.getText().toString();

                boolean valid = validation(jobTitle, jobDescription, jobPhone, jobCity, jobPrice, category, location, type);
                if(valid)
                {
                    Toast.makeText(view.getContext(), "Data was valid", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(view.getContext(), jobPhone, Toast.LENGTH_SHORT).show();

                    Toast.makeText(view.getContext(), "Sorry Check the input fields again", Toast.LENGTH_SHORT).show();

                }

            }
        });







        // Inflate the layout for this fragment
        return view;
    }

    private Boolean validation(String Jtitle, String Jdescription, String Jphone, String Jcity, String Jprice, String cat, String loc, String typ) {
        if (Jtitle.length() == 0) {
            title.requestFocus();
            title.setError("THE FIELD MUST NOT BE EMPTY");
            return false;
        }
        else if(!Jtitle.matches("[a-zA-Z]+"))
        {
            title.requestFocus();
            title.setError("ONLY ALPHABETICAL CHARACTERS");
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