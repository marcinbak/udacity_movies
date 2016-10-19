/*
 * (c) Neofonie Mobile GmbH (2016)
 *
 * This computer program is the sole property of Neofonie Mobile GmbH (http://mobile.neofonie.de)
 * and is protected under the German Copyright Act (paragraph 69a UrhG).
 *
 * All rights are reserved. Making copies, duplicating, modifying, using or distributing
 * this computer program in any form, without prior written consent of Neofonie Mobile GmbH, is prohibited.
 * Violation of copyright is punishable under the German Copyright Act (paragraph 106 UrhG).
 *
 * Removing this copyright statement is also a violation.
 */
package de.neofonie.mbak.movies.ui.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.picasso.Picasso;
import de.neofonie.mbak.movies.R;
import de.neofonie.mbak.movies.modules.movies.Movie;
import de.neofonie.mbak.movies.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcinbak on 18/10/2016.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

  final List<Movie> mMovies;

  public MoviesAdapter() {
    mMovies = new ArrayList<>();
  }

  @Override
  public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_grid_item, parent, false);
    return new MovieViewHolder(view);
  }

  @Override
  public void onBindViewHolder(MovieViewHolder holder, int position) {
    holder.bind(mMovies.get(position));
  }

  @Override
  public int getItemCount() {
    return mMovies.size();
  }

  public void nextPage(List<Movie> movies) {
    int previousSize = mMovies.size();
    mMovies.addAll(movies);

    notifyItemRangeInserted(previousSize, movies.size());
  }

  @Override
  public void onViewRecycled(MovieViewHolder holder) {
//    Glide.clear(holder.mTeaserImageView);
    Picasso.with(holder.mContext).cancelRequest(holder.mTeaserImageView);
  }

  @Override
  public boolean onFailedToRecycleView(MovieViewHolder holder) {
//    Glide.clear(holder.mTeaserImageView);
    Picasso.with(holder.mContext).cancelRequest(holder.mTeaserImageView);
    return super.onFailedToRecycleView(holder);
  }

  public void setData(List<Movie> results) {
    mMovies.clear();
    mMovies.addAll(results);
    notifyDataSetChanged();
  }

  public static class MovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.movie_teaser) ImageView mTeaserImageView;

    private Movie   mModel;
    private Context mContext;

    public MovieViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      mContext = mTeaserImageView.getContext();
    }

    public void bind(Movie movie) {
      mModel = movie;
      Picasso.with(mContext).cancelRequest(mTeaserImageView);
      Picasso.with(mContext)
          .load(movie.getTeaserPath())
          .fit()
          .placeholder(R.drawable.movie_placeholder)
          .into(mTeaserImageView);

//      Glide.clear(mTeaserImageView);
//      Glide.with(mContext)
//          .load(movie.getTeaserPath())
//          .centerCrop()
//          .into(mTeaserImageView);
    }

    @OnClick(R.id.movie_teaser)
    public void clickTeaser() {
      if (mModel != null) {
        DetailsActivity.start(mContext, mModel);
      }
    }

  }

}