package com.danidemi.europrice.tasks.scrapers;

import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.MyAction;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.screenscraping.ScrapeAction;
import com.danidemi.europrice.screenscraping.ScrapeContext;

/**
 * Base class that allows just to implement {@link AbstractShopScraper#buildScrapeAction(ScrapeContext, Request, Callback)} to define a {@link ProductItemScraper}.
 */
public abstract class AbstractShopScraper implements ProductItemScraper {

	protected final MyAction action;
	protected ScrapeAction root;
	
	public AbstractShopScraper() {
		action = new MyAction();			
	}

	protected abstract ScrapeAction buildScrapeAction(ScrapeContext ctx, Request request, Callback callback) throws Exception;

	@Override
	public final void scrape(ScrapeContext ctx, Request request, Callback callback) {
		
		action.setCallback(callback);
		try {
			root = buildScrapeAction(ctx, request, callback);
		} catch (Exception e) {
			throw new RuntimeException("An error occurred while createing shop scrape action", e);
		}
		
		root.startScraping();
		root.scrape(ctx);
		root.endScraping();
	}
	
}
