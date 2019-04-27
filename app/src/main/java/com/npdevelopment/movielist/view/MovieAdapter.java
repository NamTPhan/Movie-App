package com.npdevelopment.movielist.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.npdevelopment.movielist.R;
import com.npdevelopment.movielist.database.MovieApi;
import com.npdevelopment.movielist.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

    public List<Movie> movieObjectList;

    public MovieAdapter(List<Movie> movieObjectList) {
        this.movieObjectList = movieObjectList;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_movie_cell, viewGroup, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        // Gets a single item in the list from its position
        final Movie movieObject = movieObjectList.get(position);

        holder.popularRank.setText(String.valueOf(++position) + ".");

        if (movieObject.getPosterPath() == null) {
            holder.movieThumbnail.setImageResource(R.drawable.image_not_found);
        } else {
            Picasso.get().load(MovieApi.IMAGE_BASE_URL + movieObject.getPosterPath()).into(holder.movieThumbnail);
        }
    }

    /**
     * Method for refreshing the data
     *
     * @param newList the list that has to be refreshed
     */
    public void refreshList(List<Movie> newList) {
        movieObjectList = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return movieObjectList.size();
    }

}
