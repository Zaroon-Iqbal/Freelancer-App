package com.freelancer.ui.bottom_nav;

        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import androidx.fragment.app.Fragment;

        import com.freelancer.R;

// Connects to the profile activity. By Edward Kuoch.
public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_contractor_profile, container, false);
    }
}