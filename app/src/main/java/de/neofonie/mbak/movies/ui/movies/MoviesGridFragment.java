package de.neofonie.mbak.movies.ui.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.neofonie.mbak.movies.R;
import de.neofonie.mbak.movies.di.ActivityComponent;
import de.neofonie.mbak.movies.di.base.BaseFragment;
import de.neofonie.mbak.movies.modules.MoviesManager;
import de.neofonie.mbak.movies.modules.MoviesResponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviesGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesGridFragment extends BaseFragment {

  @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

  @Inject MoviesManager mMoviesManager;

  private GridLayoutManager mLayoutManager;
  private MoviesAdapter     mAdapter;

  private Disposable mDisposable = Disposables.disposed();

  public MoviesGridFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment
   *
   * @return A new instance of fragment MoviesGridFragment.
   */
  public static MoviesGridFragment newInstance() {
    MoviesGridFragment fragment = new MoviesGridFragment();
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_movies_grid, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mUnbinder = ButterKnife.bind(this, view);
    mLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
    mRecyclerView.setLayoutManager(mLayoutManager);

    mAdapter = new MoviesAdapter();
    mRecyclerView.setAdapter(mAdapter);
  }

  @Override
  protected void inject(ActivityComponent component) {
    component.inject(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    mDisposable = mMoviesManager
        .getMovies()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new BiConsumer<MoviesResponse, Throwable>() {
          @Override
          public void accept(MoviesResponse moviesResponse, Throwable throwable) throws Exception {
            if (throwable != null) {
              handleError(throwable);
            } else {
              handleResponse(moviesResponse);
            }
          }
        });
  }

  private void handleResponse(MoviesResponse moviesResponse) {
    mAdapter.clearData();
    mAdapter.nextPage(moviesResponse.getResults());
  }

  private void handleError(Throwable throwable) {

  }

  @Override
  public void onStop() {
    super.onStop();
    mDisposable.dispose();
  }
}
