package com.salah.seifeldin.topmoviewtihtrailer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.salah.seifeldin.topmoviewtihtrailer.models.MovieModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Mohammed Salah on 20/11/2016.
 */

public class MoviesDataBaseHelper extends SQLiteOpenHelper {

    private static MoviesDataBaseHelper moviesDataBase_Instance ;
    private static final String DATABASE_NAME = "MoviesDataBase" ;
    private static final int DATABASE_VERSION = 1 ;

    private static final String TABLE_MOVIES = "movies" ;

    private static final String POSTER_PATH = "poster_path";
    private static final String OVERVIEW  ="overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String  MOVIE_Kind= "movie_kind";

    public  MoviesDataBaseHelper(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION);

    }

    /*public static synchronized MoviesDataBaseHelper getInstance(Context context){
        if(moviesDataBase_Instance == null){
            moviesDataBase_Instance = new MoviesDataBaseHelper(context);
        }
        return moviesDataBase_Instance ;
    }*/


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createMovieTable =  "CREATE TABLE `movies` (\n" +
                "\t`poster_path`\tTEXT,\n" +
                "\t`overview`\tTEXT,\n" +
                "\t`release_date`\tTEXT,\n" +
                "\t`id`\tINTEGER,\n" +
                "\t`title`\tTEXT,\n" +
                "\t`backdrop_path`\tTEXT,\n" +
                "\t`vote_average`\tREAL,\n" +
                "\t`movie_kind`\tINTEGER,\n" +
                "\tPRIMARY KEY(`id`)\n" +
                ");" ;

        sqLiteDatabase.execSQL(createMovieTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
            onCreate(sqLiteDatabase);
        }
    }


    public void addMovie (MovieModel movieModel , int movieKind){
        SQLiteDatabase database = getWritableDatabase() ;
        database.beginTransaction();
        try{
            ContentValues contentValues = new ContentValues() ;
            contentValues.put(POSTER_PATH , movieModel.getPosterPath());
            contentValues.put(OVERVIEW , movieModel.getOverview());
            contentValues.put(RELEASE_DATE , movieModel.getReleaseDate());
            contentValues.put(ID , movieModel.getId());
            contentValues.put(TITLE , movieModel.getTitle());
            contentValues.put(BACKDROP_PATH , movieModel.getBackdropPath());
            contentValues.put(VOTE_AVERAGE , movieModel.getVoteAverage());
            contentValues.put(MOVIE_Kind , movieKind);

            database.insertOrThrow(TABLE_MOVIES , null , contentValues) ;
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.d(TAG, "Error while trying to add movie to database");
        }finally {
            database.endTransaction();
        }
    }

    public void deleteRows(int movieKind){
        SQLiteDatabase database = getReadableDatabase();
        database.execSQL("delete from "+TABLE_MOVIES  + " where " + MOVIE_Kind +" = " + movieKind);
    }
    public List<MovieModel> getAllMovies(int movieType){
        String allColumns [] = {POSTER_PATH , OVERVIEW , RELEASE_DATE , ID , TITLE ,
                                BACKDROP_PATH , VOTE_AVERAGE , MOVIE_Kind } ;
        List<MovieModel> modelList = new ArrayList<>() ;
        SQLiteDatabase database = getReadableDatabase() ;
        Cursor cursor = database.query(TABLE_MOVIES , allColumns , MOVIE_Kind + " = " + String.valueOf(movieType) ,
                                        null , null , null , null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            MovieModel movieModel = cursorToMovieModel(cursor) ;
            modelList.add(movieModel);
            cursor.moveToNext();
        }
        cursor.close();
        return modelList;
    }
    public MovieModel cursorToMovieModel(Cursor cursor){
        MovieModel movieModel = new MovieModel() ;
        movieModel.setPosterPath(cursor.getString(0));
        movieModel.setOverview(cursor.getString(1));
        movieModel.setReleaseDate(cursor.getString(2));
        movieModel.setId(cursor.getInt(3));
        movieModel.setTitle(cursor.getString(4));
        movieModel.setBackdropPath(cursor.getString(5));
        movieModel.setVoteAverage(cursor.getFloat(6));

        return movieModel;
    }
}
