package com.freelancer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.freelancer.data.model.FirestoreRepository;

public class ReviewActivity extends AppCompatActivity {
    public int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        RatingBar bar = findViewById(R.id.ratingBar);
        TextView txtScore = findViewById(R.id.ratingType);
        EditText comment = findViewById(R.id.comment);
        Button submit = findViewById(R.id.submitReview);
        FirestoreRepository fire = new FirestoreRepository(getApplication());


        bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = (int) v;
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fire.createJobReview(comment.getText().toString(), rating );
            }
        });
    }
}