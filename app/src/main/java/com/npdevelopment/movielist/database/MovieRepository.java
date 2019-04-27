package com.npdevelopment.movielist.database;

import com.npdevelopment.movielist.model.MovieWrapper;

import retrofit2.Call;

public class MovieRepository {

    private MovieApiService movieApiService = MovieApi.create();

    public Call<MovieWrapper> getMovies(int page, String year) {
        return movieApiService.getMovies(page, year);
    }
}
