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
import de.neofonie.mbak.movies.R;
import de.neofonie.mbak.movies.modules.movies.MovieReview;

/**
 * Created by marcinbak on 17/01/2017.
 */
public class ReviewHolder extends TypedViewHolder<MovieReview> {

  @BindView(R.id.review_text) TextView mReviewText;
  @BindView(R.id.author_text) TextView mAuthorText;

  public ReviewHolder(ViewGroup parent) {
    super(R.layout.movie_review_li, parent);
    ButterKnife.bind(this, itemView);
  }

  @Override
  public void bind(MovieReview trailer) {
    mReviewText.setText(trailer.getContent());
    mAuthorText.setText(context.getString(R.string.author_label, trailer.getAuthor()));
  }

  @Override
  public void recycleView() {
  }

  public static TypedViewHolderFactory<MovieReview> factory() {
    return new TypedViewHolderFactory<MovieReview>(MovieReview.class) {
      @Override
      public TypedViewHolder<MovieReview> build(ViewGroup parent) {
        return new ReviewHolder(parent);
      }
    };
  }
}