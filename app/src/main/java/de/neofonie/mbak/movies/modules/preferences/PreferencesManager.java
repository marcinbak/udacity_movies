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
package de.neofonie.mbak.movies.modules.preferences;

import android.content.SharedPreferences;
import de.neofonie.mbak.movies.di.scopes.ApplicationScope;

import javax.inject.Inject;

/**
 * Created by marcinbak on 19/10/2016.
 */
@ApplicationScope
public class PreferencesManager {

  private final static String SORT_TYPE_PREFS = "SORT_TYPE_PREFS";

  @Inject SharedPreferences mPrefs;

  @Inject
  public PreferencesManager() {
  }

  public void setSelectedSortType(int sortType) {
    mPrefs.edit().putInt(SORT_TYPE_PREFS, sortType).apply();
  }

  public int getSelectedSortType() {
    return mPrefs.getInt(SORT_TYPE_PREFS, 0);
  }

}
