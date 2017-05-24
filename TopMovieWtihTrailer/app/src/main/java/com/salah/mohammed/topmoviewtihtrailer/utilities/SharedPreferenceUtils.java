package com.salah.mohammed.topmoviewtihtrailer.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.gson.Gson;
import com.salah.mohammed.topmoviewtihtrailer.models.MovieModel;

public class SharedPreferenceUtils {

    public static final String PREFS_NAME = "MoviesApp";
    public static final String FAVORITES = "Movies_Favorite";
    public static final String OFFLINE_POPULAR_STATE ="offline_popular_state";
    public static final String OFFLINE_TOP_RATED_STATE ="offline_top_rated_state";

    public SharedPreferenceUtils() {
        super();
    }

    public static void setOffline_Popular_state(Context context){
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putBoolean(OFFLINE_POPULAR_STATE,true);
        editor.commit();
    }
    public static boolean checkOffline_Popular_State(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return  settings.getBoolean(OFFLINE_POPULAR_STATE,false);
    }

    public static void setOffline_Top_Rated_state(Context context){
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putBoolean(OFFLINE_TOP_RATED_STATE,true);
        editor.commit();
    }
    public static boolean checkOffline_Top_Rated_State(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return  settings.getBoolean(OFFLINE_TOP_RATED_STATE,false);
    }


    // This four methods are used for maintaining favorites.
    public static void saveFavorites(Context context, List<MovieModel> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public static void addFavorite(Context context, MovieModel movieModel) {
        List<MovieModel> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<MovieModel>();
        if(!getIds(context).contains(movieModel.getId())) {
            favorites.add(movieModel);
            saveFavorites(context, favorites);
        }
    }

    public static void removeFavorite(Context context, MovieModel movieModel) {
        ArrayList<MovieModel> favorites = getFavorites(context);
        if (favorites != null) {

            Log.d("size now1" , String.valueOf(favorites.size())) ;
            for(int i=0 ; i<favorites.size() ; i++){
                //Log.d("shhhh" , String.valueOf(favorites.get(i).getId()) + "   " + String.valueOf(movieModel.getId())) ;
                if(favorites.get(i).getId().equals(movieModel.getId())) {
                    Log.d("yesFound", String.valueOf(movieModel.getId()));
                    favorites.remove(i);
                    Log.d("size now2" , String.valueOf(favorites.size())) ;
                }
            }
            saveFavorites(context , favorites);
        }
    }

    public static boolean checkFavorite(Context context , MovieModel movieModel){
        ArrayList<Integer> ids = getIds(context) ;
        Boolean ch = false;
        if(ids !=null){
            if(ids.contains(movieModel.getId()))
                ch = true  ;
        }
        return  ch ;
    }

    public static ArrayList<Integer> getIds(Context context){
        ArrayList<MovieModel> favorits = getFavorites(context) ;
        ArrayList<Integer> ids  =  new ArrayList<>() ;
        for(int i=0 ; i<favorits.size() ; i++){
            ids.add(favorits.get(i).getId()) ;
        }
        return ids ;
    }
    public static ArrayList<MovieModel> getFavorites(Context context) {
        SharedPreferences settings;
        List<MovieModel> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            MovieModel[] favoriteItems = gson.fromJson(jsonFavorites,
                    MovieModel[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<MovieModel>(favorites);
        } else{
            favorites = new ArrayList<>();
        }


        return (ArrayList<MovieModel>) favorites;
    }
}