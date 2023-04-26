package com.freelancer.ui.bottom_nav;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.freelancer.R;
import com.freelancer.data.model.FirestoreRepository;
import com.freelancer.databinding.ActivityRegisterConsumerBinding;
import com.freelancer.databinding.MessagePlaceholderBinding;
import com.freelancer.ui.ChatMessaging.UserAdapter;
import com.freelancer.ui.ChatMessaging.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class MessageFragment extends Fragment {
    private static final String TAG = "firestore";
    MessagePlaceholderBinding binding;
    UserAdapter user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = MessagePlaceholderBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        user = new UserAdapter(view.getContext());
        binding.recyclerUser.setAdapter(user);
        binding.recyclerUser.setLayoutManager(new LinearLayoutManager(view.getContext()));

        FirebaseFirestore data = FirebaseFirestore.getInstance();
        CollectionReference doc = data
                .collection("userListings");
        doc.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                user.clear();
                for (DocumentSnapshot document : value.getDocuments()) {
                    String id = document.getString("uid");
                    //Toast.makeText(getApplicationContext()  ,id, Toast.LENGTH_LONG).show();
                    Log.d(TAG, document.getId() + " => " + document.getString("uid"));
                    if (!id.equals(FirebaseAuth.getInstance().getUid())) {
                        Log.d(TAG, document.getId() + " THIS => " + document.getString("uid"));
                        UserModel usersModel = new UserModel(document.getString("name"),
                                                            document.getString("uid"), document.getString("email") );
                        user.add(usersModel);


                    }

                }
            }
        });
        return view;
    }
}