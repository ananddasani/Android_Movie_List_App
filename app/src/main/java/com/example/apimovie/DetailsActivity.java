package com.example.apimovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    TextView movieName, movieRating, movieDescription;
    ImageView movieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        movieName = findViewById(R.id.movieName);
        movieRating = findViewById(R.id.movieRating);
        movieDescription = findViewById(R.id.movieDescription);
        movieImage = findViewById(R.id.movieImage);

        //Extract information from the bundle and show on the UI
        Bundle bundle = getIntent().getExtras();

        String mName = bundle.getString("TITLE");
        double mRating = bundle.getDouble("RATING");
        String mPoster = bundle.getString("POSTER");
        String mOverview = bundle.getString("DESCRIPTION");

        //set the information onto the UI component
        movieName.setText(mName);
        movieRating.setText(String.valueOf(mRating));
        movieDescription.setText(mOverview);

        Glide.with(DetailsActivity.this).load(mPoster).into(movieImage);
    }
}