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
package de.neofonie.mbak.movies.modules.movies.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import de.neofonie.mbak.movies.di.qualifiers.ForApplication;
import de.neofonie.mbak.movies.di.scopes.ApplicationScope;
import de.neofonie.mbak.movies.modules.movies.Movie;
import io.reactivex.Single;

import javax.inject.Inject;
import java.util.Vector;
import java.util.concurrent.Callable;

/**
 * Created by marcinbak on 17/01/2017.
 */
@ApplicationScope
public class FavoriteMoviesManager {

  private final Context mContext;

  @Inject
  public FavoriteMoviesManager(@ForApplication Context context) {
    this.mContext = context;
  }

  public Single<Boolean> toggleFavorite(final Movie movie) {
    return Single.fromCallable(new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        boolean exists = existsMovie(movie);

        if (exists) {
          mContext.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI,
              MovieContract.MovieEntry.COLUMN_ID + " = ?",
              new String[]{movie.getId().toString()});
        } else {
          mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, toContentValues(movie));
        }

        return !exists;
      }
    });
  }

  public Single<Vector<Movie>> getAllFavorite() {
    return Single.fromCallable(new Callable<Vector<Movie>>() {
      @Override
      public Vector<Movie> call() throws Exception {
        Cursor cursor = mContext.getContentResolver().query(
            MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
        Vector<Movie> vector = new Vector<>(cursor.getCount());
        if (cursor.moveToFirst()) {
          do {
            vector.add(fromCursor(cursor));
          } while (cursor.moveToNext());
        }
        cursor.close();
        return vector;
      }
    });
  }

  public Single<Boolean> isFavorite(final Movie movie) {
    return Single.fromCallable(new Callable<Boolean>() {

      @Override
      public Boolean call() throws Exception {
        return existsMovie(movie);
      }
    });
  }

  private boolean existsMovie(Movie movie) {
    Cursor cursor = mContext.getContentResolver().query(
        MovieContract.MovieEntry.CONTENT_URI,
        new String[]{MovieContract.MovieEntry._ID},
        MovieContract.MovieEntry.COLUMN_ID + " = ?",
        new String[]{movie.getId().toString()},
        null);

    boolean exists = cursor.moveToFirst();
    cursor.close();

    return exists;
  }

  private static ContentValues toContentValues(Movie movie) {
    ContentValues cv = new ContentValues();
    cv.put(MovieContract.MovieEntry.COLUMN_ID, movie.getId());
    cv.put(MovieContract.MovieEntry.COLUMN_ORIG_TITLE, movie.getOriginal_title());
    cv.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
    cv.put(MovieContract.MovieEntry.COLUMN_POSTER, movie.getPoster_path());
    cv.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getRelease_date());
    cv.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
    cv.put(MovieContract.MovieEntry.COLUMN_VOTE, movie.getVote_average());

    return cv;
  }

  private static Movie fromCursor(Cursor cursor) {
    Movie movie = new Movie();
    movie.setId(cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)));
    movie.setOriginal_title(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ORIG_TITLE)));
    movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));
    movie.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER)));
    movie.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)));
    movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE)));
    movie.setVote_average(cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE)));

    return movie;
  }
}
