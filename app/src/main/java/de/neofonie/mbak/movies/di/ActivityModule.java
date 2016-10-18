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
package de.neofonie.mbak.movies.di;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import dagger.Module;
import dagger.Provides;
import de.neofonie.mbak.movies.di.qualifiers.ForActivity;

/**
 * Created by marcinbak on 18/10/2016.
 */
@Module
public class ActivityModule {
  private FragmentActivity mFragmentActivity;

  public ActivityModule(FragmentActivity mFragmentActivity) {
    this.mFragmentActivity = mFragmentActivity;
  }

  @Provides
  @ForActivity
  Context provideActivityContext() {
    return mFragmentActivity;
  }

  @Provides
  @ForActivity
  LayoutInflater provideLayoutInflater(Activity activity) {
    return LayoutInflater.from(activity);
  }

  @Provides
  Activity provideActivity() {
    return mFragmentActivity;
  }

  @Provides
  FragmentActivity provideFragmentActivity() {
    return mFragmentActivity;
  }
}
