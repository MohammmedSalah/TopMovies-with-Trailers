package com.salah.seifeldin.topmoviewtihtrailer.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.salah.seifeldin.topmoviewtihtrailer.R;
import com.salah.seifeldin.topmoviewtihtrailer.adapters.RecycleMoviesAdapter;
import com.salah.seifeldin.topmoviewtihtrailer.interfaces.MovieApiServices;
import com.salah.seifeldin.topmoviewtihtrailer.models.MovieModel;
import com.salah.seifeldin.topmoviewtihtrailer.models.MoviesListModel;
import com.salah.seifeldin.topmoviewtihtrailer.retrofitnstance.RetrofitClient;
import com.salah.seifeldin.topmoviewtihtrailer.utilities.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesListFragment extends Fragment {

    static List<MovieModel> movies ;
    static List<MovieModel> poularMovies ;
    static List<MovieModel> topRatedMovies ;
    RecycleMoviesAdapter moviesAdapter ;
    RecyclerView recyclerView ;
    int show_MoviesStatus = 0 ;
    //ProgressDialog dialog ;
    MoviesClickListener listener ;
    static Context context;
    public interface MoviesClickListener{
        public void movieId(int id) ;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (MoviesClickListener) activity ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_movies_list, container, false);
        movies =  new ArrayList<>() ;

        /*dialog = new ProgressDialog(getActivity());
        dialog.show();
        dialog.setCancelable(false);*/
        initViews(view);
        context = getActivity() ;
        return view ;
    }

    private void initViews(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclemovieslist);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        moviesAdapter = new RecycleMoviesAdapter(getActivity(), movies ) ;
        recyclerView.setAdapter(moviesAdapter);
        if(show_MoviesStatus == 0)
            getJsonPopularMoviesData();
        else if(show_MoviesStatus == 1)
            getJsonTopRatedMoviesData();
        else{
            getFavoriteMoviesData();
        }

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    listener.movieId(position);
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    public  void getJsonPopularMoviesData(){
        if(poularMovies!=null && poularMovies.size()>0) {
            Log.d("success" , "success") ;
            setAdapter(poularMovies);
            movies = topRatedMovies ;
        }else {
            MovieApiServices movieApiServices = RetrofitClient.getMoviesMovieApiServices();
            Call<MoviesListModel> call = movieApiServices.getPopularMovies(RetrofitClient.API_KEY);
            call.enqueue(new Callback<MoviesListModel>() {
                @Override
                public void onResponse(Response<MoviesListModel> response, Retrofit retrofit) {
                    movies = response.body().getMovies();
                    //Toast.makeText(getActivity(), String.valueOf(movies.size()), Toast.LENGTH_LONG).show();
                    //dialog.hide();
                    Log.d("dddd", String.valueOf(movies.size()));
                    setAdapter(movies);
                    poularMovies = new ArrayList<MovieModel>(movies);
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("error in loading data ", t.getMessage());
                }
            });
        }
    }

    public  void getJsonTopRatedMoviesData(){
        if(topRatedMovies!=null && topRatedMovies.size()>0) {
            Log.d("success" , "success") ;
            setAdapter(topRatedMovies);
            movies = topRatedMovies;
        }else {
            MovieApiServices movieApiServices = RetrofitClient.getMoviesMovieApiServices();
            Call<MoviesListModel> call = movieApiServices.getTopRatedMovies(RetrofitClient.API_KEY);
            call.enqueue(new Callback<MoviesListModel>() {
                @Override
                public void onResponse(Response<MoviesListModel> response, Retrofit retrofit) {
                    movies = response.body().getMovies();
                    //Toast.makeText(getActivity(), String.valueOf(movies.size()), Toast.LENGTH_LONG).show();
                    //dialog.hide();
                    Log.d("dddd", String.valueOf(movies.size()));
                    setAdapter(movies);
                    topRatedMovies = new ArrayList<MovieModel>(movies);
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("error in loading data ", t.getMessage());
                }
            });
        }
    }

    public void setAdapter(List<MovieModel> moviesList){
        moviesAdapter.setMovieModelList(moviesList);
    }
    public void getFavoriteMoviesData(){
        movies = SharedPreferenceUtils.getFavorites(context) ;
        moviesAdapter = new RecycleMoviesAdapter(context ,movies) ;
        recyclerView.setAdapter(moviesAdapter);
    }

    public void setShow_MoviesStatus(int moviesStatus){
        this.show_MoviesStatus = moviesStatus ;
    }


}
