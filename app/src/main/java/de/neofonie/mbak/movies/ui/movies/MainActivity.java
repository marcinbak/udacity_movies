package de.neofonie.mbak.movies.ui.movies;

import android.os.Bundle;
import de.neofonie.mbak.movies.R;
import de.neofonie.mbak.movies.di.base.BaseActivity;

public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mComponent.inject(this);
    setContentView(R.layout.activity_main);
  }
}
