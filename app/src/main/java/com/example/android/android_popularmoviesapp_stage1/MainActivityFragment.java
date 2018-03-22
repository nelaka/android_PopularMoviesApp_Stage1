package com.example.android.android_popularmoviesapp_stage1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.android_popularmoviesapp_stage1.model.Movie;
import com.example.android.android_popularmoviesapp_stage1.utils.MoviesJsonUtils;
import com.example.android.android_popularmoviesapp_stage1.utils.MoviesPreferences;
import com.example.android.android_popularmoviesapp_stage1.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivityFragment extends Fragment implements MoviesAdapter.MoviesAdapterOnClickHandler, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;
    @BindView(R.id.movies_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.tv_error_message_display)
    TextView mErrorMessageDisplay;
    private MoviesAdapter mMoviesAdapter;
    private List<Movie> mMovies;

    private Context mContext;

    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, rootView);

        int noOfColumns = 4;
        GridLayoutManager layoutManager =
                new GridLayoutManager(mContext, noOfColumns);

        /* setLayoutManager associates the LayoutManager we created above with our RecyclerView */
        mRecyclerView.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);
        mMoviesAdapter = new MoviesAdapter(mContext, mMovies, this);
        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mMoviesAdapter);
        showLoading();

        new FetchMoviesTask().execute();
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra("movie", movie);

        mContext.startActivity(intent);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);

        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the loading indicator visible and hide the movies View and error
     * message.
     */
    private void showLoading() {
        /* Then, hide the weather data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Finally, show the loading indicator */
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PREFERENCES_HAVE_BEEN_UPDATED = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        // unregister the preference change listener
        sp.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (PREFERENCES_HAVE_BEEN_UPDATED) {
            Log.d(TAG, "onStart: preferences were updated");
            FetchMoviesTask task = new FetchMoviesTask();
            task.execute();
            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(String... params) {

            String sortBy = MoviesPreferences
                    .getPreferredSortBy(mContext);
            URL moviesRequestUrl = NetworkUtils.buildUrl(sortBy);
            try {

                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(moviesRequestUrl.toString());

                return MoviesJsonUtils
                        .parseMovieJson(jsonMovieResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> moviesData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (moviesData != null) {
                Log.v(TAG, "MoviesData: " + moviesData.get(0).getPosterUrl());
                showMovieDataView();

                mMoviesAdapter.setMoviesData(moviesData);
            } else {
                showErrorMessage();
            }
        }
    }
}
