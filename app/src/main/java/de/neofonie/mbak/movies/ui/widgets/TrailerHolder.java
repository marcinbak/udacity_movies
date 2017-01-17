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

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.neofonie.mbak.movies.R;
import de.neofonie.mbak.movies.modules.movies.MovieTrailer;

/**
 * Created by marcinbak on 17/01/2017.
 */
public class TrailerHolder extends TypedViewHolder<MovieTrailer> implements View.OnClickListener {

  private final TrailerClickedListener listener;
  private       MovieTrailer           trailer;

  @BindView(R.id.trailer_text) TextView mTrailerText;

  public TrailerHolder(ViewGroup parent, TrailerClickedListener listener) {
    super(R.layout.movie_trailer_li, parent);
    ButterKnife.bind(this, itemView);
    this.listener = listener;
    mTrailerText.setOnClickListener(this);
  }

  @Override
  public void bind(MovieTrailer trailer) {
    this.trailer = trailer;
    String text = context.getString(R.string.trailer_label, trailer.getName(), trailer.getSite(), trailer.getSize());
    mTrailerText.setText(text);
  }

  @Override
  public void recycleView() {
  }

  public static TypedViewHolderFactory<MovieTrailer> factory(final TrailerClickedListener listener) {
    return new TypedViewHolderFactory<MovieTrailer>(MovieTrailer.class) {
      @Override
      public TypedViewHolder<MovieTrailer> build(ViewGroup parent) {
        return new TrailerHolder(parent, listener);
      }
    };
  }

  @Override
  public void onClick(View v) {
    if (listener != null) {
      listener.onTrailerClick(trailer);
    }
  }

  public interface TrailerClickedListener {
    void onTrailerClick(MovieTrailer trailer);
  }
}