package de.neofonie.mbak.movies.ui.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.neofonie.mbak.movies.R;
import de.neofonie.mbak.movies.di.ActivityComponent;
import de.neofonie.mbak.movies.di.base.BaseFragment;
import de.neofonie.mbak.movies.modules.movies.MoviesManager;
import de.neofonie.mbak.movies.modules.movies.MoviesResponse;
import de.neofonie.mbak.movies.modules.preferences.PreferencesManager;
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
public class MoviesGridFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

  @BindView(R.id.recycler_view)  RecyclerView mRecyclerView;
  @BindView(R.id.loading_layout) View         mLoadingLayout;

  @Inject MoviesManager      mMoviesManager;
  @Inject PreferencesManager mPrefsManager;

  private GridLayoutManager             mLayoutManager;
  private MoviesAdapter                 mAdapter;
  private MoviesResponse                mResponse;
  private RecyclerView.OnScrollListener mScrollListener;

  private boolean    mLoading    = false;
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
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
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

    mScrollListener = new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int totalItemCount = mLayoutManager.getItemCount();
        int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

        if (lastVisibleItem > totalItemCount - 2) {
          if (mResponse.getPage() < mResponse.getTotal_pages()) {
            subscribeForPage(mResponse.getPage() + 1);
          }
        }
      }
    };

    mRecyclerView.addOnScrollListener(mScrollListener);
    subscribeForPage(null);
  }

  @Override
  public void onDestroyView() {
    mRecyclerView.removeOnScrollListener(mScrollListener);
    super.onDestroyView();
  }

  @Override
  protected void inject(ActivityComponent component) {
    component.inject(this);
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  private void subscribeForPage(Integer page) {
    if (mLoading) {
      return;
    }
    if (page == null) { // only when requesting first page
      mLoadingLayout.setVisibility(View.VISIBLE);
    }
    mLoading = true;
    mDisposable.dispose();
    mDisposable = mMoviesManager
        .getMovies(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new BiConsumer<MoviesResponse, Throwable>() {
          @Override
          public void accept(MoviesResponse moviesResponse, Throwable throwable) throws Exception {
            mLoading = false;
            if (throwable != null) {
              handleError(throwable);
            } else {
              handleResponse(moviesResponse);
            }
          }
        });
  }

  private void handleResponse(MoviesResponse moviesResponse) {
    mResponse = moviesResponse;
    if (mResponse.getPage() > 1) {
      mAdapter.nextPage(moviesResponse.getResults());
    } else {
      mAdapter.setData(moviesResponse.getResults());
      mLoadingLayout.setVisibility(View.GONE);
    }
  }

  private void handleError(Throwable throwable) {

  }

  @Override
  public void onStop() {
    super.onStop();
    mDisposable.dispose();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.details_menu, menu);
    MenuItem item = menu.findItem(R.id.action_sort);
    Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
        R.array.sort_options, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner.setAdapter(adapter); // set the adapter to provide layout of rows and content
    spinner.setSelection(mPrefsManager.getSelectedSortType(), false);
    spinner.setOnItemSelectedListener(this); // set the listener, to perform actions based on item selection
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    mPrefsManager.setSelectedSortType(position);
    subscribeForPage(null);
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {

  }

}
