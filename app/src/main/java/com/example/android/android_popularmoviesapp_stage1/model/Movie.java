package com.example.android.android_popularmoviesapp_stage1.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {
    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private String mTitle;
    private String mPosterUrl;
    private String mOverview;  //plot synopsis
    private float mVoteAverage;  //user rating
    private int mVoteCount; //vote count
    private String mReleaseDate;
    private String mBackdropPath;

    public Movie(String title, String posterUrl, String backdropPath, String releaseDate, String overview, float voteAverage, int voteCount) {
        mTitle = title;
        mPosterUrl = posterUrl;
        mBackdropPath = backdropPath;
        mReleaseDate = releaseDate;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mVoteCount = voteCount;
    }

    private Movie(Parcel in) {
        mTitle = in.readString();
        mPosterUrl = in.readString();
        mBackdropPath = in.readString();
        mReleaseDate = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readFloat();
        mVoteCount = in.readInt();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public float getVoteAverage() {
        return mVoteAverage;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTitle);
        out.writeString(mPosterUrl);
        out.writeString(mBackdropPath);
        out.writeString(mReleaseDate);
        out.writeString(mOverview);
        out.writeFloat(mVoteAverage);
        out.writeInt(mVoteCount);
    }
}