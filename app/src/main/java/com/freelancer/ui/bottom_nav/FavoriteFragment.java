package com.freelancer.ui.bottom_nav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.freelancer.R;
import com.freelancer.ui.map.MapActivity;

// Connects to the favorites activity. By Edward Kuoch.
public class FavoriteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO temporary placeholder for accessing the map. Change later.
        Intent intent = new Intent(getActivity(), MapActivity.class);
        startActivity(intent);
        return inflater.inflate(R.layout.map_fragment, container, false);
    }
}