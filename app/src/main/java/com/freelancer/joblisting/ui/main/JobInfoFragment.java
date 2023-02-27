package com.freelancer.joblisting.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

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


        // Inflate the layout for this fragment
        return view;
    }
}