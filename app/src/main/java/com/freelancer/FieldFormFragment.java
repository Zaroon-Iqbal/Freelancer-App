package com.freelancer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.freelancer.joblisting.creation.custom.fragment.FieldTemplateFragment;
import com.freelancer.joblisting.creation.custom.model.FieldType;
import com.freelancer.joblisting.creation.custom.viewmodel.FieldFormViewModel;
import com.nambimobile.widgets.efab.FabOption;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FieldFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FieldFormFragment extends Fragment {

    private FieldFormViewModel viewModel;
    private FragmentManager fragmentManager;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FieldFormFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FieldFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FieldFormFragment newInstance() {
        FieldFormFragment fragment = new FieldFormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_field_form, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(FieldFormViewModel.class);
        fragmentManager = getParentFragmentManager();


        FabOption selection = (FabOption) view.findViewById(R.id.selection_fab);
        FabOption checkbox = (FabOption) view.findViewById(R.id.checkbox_fab);
        FabOption freeform = (FabOption) view.findViewById(R.id.free_form_fab);
        //ExpandableFabLayout expandableFabLayout = (ExpandableFabLayout) view.findViewById(R.id.expandable_fab_layout);
        //ExpandableFab expandableFab = (ExpandableFab) view.findViewById(R.id.expandable_fab);

        selection.setOnClickListener(view12 -> {
            Toast.makeText(getContext(), "Clicked selection!", Toast.LENGTH_SHORT).show();
            this.addChildFragmentTransaction(FieldType.SELECTION);
        });

        checkbox.setOnClickListener(view123 -> addChildFragmentTransaction(FieldType.BOOLEAN));
        freeform.setOnClickListener(view13 -> addChildFragmentTransaction(FieldType.FREE_FORM));
        return view;
    }

    private void addChildFragmentTransaction(FieldType fieldType) {
        FieldTemplateFragment fragment = FieldTemplateFragment.newInstance(fieldType);

        Log.i("Frag", fragment.toString());
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_linear_layout, fragment);
        transaction.commit();
    }
}










