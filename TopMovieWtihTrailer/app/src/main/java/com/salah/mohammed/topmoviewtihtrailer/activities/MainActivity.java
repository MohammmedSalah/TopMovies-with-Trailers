package com.salah.mohammed.topmoviewtihtrailer.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.salah.mohammed.topmoviewtihtrailer.R;
import com.salah.mohammed.topmoviewtihtrailer.fragments.MovieDetailFragment;
import com.salah.mohammed.topmoviewtihtrailer.fragments.MoviesListFragment;

public class MainActivity extends AppCompatActivity implements MoviesListFragment.MoviesClickListener{

    int moviesStatus;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("moviesStatus",moviesStatus);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState !=null && savedInstanceState.containsKey("moviesStatus")){
            moviesStatus = savedInstanceState.getInt("moviesStatus");
            Log.d("moviesStatues" , "true");
        }else
            moviesStatus = 0;

        if(moviesStatus == 0){
            startPopularMovies();
        }else if(moviesStatus == 1){
            startTopRatedMovies();
        }else {
            startFavoriteMovies();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_popular_movies){
            moviesStatus = 0;
            startPopularMovies();
        }
        else if(id == R.id.action_toprated_movies){
            moviesStatus = 1;
            startTopRatedMovies();
        }
        else if(id == R.id.action_favorite_movies){
            moviesStatus = 2;
            startFavoriteMovies();
        }


        return super.onOptionsItemSelected(item);
    }

    public void startPopularMovies(){
        MoviesListFragment moviesListFragment = new MoviesListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        moviesListFragment.setShow_MoviesStatus(moviesStatus);
        ft.replace(R.id.listfragment, moviesListFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void startTopRatedMovies(){
        MoviesListFragment moviesListFragment = new MoviesListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        moviesListFragment.setShow_MoviesStatus(moviesStatus);
        ft.replace(R.id.listfragment, moviesListFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void startFavoriteMovies(){
        MoviesListFragment moviesListFragment = new MoviesListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        moviesListFragment.setShow_MoviesStatus(moviesStatus);
        ft.replace(R.id.listfragment, moviesListFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }


    @Override
    public void movieId(int id) {
        //Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
        View view = findViewById(R.id.detailfragment) ;
        if(view !=null){
            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            movieDetailFragment.setMovieId(id);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.detailfragment, movieDetailFragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }else {
            Intent intent = new Intent(getApplicationContext() , DetailActivity.class) ;
            intent.putExtra("id" , id) ;
            startActivity(intent);
        }
    }
}
