package com.dobrucali.bestmovies.ui.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dobrucali.bestmovies.R;
import com.dobrucali.bestmovies.data.database.MovieEntry;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500/";
    private final Context mContext;
    private List<MovieEntry> mMovieEntryList;
    private MovieItemClickListener mClickListener;

    public MovieAdapter(Context context, List<MovieEntry> movieEntryList, MovieItemClickListener clickListener) {
        mContext = context;
        mMovieEntryList = movieEntryList;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_movie, viewGroup, false);
        view.setFocusable(true);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieAdapterViewHolder holder, int position) {
        MovieEntry movieEntry = mMovieEntryList.get(position);

        if(movieEntry.getPosterPath() != null){
            Picasso.get().load(IMAGE_URL.concat(movieEntry.getPosterPath()))
                    .into(holder.posterImage);
        }

        if(movieEntry.getVoteAverage() != null){
            String voteAverage = mContext.getString(R.string.vote_average).concat(String.valueOf(movieEntry.getVoteAverage()));
            holder.voteAverage.setText(voteAverage);
        }

        if(movieEntry.getVoteCount() != null) {
            String voteCount = mContext.getString(R.string.vote_count).concat(String.valueOf(movieEntry.getVoteCount()));
            holder.voteCount.setText(voteCount);
        }

       if(movieEntry.getTitle() != null){
           holder.title.setText(movieEntry.getTitle());
       }

    }

    @Override
    public int getItemCount() {
        if (mMovieEntryList == null){
            return 0;
        } else {
            return mMovieEntryList.size();
        }
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ConstraintLayout layout;
        ImageView posterImage;
        TextView title;
        TextView voteAverage;
        TextView voteCount;

        public MovieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.movie_layout);
            posterImage = itemView.findViewById(R.id.movie_poster_image);
            title = itemView.findViewById(R.id.movie_title);
            voteAverage = itemView.findViewById(R.id.vote_average);
            voteCount = itemView.findViewById(R.id.vote_count);

            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Integer movieId = mMovieEntryList.get(adapterPosition).getId();
            mClickListener.onItemClick(movieId);
        }
    }

    void swapForecast(List<MovieEntry> newMovieList) {

        if (mMovieEntryList == null) {
            mMovieEntryList = newMovieList;
            notifyDataSetChanged();
        } else {

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mMovieEntryList.size();
                }

                @Override
                public int getNewListSize() {
                    return newMovieList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mMovieEntryList.get(oldItemPosition).getId() ==
                            newMovieList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    MovieEntry newWeather = newMovieList.get(newItemPosition);
                    MovieEntry oldWeather = mMovieEntryList.get(oldItemPosition);
                    return newWeather.getId() == oldWeather.getId();
                }
            });
            mMovieEntryList = newMovieList;
            result.dispatchUpdatesTo(this);
        }
    }
}
