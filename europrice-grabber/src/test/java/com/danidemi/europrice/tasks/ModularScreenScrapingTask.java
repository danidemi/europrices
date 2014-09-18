package com.danidemi.europrice.tasks;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.ScrapeContextFactory;
import com.danidemi.europrice.tasks.scrapers.ProductItemScraper;

public class ModularScreenScrapingTask implements Runnable {
	
	private Logger log = LoggerFactory.getLogger(ModularScreenScrapingTask.class);

	// dependencies
	private List<Iterable< Tupla2<Request,ProductItemScraper> >> iterators;
	private ScrapeContextFactory ctxFactory;
	private Callback callback;
	
	public void setCallback(Callback callback) {
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
