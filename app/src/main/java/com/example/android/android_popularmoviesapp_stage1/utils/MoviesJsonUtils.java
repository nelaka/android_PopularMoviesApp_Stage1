package com.example.android.android_popularmoviesapp_stage1.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.android_popularmoviesapp_stage1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions to handle movies JSON data.
 */
public final class MoviesJsonUtils {

    private static final String JSON_RESULTS_KEY = "results";
    private static final String JSON_TITLE_KEY = "title";
    private static final String JSON_POSTER_PATH_KEY = "poster_path";
    private static final String JSON_BACKDROP_PATH_KEY = "backdrop_path";
    private static final String JSON_DESCRIPTION_KEY = "overview";
    private static final String JSON_ID_KEY = "id";
    private static final String JSON_RELEASE_DATE_KEY = "release_date";
    private static final String JSON_VOTE_AVERAGE_KEY = "vote_average";
    private static final String JSON_VOTE_COUNT_KEY = "vote_count";

    public static List<Movie> parseMovieJson(String json) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding movies to
        List<Movie> movies = new ArrayList<>();

        // Create a JSONObject from the JSON response string
        JSONObject baseJsonResponse;
        try {
            baseJsonResponse = new JSONObject(json);
            if (baseJsonResponse.has(JSON_RESULTS_KEY)) {
                // Extract the JSONArray associated with the key called results
                JSONArray movieResultsArray = baseJsonResponse.getJSONArray(JSON_RESULTS_KEY);

                // For each result item in the movieResultsArray
                for (int i = 0; i < movieResultsArray.length(); i++) {
                    JSONObject currentMovie = movieResultsArray.getJSONObject(i);
                    String title = currentMovie.optString(JSON_TITLE_KEY);
                    String posterPath = currentMovie.optString(JSON_POSTER_PATH_KEY);
                    int id = currentMovie.optInt(JSON_ID_KEY);
                    String overview = currentMovie.optString(JSON_DESCRIPTION_KEY);
                    String backdropPath = currentMovie.optString(JSON_BACKDROP_PATH_KEY);
                    String releaseDate = currentMovie.optString(JSON_RELEASE_DATE_KEY);
                    float voteAverage = currentMovie.optInt(JSON_VOTE_AVERAGE_KEY);
                    int voteCount = currentMovie.optInt(JSON_VOTE_COUNT_KEY);

                    /* Create a new {@link Movie} object with the title, posterPath, backdropPath,
                    releaseDate, overview, voteAverage and voteCount from the JSON response.
                    */
                    Movie movie = new Movie(title, posterPath, backdropPath, releaseDate, overview, voteAverage, voteCount);
                    // Add the new {@link Movie} to the list of movies
                    movies.add(movie);
                }
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("JsonUtils", "Problem parsing the movie JSON results", e);
        }
        return movies;
    }
}