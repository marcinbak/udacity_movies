package de.neofonie.mbak.movies.ui.details;

import android.os.Bundle;
import de.neofonie.mbak.movies.R;
import de.neofonie.mbak.movies.di.base.BaseActivity;

public class DetailsActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mComponent.inject(this);
    setContentView(R.layout.activity_details);
  }
}
