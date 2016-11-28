package com.salah.seifeldin.topmoviewtihtrailer.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.salah.seifeldin.topmoviewtihtrailer.models.MovieModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohammed Salah on 21/11/2016.
 */

public class LoadMoviesDataBaseService extends AsyncTask<Integer , Void , List<MovieModel>> {

    public interface OnLoadingOfflineMovies {
        public void onLoadingOfflineMoviesListner(List<MovieModel> movieModels);
    }
    OnLoadingOfflineMovies onLoadingOfflineMovies ;
    public  Context  context ;
    public  LoadMoviesDataBaseService(Context context ,OnLoadingOfflineMovies onLoadingOfflineMovies ){
        this.context = context ;
        this.onLoadingOfflineMovies = onLoadingOfflineMovies;
    }

    @Override
    protected List<MovieModel> doInBackground(Integer... integers) {
        List<MovieModel> modelList = new ArrayList<>();
        if(context == null)
            Log.d("null" , "null");
        MoviesDataBaseHelper moviesDataBaseHelper  = new MoviesDataBaseHelper(context) ;
        modelList = moviesDataBaseHelper.getAllMovies(integers[0]) ;
        return modelList;
    }

    @Override
    protected void onPostExecute(List<MovieModel> movieModels) {
        super.onPostExecute(movieModels);
        Log.d("loading " , ""+ movieModels.size()) ;
        for(int i=0 ; i<movieModels.size() ; i++){
            Log.d("MovieTitle" , movieModels.get(i).getTitle()) ;
        }
        onLoadingOfflineMovies.onLoadingOfflineMoviesListner(movieModels);
    }
}
