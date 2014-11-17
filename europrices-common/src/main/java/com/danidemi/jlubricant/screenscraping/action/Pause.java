package com.danidemi.jlubricant.screenscraping.action;

import java.util.concurrent.TimeUnit;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProduct;
import com.danidemi.jlubricant.screenscraping.ScreenScrapingException;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;
import com.danidemi.jlubricant.utils.wait.For;

/**
 * As described in <a href="http://lethain.com/an-introduction-to-compassionate-screenscraping/">An Introduction To Compassionate Screenscraping</a>
 * it's polite that screen scraper traffic be indistinguishable from human traffic. 
 * @author danidemi
 *
 */
public class Pause extends ScrapeActionAdapter {

	private int min;
	private int max;
	
	/**
	 * Wait for a random amount of time betweeb min and max seconds.
	 */
	public Pause(int min, int max) {
		super();
		this.min = Math.min(min, max);
		this.max = Math.max(min, max);
	}

	@Override
	public void startScraping() {		
	}

	@Override
	public void scrape(ScrapeContext ctx) throws ScreenScrapingException {
		new For( (int)(((max-min) * Math.random()) + min) , TimeUnit.SECONDS).pause();
	}

	@Override
	public void endScraping() {		
	}

}
