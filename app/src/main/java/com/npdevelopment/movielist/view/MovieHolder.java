package com.npdevelopment.movielist.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.npdevelopment.movielist.R;

public class MovieHolder extends RecyclerView.ViewHolder {

    public View view;
    public TextView popularRank;
    public ImageView movieThumbnail;

    public MovieHolder(View itemView) {
        super(itemView);
        popularRank = itemView.findViewById(R.id.popularRank);
        movieThumbnail = itemView.findViewById(R.id.movieImageCard);
        view = itemView;
    }
}
