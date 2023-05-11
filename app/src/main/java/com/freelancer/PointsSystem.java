package com.freelancer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PointsSystem extends AppCompatActivity {

    private TextView pointsTextView;
    private Button addButton;
    private Button subtractButton;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_system);

        pointsTextView = findViewById(R.id.points_textview);
        addButton = findViewById(R.id.add_button);
        subtractButton = findViewById(R.id.subtract_button);
        resetButton = findViewById(R.id.reset_button);

        // Add click listeners to the buttons
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPoint();
            }
        });

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractPoint();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPoints();
            }
        });
    }

    // Define methods to add, subtract, and reset points
    private void addPoint() {
        int currentPoints = Integer.parseInt(pointsTextView.getText().toString().split(": ")[1]);
        pointsTextView.setText("Points: " + (currentPoints + 1));
    }

    private void subtractPoint() {
        int currentPoints = Integer.parseInt(pointsTextView.getText().toString().split(": ")[1]);
        pointsTextView.setText("Points: " + (currentPoints - 1));
    }

    private void resetPoints() {
        pointsTextView.setText("Points: 0");
    }
}

