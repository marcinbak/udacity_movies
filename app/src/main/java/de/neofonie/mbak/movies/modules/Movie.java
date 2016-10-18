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
package de.neofonie.mbak.movies.modules;

import lombok.Data;

/**
 * Created by marcinbak on 18/10/2016.
 */
@Data
public class Movie {

  //"/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg"
  String poster_path;
  String overview;
  String release_date;
  String title;
  String original_title;
  long   id;
}

//    "adult": false,
//    "genre_ids": [
//    80
//    ],
//    "original_language": "en",
//    "backdrop_path": "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
//    "popularity": 48.261451,
//    "vote_count": 1466,
//    "video": false,
//    "vote_average": 5.91