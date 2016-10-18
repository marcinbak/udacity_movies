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

import android.content.Context;
import android.support.v4.app.Fragment;
import butterknife.Unbinder;
import de.neofonie.mbak.movies.di.ActivityComponent;

/**
 * Created by marcinbak on 18/10/2016.
 */
public abstract class BaseFragment extends Fragment {

  protected Unbinder mUnbinder;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    inject(getComponent(context));
  }

  private ActivityComponent getComponent(Context context) {
    if (context instanceof BaseActivity) {
      return ((BaseActivity) context).mComponent;
    } else {
      throw new IllegalArgumentException("This view should be attached to Activity extending BaseActivity. Please override getComponent() to provide different source of ActivityComponent to which this View should inject.");
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (mUnbinder != null) mUnbinder.unbind();
  }

  protected abstract void inject(ActivityComponent component);

}
