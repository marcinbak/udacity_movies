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
package de.neofonie.mbak.movies.di.modules;

import android.content.Context;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import dagger.Module;
import dagger.Provides;
import de.neofonie.mbak.movies.R;
import de.neofonie.mbak.movies.di.qualifiers.ForApplication;
import de.neofonie.mbak.movies.di.scopes.ApplicationScope;
import de.neofonie.mbak.movies.modules.MoviesApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by marcinbak on 18/10/2016.
 */
@Module
public final class NetworkModule {

  @Provides
  @ApplicationScope
  OkHttpClient provideOkHttpClient() {
    return new OkHttpClient();
  }

  @Provides
  @ApplicationScope
  Retrofit provideAuthAdapter(OkHttpClient client,
                              @ForApplication Context context) {
    return new Retrofit.Builder()
        .baseUrl(context.getString(R.string.backend_url))
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }

  @Provides
  @ApplicationScope
  MoviesApi provideMoviesApi(Retrofit retrofit) {
    return retrofit.create(MoviesApi.class);
  }

}
