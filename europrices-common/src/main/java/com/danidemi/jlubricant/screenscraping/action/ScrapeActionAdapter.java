package com.danidemi.jlubricant.screenscraping.action;

import com.danidemi.jlubricant.screenscraping.ScreenScrapingException;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;

/**
 * A {@link ScrapeAction} that does absolutely nothing. Subclasses can specify only the methods they re interested in.
 */
public class ScrapeActionAdapter implements ScrapeAction {

	@Override
	public void startScraping() {
		// subclasses should implement this method if they are interested in it.		
	}

	@Override
	public void scrape(ScrapeContext ctx) throws ScreenScrapingException {
		// subclasses should implement this method if they are interested in it.
	}

	@Override
	public void endScraping() {
		// subclasses should implement this method if they are interested in it.
	}

}
