package de.neofonie.mbak.movies.ui.details;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.ShareActionProvider;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.neofonie.mbak.movies.R;
import de.neofonie.mbak.movies.di.ActivityComponent;
import de.neofonie.mbak.movies.di.base.BaseFragment;
import de.neofonie.mbak.movies.modules.movies.Movie;
import de.neofonie.mbak.movies.modules.movies.MovieReview;
import de.neofonie.mbak.movies.modules.movies.MovieTrailer;
import de.neofonie.mbak.movies.modules.movies.MoviesManager;
import de.neofonie.mbak.movies.modules.movies.provider.FavoriteMoviesManager;
import de.neofonie.mbak.movies.ui.widgets.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import org.parceler.Parcels;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends BaseFragment {

  private static final String TAG = "DetailsFragment";

  @BindView(R.id.recycler_view) RecyclerView mRecycler;

  @Inject MoviesManager         mMoviesManager;
  @Inject FavoriteMoviesManager mFavoriteMoviesManager;

  private Movie                          mMovie;
  private TypedViewHolderAdapter<Object> mAdapter;
  private ShareActionProvider            mShareActionProvider;

  private Disposable mDisposable              = Disposables.disposed();
  private Disposable mFavoriteCheckDisposable = Disposables.disposed();
  private MenuItem mShareItem;

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
    mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    if (mMovie != null) {
      mAdapter = new TypedViewHolderAdapter.Builder<>()
          .addFactory(SummaryHolder.factory())
          .addFactory(HeaderHolder.factory())
          .addFactory(TrailerHolder.factory(new TrailerHolder.TrailerClickedListener() {
            @Override
            public void onTrailerClick(MovieTrailer trailer) {
              String videoUrl = getString(R.string.youtube_url, trailer.getKey());
              startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)));
            }
          }))
          .addFactory(ReviewHolder.factory())
          .build();

      mAdapter.setData(new ArrayList<Object>(Collections.singletonList(mMovie)));
      mRecycler.setAdapter(mAdapter);
    }
  }

  @Override
  public void onStart() {
    super.onStart();

    if (mMovie != null && mAdapter != null && mAdapter.getItemCount() <= 1) {
      mMoviesManager.getVideos(mMovie)
          .zipWith(mMoviesManager.getReviews(mMovie), new BiFunction<List<MovieTrailer>, List<MovieReview>, List<Object>>() {
            @Override
            public List<Object> apply(List<MovieTrailer> movieTrailers, List<MovieReview> movieReviews) throws Exception {
              setShareIntent(movieTrailers);

              ArrayList<Object> completeList = new ArrayList<>(movieReviews.size() + movieTrailers.size() + 3);
              completeList.add(mMovie);
              completeList.add("Trailers");
              completeList.addAll(movieTrailers);
              completeList.add("Reviews");
              completeList.addAll(movieReviews);
              return completeList;
            }
          })
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new BiConsumer<List<Object>, Throwable>() {
            @Override
            public void accept(List<Object> objects, Throwable throwable) throws Exception {
              loadData(objects);
            }
          });
    }
  }

  private void loadData(List<Object> objects) {
    mAdapter.setData(objects);
    mAdapter.notifyItemRangeInserted(1, objects.size() - 1);
  }

  @Override
  public void onStop() {
    super.onStop();
    mDisposable.dispose();
    mFavoriteCheckDisposable.dispose();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.details_menu, menu);
    MenuItem item = menu.findItem(R.id.favorite_toggle);
    setFavoriteStateIcon(item);

    mShareItem = menu.findItem(R.id.menu_item_share);
    mShareActionProvider = (ShareActionProvider) item.getActionProvider();
  }

  private void setShareIntent(List<MovieTrailer> movieTrailers) {
    if (mShareActionProvider != null && movieTrailers.size() > 0) {
      Intent shareIntent = new Intent(Intent.ACTION_SEND);
      String url = getString(R.string.youtube_url, movieTrailers.get(0).getKey());
      shareIntent.putExtra(Intent.EXTRA_TEXT, url);
      mShareActionProvider.setShareIntent(shareIntent);

      mShareItem.setVisible(true);
    } else if (movieTrailers.size() == 0) {
      mShareItem.setVisible(false);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.favorite_toggle) {
      toggleFavorite(item);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void toggleFavorite(final MenuItem item) {
    if (mMovie != null) {
      mFavoriteCheckDisposable.dispose();
      mFavoriteCheckDisposable = mFavoriteMoviesManager.toggleFavorite(mMovie)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new BiConsumer<Boolean, Throwable>() {
            @Override
            public void accept(Boolean favorite, Throwable throwable) throws Exception {
              if (favorite != null) {
                item.setIcon(favorite ? R.drawable.star : R.drawable.star_outline);
              } else {
                Log.e(TAG, "toggleFavorite failed", throwable);
                Toast.makeText(getContext(), R.string.error_favorite_toggle, Toast.LENGTH_SHORT).show();
              }
            }
          });
    }
  }

  private void setFavoriteStateIcon(final MenuItem item) {
    if (mMovie != null) {
      mFavoriteCheckDisposable = mFavoriteMoviesManager.isFavorite(mMovie)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new BiConsumer<Boolean, Throwable>() {
            @Override
            public void accept(Boolean favorite, Throwable throwable) throws Exception {
              if (favorite != null) {
                item.setIcon(favorite ? R.drawable.star : R.drawable.star_outline);
              } else {
                Log.e(TAG, "setFavoriteStateIcon failed", throwable);
                Toast.makeText(getContext(), R.string.error_favorite_state_check, Toast.LENGTH_SHORT).show();
              }
            }
          });
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

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }
}