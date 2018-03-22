package com.example.android.android_popularmoviesapp_stage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.android_popularmoviesapp_stage1.model.Movie;
import com.example.android.android_popularmoviesapp_stage1.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    @BindView(R.id.movie_poster_iv)
    ImageView posterView;
    @BindView(R.id.movie_title_tv)
    TextView movieTitle;
    @BindView(R.id.movie_desc_tv)
    TextView movieOverview;
    @BindView(R.id.movie_release_tv)
    TextView movieReleaseDate;
    @BindView(R.id.movie_vote_average_tv)
    TextView movieVoteAverage;
    @BindView(R.id.movie_vote_count_tv)
    TextView movieVoteCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent movieIntent = this.getIntent();

        if (movieIntent != null) {
            if (movieIntent.hasExtra("movie")) {
                Movie movie = movieIntent.getExtras().getParcelable("movie");
                ButterKnife.bind(this);
                // Display the current selected movie title on the Action Bar
                getSupportActionBar().setTitle(movie.getTitle());
                Log.v(TAG, "Detail Activity: " + movie.getPosterUrl());
                String movieUrl = NetworkUtils.POSTER_IMAGES_URL + movie.getBackdropPath();
                Picasso.with(posterView.getContext()).load(movieUrl)
                        .placeholder(R.drawable.movie_placeholder)
                        .error(R.mipmap.ic_launcher)
                        .into(posterView);

                movieTitle.setText(movie.getTitle());
                movieOverview.setText(movie.getOverview());
                movieReleaseDate.setText(movie.getReleaseDate());
                movieVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
                movieVoteCount.setText(String.valueOf(movie.getVoteCount()));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
          /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
