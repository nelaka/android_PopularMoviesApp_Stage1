package com.example.android.android_popularmoviesapp_stage1.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NetworkUtils {

    /*
     * The sort field. One of stars, forks, or updated.
     * Default: results are sorted by best match if no field is specified.
     */
    public static final String SORT_BY_POPULARITY = "popular";
    public static final String SORT_BY_TOP_RATED = "top_rated";
    public static final String POSTER_IMAGES_URL = "http://image.tmdb.org/t/p/w342";
    private final static String API_KEY = "api_key";
    /************* INSERT YOUR API KEY HERE ********************/
    private final static String apiKey = "INSERT YOUR API KEY HERE";
    /************* INSERT YOUR API KEY HERE ********************/
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/3/movie";

    /**
     * Retrieves the proper URL to query for the weather data. The reason for both this method as
     * well as {@link #buildUrl(String)} is two fold.
     * <p>
     * 1) You should be able to just use one method when you need to create the URL within the
     * app instead of calling both methods.
     * 2) Later in Sunshine, you are going to add an alternate method of allowing the user
     * to select their preferred location. Once you do so, there will be another way to form
     * the URL using a latitude and longitude rather than just a location String. This method
     * will "decide" which URL to build and return it.
     *
     * @param context used to access other Utility methods
     * @return URL to query weather service
     */
    public static URL getUrl(Context context) {

        if (!MoviesPreferences.sortByPopular(context)) {
            return buildUrl(NetworkUtils.SORT_BY_TOP_RATED);

        } else {
            return buildUrl(NetworkUtils.SORT_BY_POPULARITY);
        }
    }

    /**
     * Builds the URL used to talk to the weather server using latitude and longitude of a
     * location.
     *
     * @param sortBy The latitude of the location
     * @return The Url to use to query the weather server.
     */
    public static URL buildUrl(String sortBy) {

        Uri popularMoviesQueryUri = Uri.parse(MOVIEDB_BASE_URL + "/" + sortBy).buildUpon()
                .appendQueryParameter(API_KEY, apiKey)
                .build();

        try {
            URL popularMoviesQueryUrl = new URL(popularMoviesQueryUri.toString());
            Log.v(TAG, "URL: " + popularMoviesQueryUrl);
            return popularMoviesQueryUrl;
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
