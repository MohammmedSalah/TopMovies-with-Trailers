package com.salah.seifeldin.topmoviewtihtrailer.interfaces;

import com.salah.seifeldin.topmoviewtihtrailer.models.MoviesListModel;
import com.salah.seifeldin.topmoviewtihtrailer.models.ReviewsListModel;
import com.salah.seifeldin.topmoviewtihtrailer.models.TrailersListModel;
import com.salah.seifeldin.topmoviewtihtrailer.retrofitnstance.RetrofitClient;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


/**
 * Created by Mohammed Salah on 29/10/2016.
 */

public interface MovieApiServices {

    @GET(RetrofitClient.POPULARMOVIES_ENDPOINT)
    Call<MoviesListModel> getPopularMovies(@Query("api_key") String apiKey) ;

    @GET(RetrofitClient.TOPRATEDMOVIE_ENDPOINT)
    Call<MoviesListModel> getTopRatedMovies(@Query("api_key") String apiKey) ;

    @GET(RetrofitClient.REVIEWMOVIES_ENDPOINT)
    Call<ReviewsListModel> getReviews(@Path("movieID") int movieID , @Query("api_key") String apiKey) ;

    @GET(RetrofitClient.TrailerMOVIES_ENDPOINT)
    Call<TrailersListModel> getTrailers(@Path("movieID") int movieID , @Query("api_key") String apiKey) ;


}
