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
package de.neofonie.mbak.movies;

import dagger.Component;
import de.neofonie.mbak.movies.di.AppComponent;
import de.neofonie.mbak.movies.di.base.BaseApplication;
import de.neofonie.mbak.movies.di.modules.AppModule;
import de.neofonie.mbak.movies.di.modules.NetworkModule;
import de.neofonie.mbak.movies.di.scopes.ApplicationScope;

/**
 * Created by marcinbak on 18/10/2016.
 */
public class MoviesApp extends BaseApplication {

  @Override
  protected AppComponent onCreateAppComponent() {
    return DaggerMoviesApp_MoviesAppComponent.builder()
        .appModule(new AppModule(this))
        .build();
  }

  @ApplicationScope
  @Component(modules = {
      AppModule.class,
      NetworkModule.class
  })
  interface MoviesAppComponent extends AppComponent {}
}
