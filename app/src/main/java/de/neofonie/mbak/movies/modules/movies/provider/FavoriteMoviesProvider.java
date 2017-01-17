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

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by marcinbak on 17/01/2017.
 */
public class FavoriteMoviesProvider extends ContentProvider {

  // The URI Matcher used by this content provider.
  private static final UriMatcher sUriMatcher = buildUriMatcher();
  private FavoriteMoviesDbHelper mOpenHelper;

  static final int FAVORITE_MOVIE = 100;

  private static final SQLiteQueryBuilder sWeatherByLocationSettingQueryBuilder;

  static {
    sWeatherByLocationSettingQueryBuilder = new SQLiteQueryBuilder();
    sWeatherByLocationSettingQueryBuilder.setTables(MovieContract.MovieEntry.TABLE_NAME);
  }

//  //favorite.movie_id = ?
//  private static final String sMovieIdSelection =
//      MovieContract.MovieEntry.TABLE_NAME +
//          "." + MovieContract.MovieEntry.COLUMN_ID + " = ? ";
//
//
//  private Cursor getMoviewById(Uri uri, String[] projection, String sortOrder) {
//    String locationSetting = MovieContract.MovieEntry.getLocationSettingFromUri(uri);
//    long startDate = WeatherContract.WeatherEntry.getStartDateFromUri(uri);
//
//    String[] selectionArgs;
//    String selection;
//
//    if (startDate == 0) {
//      selection = sLocationSettingSelection;
//      selectionArgs = new String[]{locationSetting};
//    } else {
//      selectionArgs = new String[]{locationSetting, Long.toString(startDate)};
//      selection = sLocationSettingWithStartDateSelection;
//    }
//
//    return sWeatherByLocationSettingQueryBuilder.query(mOpenHelper.getReadableDatabase(),
//        projection,
//        selection,
//        selectionArgs,
//        null,
//        null,
//        sortOrder
//    );
//  }

  static UriMatcher buildUriMatcher() {
    final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    final String authority = MovieContract.CONTENT_AUTHORITY;

    matcher.addURI(authority, MovieContract.PATH_FAVORITE, FAVORITE_MOVIE);
    return matcher;
  }


  @Override
  public boolean onCreate() {
    mOpenHelper = new FavoriteMoviesDbHelper(getContext());
    return true;
  }

  @Nullable
  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    // Here's the switch statement that, given a URI, will determine what kind of request it is,
    // and query the database accordingly.
    Cursor retCursor;
    switch (sUriMatcher.match(uri)) {
      // "weather/*/*"
      case FAVORITE_MOVIE: {
        retCursor = mOpenHelper.getReadableDatabase().query(
            MovieContract.MovieEntry.TABLE_NAME,
            projection, selection, selectionArgs, null, null, sortOrder);
        break;
      }
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    retCursor.setNotificationUri(getContext().getContentResolver(), uri);
    return retCursor;
  }

  @Nullable
  @Override
  public String getType(Uri uri) {
    // Use the Uri Matcher to determine what kind of URI this is.
    final int match = sUriMatcher.match(uri);
    switch (match) {
      case FAVORITE_MOVIE:
        return MovieContract.MovieEntry.CONTENT_TYPE;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
  }

  @Nullable
  @Override
  public Uri insert(Uri uri, ContentValues values) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);
    Uri returnUri;

    switch (match) {
      case FAVORITE_MOVIE: {
        long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
        if (_id > 0)
          returnUri = MovieContract.MovieEntry.buildMovieUri(_id);
        else
          throw new android.database.SQLException("Failed to insert row into " + uri);
        break;
      }
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return returnUri;

  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);

    int rowsDeleted;
    switch (match) {
      case FAVORITE_MOVIE: {
        rowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
        break;
      }
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    if (rowsDeleted > 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    db.close();
    return rowsDeleted;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);

    int rowsUpdated;
    switch (match) {
      case FAVORITE_MOVIE: {
        rowsUpdated = db.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
        break;
      }
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    if (rowsUpdated > 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    db.close();
    return rowsUpdated;
  }

  @Override
  public int bulkInsert(Uri uri, ContentValues[] values) {
    final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
    final int match = sUriMatcher.match(uri);
    switch (match) {
      case FAVORITE_MOVIE:
        db.beginTransaction();
        int returnCount = 0;
        try {
          for (ContentValues value : values) {
            long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, value);
            if (_id != -1) {
              returnCount++;
            }
          }
          db.setTransactionSuccessful();
        } finally {
          db.endTransaction();
        }
        db.close();
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
      default:
        return super.bulkInsert(uri, values);
    }
  }

  @Override
  @TargetApi(11)
  public void shutdown() {
    mOpenHelper.close();
    super.shutdown();
  }

}