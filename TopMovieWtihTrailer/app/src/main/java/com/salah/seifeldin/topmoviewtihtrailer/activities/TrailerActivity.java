package com.salah.seifeldin.topmoviewtihtrailer.activities;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.salah.seifeldin.topmoviewtihtrailer.interfaces.MovieApiServices;
import com.salah.seifeldin.topmoviewtihtrailer.models.TrailerModel;
import com.salah.seifeldin.topmoviewtihtrailer.models.TrailersListModel;
import com.salah.seifeldin.topmoviewtihtrailer.retrofitnstance.RetrofitClient;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TrailerActivity extends ListActivity {

    ListView listView ;
    ArrayAdapter<String> adapter;
    List<TrailerModel> trailerModel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        listView = getListView();

        int movieId = getIntent().getExtras().getInt("movieId") ;
        getTrailers(movieId);
    }

    public void getTrailers(final int movieId){
        MovieApiServices movieApiServices = RetrofitClient.getMoviesMovieApiServices() ;
        Call<TrailersListModel> call = movieApiServices.getTrailers(movieId , RetrofitClient.API_KEY) ;
        call.enqueue(new Callback<TrailersListModel>() {
            @Override
            public void onResponse(Response<TrailersListModel> response, Retrofit retrofit) {
                trailerModel =  response.body().getTrailers();
                Log.d("number "  ,  String.valueOf(trailerModel.size()) ) ;
                Log.d("id "  ,  String.valueOf(movieId) ) ;
                getStringData(trailerModel);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("error " , t.getMessage()) ;
            }
        });

    }
    public void getStringData(List<TrailerModel> trailerModelList){
        String[] data  = new String[trailerModelList.size()];
        for(int i=0 ;i<trailerModelList.size() ; i++){
            data[i] = "Trailer"+String.valueOf(i+1)+ " => " + trailerModelList.get(i).getName() ;
        }
        adapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 ,
                data){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.RED);
                return view;
            }
        } ;

        listView.setAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerModel.get(position).getKey())) ;
            startActivity(intent);
        }
        catch (ActivityNotFoundException ex){
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + trailerModel.get(position).getKey()));
            startActivity(intent);
        }
    }
}