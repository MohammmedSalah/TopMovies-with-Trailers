package com.salah.mohammed.topmoviewtihtrailer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;




public class MoviesListModel {

    @SerializedName("results")
    @Expose
    private List<MovieModel> movies = new ArrayList<MovieModel>();

    /**
     *
     * @return
     * The movies
     */
    public List<MovieModel> getMovies() {
        return movies;
    }

    /**
     *
     * @param movies
     * The movies
     */
    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
    }

}