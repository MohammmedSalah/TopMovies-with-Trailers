package com.salah.mohammed.topmoviewtihtrailer.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.salah.mohammed.topmoviewtihtrailer.R;
import com.salah.mohammed.topmoviewtihtrailer.activities.ReviewActivity;
import com.salah.mohammed.topmoviewtihtrailer.activities.TrailerActivity;
import com.salah.mohammed.topmoviewtihtrailer.models.MovieModel;
import com.salah.mohammed.topmoviewtihtrailer.utilities.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {


    public static Context context;
    public int movieId;
    MovieModel movieModel;
    ImageView backdropPath, reviewImage, trailerImage, favImage;
    RatingBar ratingBar;
    TextView date, title, descreption;

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("myMovieId", movieId);
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (savedInstanceState != null) {
            movieId = savedInstanceState.getInt("myMovieId");
        }
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        context = getActivity();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //TextView textView = (TextView) getView().findViewById(R.id.test);
        //textView.setText(String.valueOf(this.movieId));
        View view = getView();
        backdropPath = (ImageView) view.findViewById(R.id.imagebackdrop_path);

        movieModel = MoviesListFragment.movies.get(movieId);
        Picasso.with(getActivity())
                .load("http://image.tmdb.org/t/p/w185/" + movieModel.getBackdropPath())
                .placeholder(R.drawable.loadimage)
                .into(backdropPath);

        ratingBar = (RatingBar) view.findViewById(R.id.rating);
        ratingBar.setRating((Float) movieModel.getVoteAverage() / 2);

        title = (TextView) view.findViewById(R.id.title);
        date = (TextView) view.findViewById(R.id.date);
        descreption = (TextView) view.findViewById(R.id.desc_textview);

        title.setText(movieModel.getTitle());
        date.setText(movieModel.getReleaseDate());
        descreption.setText(movieModel.getOverview());

        reviewImage = (ImageView) view.findViewById(R.id.rev_im);
        reviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReviewActivity(movieModel.getId());
            }
        });


        trailerImage = (ImageView) view.findViewById(R.id.watchtrailer);
        trailerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getTrailers(movieModel.getId()) ;
                startTrailerActivity(movieModel.getId());
            }
        });


        favImage = (ImageView) view.findViewById(R.id.favorite);
        if (SharedPreferenceUtils.checkFavorite(context, movieModel)) {
            Picasso.with(getActivity())
                    .load(R.drawable.fav)
                    .into(favImage);
        }

        favImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferenceUtils.checkFavorite(context, movieModel)) {
                    Log.d("A", "fav");
                    Picasso.with(getActivity())
                            .load(R.drawable.nfav)
                            .into(favImage);
                    SharedPreferenceUtils.removeFavorite(context, movieModel);
                } else {
                    Log.d("B", "nfav");
                    Picasso.with(getActivity())
                            .load(R.drawable.fav)
                            .into(favImage);
                    SharedPreferenceUtils.addFavorite(context, movieModel);
                }
            }
        });

    }

    public void startReviewActivity(int reviewId) {
        Intent intent = new Intent(getActivity(), ReviewActivity.class);
        intent.putExtra("reviewId", reviewId);
        startActivity(intent);
    }

    public void startTrailerActivity(int movieId) {
        Intent intent = new Intent(getActivity(), TrailerActivity.class);
        intent.putExtra("movieId", movieId);
        startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("size :- ", String.valueOf(SharedPreferenceUtils.getFavorites(context).size()));
    }
}
