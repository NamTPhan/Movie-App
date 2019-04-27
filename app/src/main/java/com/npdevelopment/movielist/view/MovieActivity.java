package com.npdevelopment.movielist.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.npdevelopment.movielist.R;
import com.npdevelopment.movielist.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    public static final String MOVIE_ITEM_KEY = "movieItemKey";
    private final int ITEMS_EACH_ROW = 2;
    private final int STARTING_PAGE = 1;

    private Button mSubmit;
    private EditText mYearInput;
    private MainViewModel mainViewModel;
    private RecyclerView rvMovieList;
    private GestureDetector mGestureDetector;
    private MovieAdapter mMovieAdapter;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mSubmit = findViewById(R.id.submitBtn);
        mYearInput = findViewById(R.id.yearInput);
        rvMovieList = findViewById(R.id.rv_movie_list);
        movieList = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(calculateNumberOfColumns(ITEMS_EACH_ROW), LinearLayoutManager.VERTICAL);

        rvMovieList.setLayoutManager(mLayoutManager);
        rvMovieList.setHasFixedSize(true);
        rvMovieList.addOnItemTouchListener(this);

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mYearInput.getText())) {
                    Toast.makeText(MovieActivity.this, getString(R.string.fields_required), Toast.LENGTH_LONG).show();
                } else {
                    mainViewModel.getMovies(STARTING_PAGE, mYearInput.getText().toString());
                }
            }
        });

        // Link the correct ViewModel to the activity
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        // Show error in toast if api call fails
        mainViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                Toast.makeText(MovieActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        // Dynamically update view
        mainViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieList = movies;
                updateUI();
            }
        });
    }

    private void updateUI() {
        if (mMovieAdapter == null) {
            mMovieAdapter = new MovieAdapter(movieList);
            rvMovieList.setAdapter(mMovieAdapter);
        } else {
            mMovieAdapter.refreshList(movieList);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View child = rvMovieList.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

        if (child != null) {
            int adapterPosition = rvMovieList.getChildAdapterPosition(child);

            if (mGestureDetector.onTouchEvent(motionEvent)) {
                Intent intent = new Intent(MovieActivity.this, DisplayMovieActivity.class);
                // Send the move that has to be shown
                intent.putExtra(MOVIE_ITEM_KEY, movieList.get(adapterPosition));
                startActivity(intent);
            }
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    // Custom method to calculate number of columns for grid type recycler view
    private int calculateNumberOfColumns(int base){
        int columns = base;
        String screenSize = getScreenSizeCategory();

        if(screenSize.equals("small")){
            if(base!=1){
                columns = columns-1;
            }
        }else if (screenSize.equals("normal")){
            // Do nothing
        }else if(screenSize.equals("large")){
            columns += 2;
        }else if (screenSize.equals("xlarge")){
            columns += 3;
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            columns = (int) (columns * 1.5);
        }

        return columns;
    }

    // Custom method to get screen size category
    private String getScreenSizeCategory(){
        int screenLayout = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenLayout){
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                // small screens are at least 426dp x 320dp
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                // normal screens are at least 470dp x 320dp
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                // large screens are at least 640dp x 480dp
                return "large";
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                // xlarge screens are at least 960dp x 720dp
                return "xlarge";
            default:
                return "undefined";
        }
    }
}
