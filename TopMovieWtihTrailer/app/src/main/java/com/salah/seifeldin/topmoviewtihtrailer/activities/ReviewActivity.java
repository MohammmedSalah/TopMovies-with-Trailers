package com.salah.seifeldin.topmoviewtihtrailer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.salah.seifeldin.topmoviewtihtrailer.R;
import com.salah.seifeldin.topmoviewtihtrailer.adapters.ReviewAdapter;
import com.salah.seifeldin.topmoviewtihtrailer.interfaces.MovieApiServices;
import com.salah.seifeldin.topmoviewtihtrailer.models.ReviewModel;
import com.salah.seifeldin.topmoviewtihtrailer.models.ReviewsListModel;
import com.salah.seifeldin.topmoviewtihtrailer.retrofitnstance.RetrofitClient;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        int id = getIntent().getExtras().getInt("reviewId") ;
        getReviewData(id);
    }


    public void getReviewData(final int reviewId){
        MovieApiServices movieApiServices = RetrofitClient.getMoviesMovieApiServices() ;
        Call<ReviewsListModel> call  = movieApiServices.getReviews(reviewId , RetrofitClient.API_KEY) ;
        call.enqueue(new Callback<ReviewsListModel>() {
            @Override
            public void onResponse(Response<ReviewsListModel> response, Retrofit retrofit) {
                List<ReviewModel> reviewList = response.body().getreviewModels() ;
                Toast.makeText(getApplicationContext(), String.valueOf(reviewList.size()), Toast.LENGTH_SHORT).show();
                Log.d("ddddd" , String.valueOf(reviewId) + "   "+String.valueOf(reviewList.size())) ;
                ReviewAdapter adapter = new ReviewAdapter(getApplicationContext() , R.layout.row_rev , reviewList) ;
                ListView listView = (ListView) findViewById(R.id.reviewlist) ;
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
