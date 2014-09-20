package com.danidemi.europrice.tasks;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProductCallback;
import com.danidemi.europrice.pricegrabber.screenscraping.shops.ProductItemScraper;
import com.danidemi.europrice.pricegrabber.screenscraping.shops.Request;
import com.danidemi.europrice.utils.Tupla2;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContextFactory;

/**
 * Receipt for a quite usual scraping workflow, defined as a {@link Runnable} in order to be executed by some executor.
 * @author danidemi
 */
public class ModularScreenScrapingTask implements Runnable {
	
	private Logger log = LoggerFactory.getLogger(ModularScreenScrapingTask.class);

	// dependencies
	private List<Iterable< Tupla2<Request,ProductItemScraper> >> iterators;
	private ScrapeContextFactory ctxFactory;
	private ScrapedProductCallback callback;
	
	public void setCallback(ScrapedProductCallback callback) {
		this.callback = callback;
	}
	
	public void setIterators(
			List<Iterable<Tupla2<Request, ProductItemScraper>>> iterators) {
		this.iterators = iterators;
	}
		
	@Override
	public void run() {
			
		ScrapeContext scrapeContext = ctxFactory.getScrapeContext();		
		for (Iterable<Tupla2<Request, ProductItemScraper>> iterator : iterators) {
			
			for (Tupla2<Request, ProductItemScraper> tupla2 : iterator) {
				
				
				
				Request one = Objects.requireNonNull(tupla2.getOne(), "Request cannot be null");
				ProductItemScraper two = Objects.requireNonNull(tupla2.getTwo(), "scraper cannot be null");
				log.info("Scraping for '{}' on '{}'.", one, two);
				two.scrape(scrapeContext, one, callback);
				
			}
			
			
		}
				
	}
				
	public void setCtxFactory(ScrapeContextFactory ctxFactory) {
		this.ctxFactory = ctxFactory;
	}
		
}
