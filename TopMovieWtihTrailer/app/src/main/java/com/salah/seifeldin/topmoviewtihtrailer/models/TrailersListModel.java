package com.salah.seifeldin.topmoviewtihtrailer.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohammed Salah on 02/11/2016.
 */


public class TrailersListModel {

    @SerializedName("results")
    @Expose
    private List<TrailerModel> trailers = new ArrayList<TrailerModel>();

    /**
     *
     * @return
     * The trailers
     */
    public List<TrailerModel> getTrailers() {
        return trailers;
    }

    /**
     *
     * @param trailers
     * The trailers
     */
    public void setTrailers(List<TrailerModel> trailers) {
        this.trailers = trailers;
    }

}