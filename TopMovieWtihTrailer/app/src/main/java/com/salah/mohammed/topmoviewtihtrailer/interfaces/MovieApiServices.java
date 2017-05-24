package com.salah.mohammed.topmoviewtihtrailer.interfaces;

import com.salah.mohammed.topmoviewtihtrailer.models.MoviesListModel;
import com.salah.mohammed.topmoviewtihtrailer.models.ReviewsListModel;
import com.salah.mohammed.topmoviewtihtrailer.models.TrailersListModel;
import com.salah.mohammed.topmoviewtihtrailer.retrofitnstance.RetrofitClient;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;



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
