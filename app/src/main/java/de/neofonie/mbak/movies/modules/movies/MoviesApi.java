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
package de.neofonie.mbak.movies.modules.movies;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by marcinbak on 18/10/2016.
 */
public interface MoviesApi {

  @GET("/3/movie/popular")
  @Headers("Accept: application/json")
  Single<MoviesResponse> getPopular(@Query("api_key") String apiKey,
                                    @Query("language") String language,
                                    @Query("page") Integer page);

  @GET("/3/movie/top_rated")
  @Headers("Accept: application/json")
  Single<MoviesResponse> getTopRated(@Query("api_key") String apiKey,
                                     @Query("language") String language,
                                     @Query("page") Integer page);

}