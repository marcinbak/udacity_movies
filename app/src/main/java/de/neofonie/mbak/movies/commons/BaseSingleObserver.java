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
package de.neofonie.mbak.movies.commons;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by marcinbak on 18/10/2016.
 */
public class BaseSingleObserver<T> implements SingleObserver<T> {
  @Override
  public void onSubscribe(Disposable d) {

  }

  @Override
  public void onSuccess(T value) {

  }

  @Override
  public void onError(Throwable e) {

  }
}
