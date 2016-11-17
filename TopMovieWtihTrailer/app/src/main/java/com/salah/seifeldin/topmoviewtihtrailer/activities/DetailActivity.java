package com.salah.seifeldin.topmoviewtihtrailer.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.salah.seifeldin.topmoviewtihtrailer.R;
import com.salah.seifeldin.topmoviewtihtrailer.fragments.MovieDetailFragment;

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
