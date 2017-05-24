package com.salah.mohammed.topmoviewtihtrailer.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.salah.mohammed.topmoviewtihtrailer.R;
import com.salah.mohammed.topmoviewtihtrailer.adapters.RecycleMoviesAdapter;
import com.salah.mohammed.topmoviewtihtrailer.database.LoadMoviesDataBaseService;
import com.salah.mohammed.topmoviewtihtrailer.database.SaveMoviesDataBaseService;
import com.salah.mohammed.topmoviewtihtrailer.interfaces.MovieApiServices;
import com.salah.mohammed.topmoviewtihtrailer.models.MovieModel;
import com.salah.mohammed.topmoviewtihtrailer.models.MoviesListModel;
import com.salah.mohammed.topmoviewtihtrailer.retrofitnstance.RetrofitClient;
import com.salah.mohammed.topmoviewtihtrailer.utilities.NetworkUtils;
import com.salah.mohammed.topmoviewtihtrailer.utilities.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesListFragment extends Fragment implements LoadMoviesDataBaseService.OnLoadingOfflineMovies {

    static List<MovieModel> movies;
    static List<MovieModel> poularMovies;
    static List<MovieModel> topRatedMovies;
    static Context context;
    ProgressDialog dialog;
    RecycleMoviesAdapter moviesAdapter;
    RecyclerView recyclerView;
    int show_MoviesStatus = 0;
    MoviesClickListener listener;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("show_MoviesStatus" , show_MoviesStatus);
        outState.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) movies);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.containsKey("show_MoviesStatus")) {
            show_MoviesStatus = savedInstanceState.getInt("show_MoviesStatus");
            movies = savedInstanceState.getParcelableArrayList("list");
            Log.d("state  Data" , ""+movies.size());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (MoviesClickListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        context = getActivity();
        movies = new ArrayList<>();
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Please Wait .....");
        dialog.setCancelable(false);
        initViews(view);


        return view;
    }

    private void initViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclemovieslist);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        moviesAdapter = new RecycleMoviesAdapter(getActivity(), movies);
        recyclerView.setAdapter(moviesAdapter);
        dialog.show();
        if (NetworkUtils.isConnectingToInternet(getActivity())) {
            if (show_MoviesStatus == 0) {
                dialog.show();
                getJsonPopularMoviesData();
            } else if (show_MoviesStatus == 1) {
                dialog.show();
                getJsonTopRatedMoviesData();
            } else {
                getFavoriteMoviesData();
            }
        } else {

            dialog.show();
            dialog.setTitle("no internet access ... \n loading to show offline data");
            if (context == null)
                Log.d("null2", "null2");
            LoadMoviesDataBaseService loadMoviesDataBaseService = new LoadMoviesDataBaseService(context, this);

            if (show_MoviesStatus == 0) {
                if (SharedPreferenceUtils.checkOffline_Popular_State(context))
                    loadMoviesDataBaseService.execute(0);
                else {
                    dialog.hide();
                    Toast.makeText(context, "sorry no offline data found !", Toast.LENGTH_LONG).show();
                }

            } else if (show_MoviesStatus == 1) {
                if (SharedPreferenceUtils.checkOffline_Top_Rated_State(context))
                    loadMoviesDataBaseService.execute(1);
                else {
                    dialog.hide();
                    Toast.makeText(context, "sorry no offline data found !", Toast.LENGTH_LONG).show();
                }
            } else {
                getFavoriteMoviesData();
            }
            Toast.makeText(context, "this is offline movies", Toast.LENGTH_SHORT).show();
        }
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(movies == null || movies.size() <=0)
                    Log.d("null movies" ,"true");
                else
                    Log.d("null movies" ,"fale");
                if (child != null && gestureDetector.onTouchEvent(e)) {
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

    public void getJsonPopularMoviesData() {
        if (poularMovies != null && poularMovies.size() > 0) {
            Log.d("success", "success");
            setAdapter(poularMovies);
            movies = poularMovies;
        } else {
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

    public void getJsonTopRatedMoviesData() {
        if (topRatedMovies != null && topRatedMovies.size() > 0) {
            Log.d("success", "success");
            setAdapter(topRatedMovies);
            movies = topRatedMovies;
        } else {
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

    public void setAdapter(List<MovieModel> moviesList) {
        dialog.hide();
        moviesAdapter.setMovieModelList(moviesList);
        SaveMoviesDataBaseService saveMoviesDataBaseService = new SaveMoviesDataBaseService(context, show_MoviesStatus);
        if (show_MoviesStatus == 0 && !SharedPreferenceUtils.checkOffline_Popular_State(context)) {
            Log.d("saving", "setAdapter");
            saveMoviesDataBaseService.execute(moviesList);
            SharedPreferenceUtils.setOffline_Popular_state(context);
        } else if (show_MoviesStatus == 1 && !SharedPreferenceUtils.checkOffline_Top_Rated_State(context)) {
            Log.d("saving", "setAdapter");
            saveMoviesDataBaseService.execute(moviesList);
            SharedPreferenceUtils.setOffline_Top_Rated_state(context);
        }
    }

    public void getFavoriteMoviesData() {
        dialog.hide();
        movies = SharedPreferenceUtils.getFavorites(context);
        moviesAdapter = new RecycleMoviesAdapter(context, movies);
        recyclerView.setAdapter(moviesAdapter);
    }

    public void setShow_MoviesStatus(int moviesStatus) {
        this.show_MoviesStatus = moviesStatus;
    }

    @Override
    public void onLoadingOfflineMoviesListner(List<MovieModel> movieModels) {
        movies = movieModels;
        dialog.hide();
        setAdapter(movieModels);
    }

    public interface MoviesClickListener {
        public void movieId(int id);
    }

}
