package com.salah.mohammed.topmoviewtihtrailer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.salah.mohammed.topmoviewtihtrailer.R;
import com.salah.mohammed.topmoviewtihtrailer.fragments.MovieDetailFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MovieDetailFragment movieDetailFragment = (MovieDetailFragment) getFragmentManager().findFragmentById(R.id.detailfragment_ph);
        int movieId= getIntent().getExtras().getInt("id") ;
        movieDetailFragment.setMovieId(movieId);
    }

}
