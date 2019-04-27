package com.npdevelopment.movielist.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.npdevelopment.movielist.database.MovieRepository;
import com.npdevelopment.movielist.model.Movie;
import com.npdevelopment.movielist.model.MovieWrapper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {

    private MovieRepository mMovieRepository = new MovieRepository();

    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> mMovie = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<List<Movie>> getAllMovies() {
        return mMovie;
    }

    public void getMovies(int page, String year) {
        mMovieRepository.getMovies(page, year).enqueue(new Callback<MovieWrapper>() {
            @Override
            public void onResponse(Call<MovieWrapper> call, Response<MovieWrapper> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mMovie.setValue(response.body().getMovieList());
                } else {
                    error.setValue("Api Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieWrapper> call, Throwable t) {
                error.setValue("Api Error: " + t.getMessage());
            }
        });
    }
}
