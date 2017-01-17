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
package de.neofonie.mbak.movies.modules.movies;

import de.neofonie.mbak.movies.BuildConfig;
import de.neofonie.mbak.movies.di.scopes.ApplicationScope;
import de.neofonie.mbak.movies.modules.movies.provider.FavoriteMoviesManager;
import de.neofonie.mbak.movies.modules.preferences.PreferencesManager;
import io.reactivex.Single;
import io.reactivex.functions.Function;

import javax.inject.Inject;
import java.util.List;
import java.util.Vector;

/**
 * Created by marcinbak on 18/10/2016.
 */
@ApplicationScope
public class MoviesManager {

  @Inject MoviesApi             mApi;
  @Inject PreferencesManager    mPrefsManager;
  @Inject FavoriteMoviesManager mFavoriteMoviesManager;

  @Inject
  MoviesManager() {
  }

  public Single<MoviesResponse> getMovies(Integer page) {
    int sortType = mPrefsManager.getSelectedSortType();

    switch (sortType) {
      case 0:
        return mApi.getPopular(BuildConfig.MOVIES_API_KEY, null, page);
      case 1:
        return mApi.getTopRated(BuildConfig.MOVIES_API_KEY, null, page);
      case 2:
        return mFavoriteMoviesManager.getAllFavorite()
            .map(new Function<Vector<Movie>, MoviesResponse>() {
              @Override
              public MoviesResponse apply(Vector<Movie> movies) throws Exception {
                MoviesResponse response = new MoviesResponse();
                response.setPage(1);
                response.setTotal_pages(1);
                response.setTotal_results(movies.size());
                response.setResults(movies);
                return response;
              }
            });
      default:
        return Single.error(new IllegalArgumentException("Illegal value of sort type: " + sortType));
    }
  }

  public Single<List<MovieTrailer>> getVideos(Movie movie) {
    return mApi.getVideos(movie.getId().toString(), BuildConfig.MOVIES_API_KEY)
        .map(new Function<VideosResponse, List<MovieTrailer>>() {
          @Override
          public List<MovieTrailer> apply(VideosResponse videosResponse) throws Exception {
            return videosResponse.getResults();
          }
        });
  }

  public Single<List<MovieReview>> getReviews(Movie movie) {
    return mApi.getReviews(movie.getId().toString(), BuildConfig.MOVIES_API_KEY)
        .map(new Function<ReviewsResponse, List<MovieReview>>() {
          @Override
          public List<MovieReview> apply(ReviewsResponse reviewsResponse) throws Exception {
            return reviewsResponse.getResults();
          }
        });
  }

}