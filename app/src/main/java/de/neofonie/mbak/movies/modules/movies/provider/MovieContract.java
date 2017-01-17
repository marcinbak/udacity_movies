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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import de.neofonie.mbak.movies.BuildConfig;

/**
 * Created by marcinbak on 17/01/2017.
 */
public class MovieContract {

  // The "Content authority" is a name for the entire content provider, similar to the
  // relationship between a domain name and its website.  A convenient string to use for the
  // content authority is the package name for the app, which is guaranteed to be unique on the
  // device.
  public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;

  // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
  // the content provider.
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

  public static final String PATH_FAVORITE = "favorite";

  /* Inner class that defines the table contents of the location table */
  public static final class MovieEntry implements BaseColumns {

    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

    public static final String CONTENT_TYPE      =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;
    public static final String CONTENT_ITEM_TYPE =
        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

    // Table name
    public static final String TABLE_NAME = "favorite";

    public static final String COLUMN_POSTER       = "poster_path";
    public static final String COLUMN_OVERVIEW     = "overview";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_TITLE        = "title";
    public static final String COLUMN_ORIG_TITLE   = "original_title";
    public static final String COLUMN_VOTE         = "vote_average";
    public static final String COLUMN_ID           = "movie_id";

    public static Uri buildMovieUri(long id) {
      return ContentUris.withAppendedId(CONTENT_URI, id);
    }
  }
}
