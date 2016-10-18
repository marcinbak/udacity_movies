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
package de.neofonie.mbak.movies.di.base;

import android.app.Application;
import android.content.Context;
import com.jakewharton.threetenabp.AndroidThreeTen;
import de.neofonie.mbak.movies.di.ActivityComponent;
import de.neofonie.mbak.movies.di.ActivityModule;
import de.neofonie.mbak.movies.di.AppComponent;

/**
 * Created by marcinbak on 18/10/2016.
 */
public abstract class BaseApplication extends Application {
  private AppComponent mAppComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    initDagger2();
    AndroidThreeTen.init(this);
  }

  private void initDagger2() {
    mAppComponent = onCreateAppComponent();
    mAppComponent.inject(this);
  }

  public ActivityComponent activityComponent(BaseActivity activity) {
    return mAppComponent.plusActivity(new ActivityModule(activity));
  }

  public AppComponent appComponent() {
    return mAppComponent;
  }

  protected abstract AppComponent onCreateAppComponent();

  public static BaseApplication get(Context context) {
    return (BaseApplication) context.getApplicationContext();
  }
}
