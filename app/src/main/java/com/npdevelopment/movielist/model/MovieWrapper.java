package com.npdevelopment.movielist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieWrapper {

    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int total_results;

    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("results")
    private List<Movie> movieList;

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
