/*
 * (c) Neofonie Mobile GmbH (2017)
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
package de.neofonie.mbak.movies.ui.widgets;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import de.neofonie.mbak.movies.R;
import de.neofonie.mbak.movies.modules.movies.Movie;

import java.util.Locale;

/**
 * Created by marcinbak on 17/01/2017.
 */
public class SummaryHolder extends TypedViewHolder<Movie> {

  private Target<GlideDrawable> request;

  @BindView(R.id.movie_teaser)      ImageView mMovieTeaser;
  @BindView(R.id.title_text)        TextView  mTitleText;
  @BindView(R.id.release_date_text) TextView  mReleaseDateText;
  @BindView(R.id.vote_text)         TextView  mVotesText;
  @BindView(R.id.synopsis_text)     TextView  mSynopsisText;

  public SummaryHolder(ViewGroup parent) {
    super(R.layout.movie_details_li, parent);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void bind(Movie movie) {
    recycleRequest();
    mTitleText.setText(movie.getTitle());
    mReleaseDateText.setText(movie.getRelease_date());
    mVotesText.setText(String.format(Locale.getDefault(), "%2.2f", movie.getVote_average()));
    mSynopsisText.setText(movie.getOverview());

    this.request = Glide.with(context)
        .load(movie.getTeaserPath())
        .placeholder(R.drawable.movie_placeholder)
        .error(R.drawable.movie_placeholder_error)
        .fitCenter()
        .into(mMovieTeaser);
  }

  @Override
  public void recycleView() {
    recycleRequest();
  }

  private void recycleRequest() {
    if (request != null) {
      request.getRequest().clear();//recycle();
    }
  }

  public static TypedViewHolderFactory<Movie> factory() {
    return new TypedViewHolderFactory<Movie>(Movie.class) {
      @Override
      public TypedViewHolder<Movie> build(ViewGroup parent) {
        return new SummaryHolder(parent);
      }
    };
  }
}