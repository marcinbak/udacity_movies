package de.neofonie.mbak.movies.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import de.neofonie.mbak.movies.R;
import de.neofonie.mbak.movies.di.base.BaseActivity;

public class DetailsActivity extends BaseActivity {

  private final static String MOVIE_ID_EXTRA = "MOVIE_ID_EXTRA";

  public static void start(Context context, long movieId) {
    Intent intent = new Intent(context, DetailsActivity.class);
    intent.putExtra(MOVIE_ID_EXTRA, movieId);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mComponent.inject(this);
    setContentView(R.layout.activity_details);
  }
}
