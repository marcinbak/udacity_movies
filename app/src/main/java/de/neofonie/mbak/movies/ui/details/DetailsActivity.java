package de.neofonie.mbak.movies.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import de.neofonie.mbak.movies.R;
import de.neofonie.mbak.movies.di.base.BaseActivity;
import de.neofonie.mbak.movies.modules.movies.Movie;
import org.parceler.Parcels;

public class DetailsActivity extends BaseActivity {

  public final static String MOVIE_EXTRA = "MOVIE_EXTRA";

  public static void start(Context context, Movie movie) {
    Intent intent = new Intent(context, DetailsActivity.class);
    intent.putExtra(MOVIE_EXTRA, Parcels.wrap(movie));
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mComponent.inject(this);
    setContentView(R.layout.activity_details);
  }
}
