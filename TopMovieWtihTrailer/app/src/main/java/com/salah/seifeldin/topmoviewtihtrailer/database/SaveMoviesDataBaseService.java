package com.salah.seifeldin.topmoviewtihtrailer.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.salah.seifeldin.topmoviewtihtrailer.models.MovieModel;

import java.util.List;

/**
 * Created by Mohammed Salah on 21/11/2016.
 */

public class SaveMoviesDataBaseService extends AsyncTask<List<MovieModel>  ,  Void , Void>{


    public  Context  context ;
    public  int kingNum ;

    public SaveMoviesDataBaseService(Context context , int kingNum ){
        this.context = context ;
        this.kingNum =  kingNum ;
    }
    @Override
    protected Void doInBackground(List<MovieModel>... lists) {
        MoviesDataBaseHelper moviesDataBaseHelper  = new MoviesDataBaseHelper(context) ;
        moviesDataBaseHelper.deleteRows(kingNum);
        for(int i=0 ; i<lists[0].size() ; i++){
            moviesDataBaseHelper.addMovie(lists[0].get(i) , kingNum);
        }
        Log.d("saving ..."  , "finished") ;

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //LoadMoviesDataBaseService loadMoviesDataBaseService = new LoadMoviesDataBaseService(context);
        //loadMoviesDataBaseService.execute(0) ;
    }
}
