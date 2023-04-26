package com.freelancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.freelancer.data.model.FirestoreRepository;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/*
This file is used to populate the UI with the reviews that were made for a service
 */
public class ReviewList extends AppCompatActivity {
    String comment;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        List<Review> reviewList = new ArrayList<Review>();//list to be displayed of reviews


        RecyclerView recycler = findViewById(R.id.ReviewList);
        Button refresh = findViewById(R.id.Refresh);//used to update after creating a new review
        FirebaseFirestore fire = FirebaseFirestore.getInstance();
        count = 0;

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fire.collection("jobReviews").
                        whereGreaterThan("starRating", 4)
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> documentList = queryDocumentSnapshots.getDocuments();
                                for(DocumentSnapshot snap : documentList){
                                    if( count == 0) {  //random conditions are used because actual data not collected
                                        count++;
                                    }else{ //temporary conditional fix due to not having proper data
                                        comment = snap.get("Comment").toString();
                                        Log.i("comment:", comment);
                                        reviewList.add(new Review("Zackeria James", 5, comment, R.drawable.reviewa));
                                        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        recycler.setAdapter(new ReviewAdapter(getApplicationContext(), reviewList));

                                    }
                                }
                            }
                        });
            }
        });
        //Temporary data sued to display in the reviews section,
        reviewList.add(new Review("Zac Khan", 3, "The Detailer did an alright job",R.drawable.reviewa ));
        reviewList.add(new Review("Tom Holland", 4, "He did a pretty good Job",R.drawable.reviewb ));
        reviewList.add(new Review("Susan Smith", 3, "Alright job",R.drawable.reviewc ));
        reviewList.add(new Review("Jack Black", 1, "HORRIBLE, He left spots and dirt",R.drawable.reviewd ));
        reviewList.add(new Review("Linkin Park", 5, "AMAZING, My car is like Brand New!",R.drawable.reviewe ));



       recycler.setLayoutManager(new LinearLayoutManager(this));//set the layout
       recycler.setAdapter(new ReviewAdapter(getApplicationContext(), reviewList));//set the adapter
    }
}