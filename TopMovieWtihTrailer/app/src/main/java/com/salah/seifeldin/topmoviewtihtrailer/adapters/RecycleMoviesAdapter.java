package com.salah.seifeldin.topmoviewtihtrailer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.salah.seifeldin.topmoviewtihtrailer.R;
import com.salah.seifeldin.topmoviewtihtrailer.models.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mohammed Salah on 29/10/2016.
 */

public class RecycleMoviesAdapter extends RecyclerView.Adapter<RecycleMoviesAdapter.ViewHolder>{
    public void setMovieModelList(List<MovieModel> movieModelList) {
        this.movieModelList = movieModelList;
        notifyDataSetChanged();
    }

    List<MovieModel> movieModelList ;
    Context context ;


    public RecycleMoviesAdapter(Context context , List<MovieModel> movieModelList){
        this.movieModelList = movieModelList ;
        this.context = context ;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row_movieslist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w185/"+movieModelList.get(position).getPosterPath())
                .placeholder(R.drawable.loadimage)
                .resize(185,278)
                .into(holder.posterImage) ;

    }

    @Override
    public int getItemCount() {
        return movieModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView posterImage  ;

        public ViewHolder(View itemView) {
            super(itemView);

            posterImage = (ImageView) itemView.findViewById(R.id.image_posterpath);

        }
    }
}
