package com.danidemi.europrice.screenscraping;

import com.danidemi.europrice.poc.pricegrabber.Callback;

public class SimpleScreenScraper implements ScreenScraper {

	private ScrapeAction action;
	
	public SimpleScreenScraper(ScrapeAction action) {
		super();
		this.action = action;
	}

	public SimpleScreenScraper() {
		super();
	}

	public void setAction(ScrapeAction action) {
		this.action = action;
	}

	@Override
	public void scrape(Callback callback) {
        action.onStartScraping();
        action.scrape(new ScrapeContext());
        action.onEndScraping();
	}

}
