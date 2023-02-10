package com.freelancer.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.Nullable;

import com.freelancer.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ConsumerProfile extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_consumer_profile,container,false);

        Button message = v.findViewById(R.id.messageButton);

//        message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ConsumerProfile.this, Message.class));
//            }
//        });

        return v;
    }
}
