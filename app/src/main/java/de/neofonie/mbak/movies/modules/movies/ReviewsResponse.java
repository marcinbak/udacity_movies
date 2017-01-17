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

import java.util.List;

/**
 * Created by marcinbak on 17/01/2017.
 */
@Data
public class ReviewsResponse {
  Long              id;
  Integer           page;
  List<MovieReview> results;
  Integer           total_pages;
  Integer           total_results;
}