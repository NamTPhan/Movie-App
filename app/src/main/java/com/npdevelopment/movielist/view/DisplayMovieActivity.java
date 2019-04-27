package com.npdevelopment.movielist.view;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.npdevelopment.movielist.R;
import com.npdevelopment.movielist.database.MovieApi;
import com.npdevelopment.movielist.model.Movie;
import com.squareup.picasso.Picasso;

public class DisplayMovieActivity extends AppCompatActivity {

    private ImageView backdrop, poster;
    private TextView title, releaseDate, overview, rating;
    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_display_movie);

        // Enable back arrow in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        backdrop = findViewById(R.id.backdropImage);
        poster = findViewById(R.id.posterImage);
        title = findViewById(R.id.movieTitle);
        releaseDate = findViewById(R.id.movieRelease);
        overview = findViewById(R.id.movieDescription);
        rating = findViewById(R.id.movieRating);

        initializeMovieData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle back arrow
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to previous activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeMovieData() {
        mMovie = getIntent().getExtras().getParcelable(MovieActivity.MOVIE_ITEM_KEY);

        rating.setText(String.valueOf(mMovie.getVoteAverage()));
        title.setText(mMovie.getTitle());

        Picasso.get().load(MovieApi.IMAGE_BASE_URL + mMovie.getPosterPath()).into(poster);

        if (mMovie.getBackdropPath() == null) {
            backdrop.setImageResource(R.drawable.image_not_found);
        } else {
            Picasso.get().load(MovieApi.IMAGE_BASE_URL + mMovie.getBackdropPath()).into(backdrop);
        }

        overview.setText(mMovie.getOverview());
        releaseDate.setText(getString(R.string.placeholder_release) + " " + mMovie.getReleaseDate());
    }
}
