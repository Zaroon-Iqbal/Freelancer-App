package com.freelancer.placeholder;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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


public class MessageActivityPlaceholder extends AppCompatActivity {
    private static final String TAG = "firestore";
    MessagePlaceholderBinding binding;
    UserAdapter user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MessagePlaceholderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = new UserAdapter(getApplicationContext());
        binding.recyclerUser.setAdapter(user);
        binding.recyclerUser.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

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
       // setContentView(R.layout.message_placeholder);
        /*
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.MessageNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    //When user clicks on the home icon
                    case R.id.HomeNav:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0,0);
                        return true;

                    //When user clicks on the calendar icon
                    case R.id.CalendarNav:
                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    //When user clicks on the favorites icon
                    case R.id.FavoriteNav:
                        startActivity(new Intent(getApplicationContext(), FavoriteActivityPlaceholder.class));
                        overridePendingTransition(0,0);
                        return true;

                    //When user clicks on the message icon
                    case R.id.MessageNav:
                        startActivity(new Intent(getApplicationContext(), MessagePlaceholderBinding.class));
                        overridePendingTransition(0,0);
                        return true;

                    //When user clicks on the profile icon
                    case R.id.ProfileNav:
                        startActivity(new Intent(getApplicationContext(), ProfileActivityPlaceholder.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        */

    }
}
