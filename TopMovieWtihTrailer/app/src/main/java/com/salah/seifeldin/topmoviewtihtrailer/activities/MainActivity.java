package com.salah.seifeldin.topmoviewtihtrailer.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.salah.seifeldin.topmoviewtihtrailer.R;
import com.salah.seifeldin.topmoviewtihtrailer.fragments.MovieDetailFragment;
import com.salah.seifeldin.topmoviewtihtrailer.fragments.MoviesListFragment;

public class MainActivity extends AppCompatActivity implements MoviesListFragment.MoviesClickListener{

    boolean ch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        if(ch)
            startPopularMovies();

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
            startPopularMovies();
        }
        else if(id == R.id.action_toprated_movies){
            startTopRatedMovies();
        }
        else if(id == R.id.action_favorite_movies){
            startFavoriteMovies();
        }


        return super.onOptionsItemSelected(item);
    }

    public void startPopularMovies(){
        MoviesListFragment moviesListFragment = new MoviesListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        moviesListFragment.setShow_MoviesStatus(0);
        ft.replace(R.id.listfragment, moviesListFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        ch = false ;
    }

    public void startTopRatedMovies(){
        MoviesListFragment moviesListFragment = new MoviesListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        moviesListFragment.setShow_MoviesStatus(1);
        ft.replace(R.id.listfragment, moviesListFragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void startFavoriteMovies(){
        MoviesListFragment moviesListFragment = new MoviesListFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        moviesListFragment.setShow_MoviesStatus(2);
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
