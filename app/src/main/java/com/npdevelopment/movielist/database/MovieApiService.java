package com.npdevelopment.movielist.database;

import com.npdevelopment.movielist.BuildConfig;
import com.npdevelopment.movielist.model.MovieWrapper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {

    String apiKey = BuildConfig.API_KEY;

    @GET("/3/discover/movie?api_key=" + apiKey + "&language=en-US&sort_by=popularity.asc")
    Call<MovieWrapper> getMovies(@Query("page") int page, @Query("year") String year);
}
