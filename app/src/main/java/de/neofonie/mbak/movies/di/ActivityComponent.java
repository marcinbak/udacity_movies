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

import dagger.Subcomponent;
import de.neofonie.mbak.movies.di.scopes.ActivityScope;
import de.neofonie.mbak.movies.ui.details.DetailsActivity;
import de.neofonie.mbak.movies.ui.details.DetailsFragment;
import de.neofonie.mbak.movies.ui.movies.MainActivity;
import de.neofonie.mbak.movies.ui.movies.MoviesGridFragment;

/**
 * Created by marcinbak on 18/10/2016.
 */
@Subcomponent(modules = {
    ActivityModule.class
})
@ActivityScope
public interface ActivityComponent {
  void inject(MainActivity mainActivity);

  void inject(DetailsActivity detailsActivity);

  void inject(MoviesGridFragment moviesGridFragment);

  void inject(DetailsFragment detailsFragment);

//  void inject(BaseActivity baseActivity);
}
