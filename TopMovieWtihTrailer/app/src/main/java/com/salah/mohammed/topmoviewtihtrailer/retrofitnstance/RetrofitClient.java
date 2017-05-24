package com.salah.mohammed.topmoviewtihtrailer.retrofitnstance;

import com.salah.mohammed.topmoviewtihtrailer.interfaces.MovieApiServices;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;




public class RetrofitClient {
    public static final String ROOT_URL = "http://api.themoviedb.org";
    public static final String POPULARMOVIES_ENDPOINT = "/3/movie/popular";
    public static final String TOPRATEDMOVIE_ENDPOINT = "/3/movie/top_rated";
    public static final String REVIEWMOVIES_ENDPOINT = "/3/movie/{movieID}/reviews";
    public static final String TrailerMOVIES_ENDPOINT = "/3/movie/{movieID}/videos";
    public static final String API_KEY = "c0520a06c65f6f64fa310517d8daaf3a";


    private static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build() ;
    }

    public static MovieApiServices getMoviesMovieApiServices(){
        return getRetrofitInstance().create(MovieApiServices.class) ;
    }

}
