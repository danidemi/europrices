package com.danidemi.europrice.tasks.scrapers;

import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.screenscraping.ScrapeAction;
import com.danidemi.europrice.screenscraping.ScrapeContext;

/** 
 * Implementations has the responsibility to scrape the content of a shop and give back info on the listed items through the provided {@link Callback}.
 */
public interface ProductItemScraper {

	void scrape(ScrapeContext ctx, Request request, Callback callback);
	
}
