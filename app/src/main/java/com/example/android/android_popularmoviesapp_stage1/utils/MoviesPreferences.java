package com.example.android.android_popularmoviesapp_stage1.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.android.android_popularmoviesapp_stage1.R;


public class MoviesPreferences {
    private static final String TAG = com.example.android.android_popularmoviesapp_stage1.utils.NetworkUtils.class.getSimpleName();

    /**
     * Returns true if the user has selected metric temperature display.
     *
     * @param context Context used to get the SharedPreferences
     * @return true if metric display should be used, false if imperial display should be used
     */
    public static boolean sortByPopular(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String keyForSortBy = context.getString(R.string.pref_sort_by_key);
        String defaultSortBy = context.getString(R.string.pref_sort_by_default);
        String preferredSortBy = sp.getString(keyForSortBy, defaultSortBy);
        String popular = context.getString(R.string.pref_sort_by_popular);

        boolean userPrefersPopular = false;
        if (popular.equals(preferredSortBy)) {
            userPrefersPopular = true;
        }

        return userPrefersPopular;
    }

    public static String getPreferredSortBy(Context context) {

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(context);
        String keyForSortBy = context.getString(R.string.pref_sort_by_key);
        String defaultSortBy = context.getString(R.string.pref_sort_by_default);
        return sp.getString(keyForSortBy, defaultSortBy);
    }
}
