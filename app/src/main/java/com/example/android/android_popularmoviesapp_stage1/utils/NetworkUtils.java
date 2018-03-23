package com.example.android.android_popularmoviesapp_stage1.utils;

import android.net.Uri;

import com.example.android.android_popularmoviesapp_stage1.BuildConfig;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private final static String THE_MOVIEDB_API_KEY = "api_key";
    private final static String theMovieDbApiKey = BuildConfig.THEMOVIEDB_API_KEY;
    private static final String THE_MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/movie";

    /**
     * Builds the URL used to talk to the movie database server using sorting parameter
     *
     * @param sortBy The sorting preference
     * @return The Url to use to query the movie database server.
     */
    public static URL buildUrl(String sortBy) {

        Uri popularMoviesQueryUri = Uri.parse(THE_MOVIEDB_BASE_URL + "/" + sortBy).buildUpon()
                .appendQueryParameter(THE_MOVIEDB_API_KEY, theMovieDbApiKey)
                .build();

        try {
            return new URL(popularMoviesQueryUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}