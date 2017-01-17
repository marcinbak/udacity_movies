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
package de.neofonie.mbak.movies.ui.widgets;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by marcinbak on 17/01/2017.
 */
public abstract class TypedViewHolder<T> extends RecyclerView.ViewHolder {

  protected Context context;

  public TypedViewHolder(@LayoutRes int layoutRes, ViewGroup parent) {
    super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
    context = parent.getContext();
  }

  public TypedViewHolder(View view) {
    super(view);
    context = view.getContext();
  }

  public abstract void bind(T dataItem);

  public abstract void recycleView();
}
