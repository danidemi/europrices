package com.danidemi.europrice.pricegrabber.screenscraping.shops;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProductCallback;
import com.danidemi.jlubricant.screenscraping.action.ScrapeAction;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;

/** 
 * Implementations has the responsibility to scrape the content of a shop and give back info on the listed items through the provided {@link ScrapedProductCallback}.
 */
public interface ProductItemScraper {

	void scrape(ScrapeContext ctx, Request request, ScrapedProductCallback callback);
	
}
