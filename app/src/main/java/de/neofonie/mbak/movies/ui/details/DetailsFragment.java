package de.neofonie.mbak.movies.ui.details;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import de.neofonie.mbak.movies.R;
import de.neofonie.mbak.movies.di.ActivityComponent;
import de.neofonie.mbak.movies.di.base.BaseFragment;
import de.neofonie.mbak.movies.modules.movies.Movie;
import org.parceler.Parcels;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends BaseFragment {

  @BindView(R.id.movie_teaser)      ImageView mMovieTeaser;
  @BindView(R.id.title_text)        TextView  mTitleText;
  @BindView(R.id.release_date_text) TextView  mReleaseDateText;
  @BindView(R.id.vote_text)         TextView  mVotesText;
  @BindView(R.id.synopsis_text)     TextView  mSynopsisText;

  private Movie mMovie;

  public DetailsFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment.
   *
   * @return A new instance of fragment MoviesGridFragment.
   */
  public static DetailsFragment newInstance() {
    DetailsFragment fragment = new DetailsFragment();
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_movie_details, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);
    if (mMovie != null) {
      mTitleText.setText(mMovie.getTitle());
      mReleaseDateText.setText(mMovie.getRelease_date());
      mVotesText.setText(String.format(Locale.getDefault(), "%2.2f", mMovie.getVote_average()));
      mSynopsisText.setText(mMovie.getOverview());
      Glide.with(getActivity())
          .load(mMovie.getTeaserPath())
          .fitCenter()
          .into(mMovieTeaser);
    }
  }

  @Override
  protected void inject(ActivityComponent component) {
    component.inject(this);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof Activity) {
      mMovie = Parcels.unwrap(((Activity) context).getIntent().getParcelableExtra(DetailsActivity.MOVIE_EXTRA));
    }
  }

}