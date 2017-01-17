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
package de.neofonie.mbak.movies.modules.movies;

import lombok.Data;

/**
 * Created by marcinbak on 17/01/2017.
 */
@Data
public class MovieTrailer {
  String  id;
  String  iso_639_1;
  String  iso_3166_1;
  String  key;
  String  name;
  String  site;
  Integer size;
  String  type;

}
//    "id": "581f8244c3a3685550002fa3",
//    "iso_639_1": "en",
//    "iso_3166_1": "US",
//    "key": "EZ-zFwuR0FY",
//    "name": "HD Trailer",
//    "site": "YouTube",
//    "size": 1080,
//    "type": "Trailer"