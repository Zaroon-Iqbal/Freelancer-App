package com.freelancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.freelancer.data.model.FirestoreRepository;
/*
This class is used for consumers to leave a review for their service
 */
public class ReviewActivity extends AppCompatActivity {
    public float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        RatingBar bar = findViewById(R.id.ratingBar);
        TextView txtScore = findViewById(R.id.ratingType);
        EditText comment = findViewById(R.id.comment);
        Button submit = findViewById(R.id.submitReview);
        FirestoreRepository fire = new FirestoreRepository(getApplication());

        //This is to add a small description according to the rating that is selected
        bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = v;//v is the rating number
                if(v <= 1){
                    txtScore.setText("Horrible! :(");
                }
                else if( v <= 2){
                    txtScore.setText("Not the Best!");
                }
                else if( v <= 3){
                    txtScore.setText("Alright!");
                }
                else if( v <= 4) {
                    txtScore.setText("Pretty Good!");
                }
                else {
                    txtScore.setText("AMAZING! :)");
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() { //stores the review in firestore
            @Override
            public void onClick(View view) {
                fire.createJobReview(comment.getText().toString(), rating );
                Intent intent = new Intent(getApplicationContext(), ReviewList.class);
                startActivity(intent);
            }
        });
    }
}