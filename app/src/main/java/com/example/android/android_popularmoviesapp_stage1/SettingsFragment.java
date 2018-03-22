package com.example.android.android_popularmoviesapp_stage1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.example.android.android_popularmoviesapp_stage1.utils.NetworkUtils;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = NetworkUtils.class.getSimpleName();

//    Preference.OnPreferenceChangeListener onPreferenceChangeListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    private void setPreferenceSummary(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Add 'general' preferences, defined in the XML file
        addPreferencesFromResource(R.xml.pref_general);
        SharedPreferences mSortByPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Preference preference = findPreference(getString(R.string.pref_sort_by_key));

        if (!(preference instanceof CheckBoxPreference)) {
            rootKey = mSortByPref.getString(preference.getKey(), "missing");
            //  Log.v(TAG, "value: " + rootKey);
            setPreferenceSummary(preference, rootKey);
        }
        //preference.setOnPreferenceChangeListener(onPreferenceChangeListener);
        onSharedPreferenceChanged(mSortByPref, mSortByPref.getString(preference.getKey(), "missing"));
    }

    @Override
    public void onStop() {
        super.onStop();
        // unregister the preference change listener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // register the preference change listener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (null != preference) {
            if (!(preference instanceof CheckBoxPreference)) {
                setPreferenceSummary(preference, sharedPreferences.getString(key, "missing"));
            }
        }
    }
}