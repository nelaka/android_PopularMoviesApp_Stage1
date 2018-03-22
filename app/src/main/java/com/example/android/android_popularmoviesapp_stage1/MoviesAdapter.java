package com.example.android.android_popularmoviesapp_stage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_popularmoviesapp_stage1.model.Movie;
import com.example.android.android_popularmoviesapp_stage1.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    /* The context we use to utility methods, app resources and layout inflaters */
    private final Context mContext;
    private final MoviesAdapterOnClickHandler mClickHandler;
    private List<Movie> mMovies;

    public MoviesAdapter(@NonNull Context context, List<Movie> movies, MoviesAdapterOnClickHandler clickHandler) {
        mContext = context;
        mMovies = movies;
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public MoviesAdapter.MoviesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, viewGroup, false);

        view.setFocusable(true);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MoviesAdapterViewHolder moviesAdapterViewHolder, int position) {
        Movie movie = mMovies.get(position);
        Log.v(TAG, "onBindViewHolder poster: " + movie.getPosterUrl());
        String movieUrl = NetworkUtils.POSTER_IMAGES_URL + movie.getPosterUrl();
        Picasso.with(moviesAdapterViewHolder.posterView.getContext()).load(movieUrl)
                .placeholder(R.drawable.movie_placeholder)
                .error(R.mipmap.ic_launcher)
                .into(moviesAdapterViewHolder.posterView);
    }

    @Override
    public int getItemCount() {

        if (null == mMovies) return 0;
        return mMovies.size();
    }

    /**
     * This method is used to set the movies on a MoviesAdapter if we've already
     * created one.
     *
     * @param moviesData The new movie data to be displayed.
     */
    public void setMoviesData(List<Movie> moviesData) {

        mMovies = moviesData;
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface MoviesAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.list_item_poster)
        ImageView posterView;

        public MoviesAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();

            mClickHandler.onClick(mMovies.get(adapterPosition));
        }
    }
}
