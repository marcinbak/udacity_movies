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

import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.neofonie.mbak.movies.R;

/**
 * Created by marcinbak on 17/01/2017.
 */
public class HeaderHolder extends TypedViewHolder<String> {

  @BindView(R.id.header_text) TextView mHeaderText;

  public HeaderHolder(ViewGroup parent) {
    super(R.layout.header_li, parent);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void bind(String header) {
    mHeaderText.setText(header);
  }

  @Override
  public void recycleView() {
  }

  public static TypedViewHolderFactory<String> factory() {
    return new TypedViewHolderFactory<String>(String.class) {
      @Override
      public TypedViewHolder<String> build(ViewGroup parent) {
        return new HeaderHolder(parent);
      }
    };
  }
}