package com.salah.seifeldin.topmoviewtihtrailer.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.salah.seifeldin.topmoviewtihtrailer.R;
import com.salah.seifeldin.topmoviewtihtrailer.models.ReviewModel;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Mohammed Salah on 02/11/2016.
 */

public class ReviewAdapter extends ArrayAdapter<ReviewModel> {
    List<ReviewModel> reviewModelList ;
    Context context ;
    int layout_resource_Id  ;

    TextView reviewAuthor  , reviewContent;
    ImageView img_readFullReview ;


    public ReviewAdapter(Context context, int resource, List<ReviewModel> objects) {
        super(context, resource, objects);
        this.context = context ;
        this.layout_resource_Id = resource ;
        this.reviewModelList = objects  ;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view =  convertView ;
        LayoutInflater  inflater = LayoutInflater.from(context) ;
        view = inflater.inflate(this.layout_resource_Id , parent , false) ;
        reviewAuthor = (TextView) view.findViewById(R.id.reviewauther) ;
        reviewContent = (TextView) view.findViewById(R.id.reviewcontent) ;
        img_readFullReview = (ImageView) view.findViewById(R.id.imag_readfillreview) ;

        reviewAuthor.setText(reviewModelList.get(position).getAuthor());
        reviewContent.setText(reviewModelList.get(position).getContent());

        img_readFullReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFullReview(reviewModelList.get(position).getUrl());
            }
        });


        return view ;
    }

    public  void readFullReview(String url){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
        context.startActivity(intent);
    }

}
