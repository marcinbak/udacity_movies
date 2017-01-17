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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import de.neofonie.mbak.movies.modules.movies.provider.MovieContract.MovieEntry;

/**
 * Created by marcinbak on 17/01/2017.
 */
public class FavoriteMoviesDbHelper extends SQLiteOpenHelper {

  private static final int    DATABASE_VERSION = 1;
  static final         String DATABASE_NAME    = "favorites.db";

  public FavoriteMoviesDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
// Create a table to hold locations.  A location consists of the string supplied in the
    // location setting, the city name, and the latitude and longitude
    final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
        MovieEntry._ID + " INTEGER PRIMARY KEY," +
        MovieEntry.COLUMN_ID + " INTEGER NOT NULL, " +
        MovieEntry.COLUMN_VOTE + " REAL NOT NULL, " +
        MovieEntry.COLUMN_ORIG_TITLE + " TEXT, " +
        MovieEntry.COLUMN_TITLE + " TEXT, " +
        MovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +
        MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
        MovieEntry.COLUMN_POSTER + " TEXT, " +
        //        // To assure the application have just one weather entry per day
//        // per location, it's created a UNIQUE constraint with REPLACE strategy
        " UNIQUE (" + MovieEntry.COLUMN_ID + ") ON CONFLICT REPLACE);";

    sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }
}
