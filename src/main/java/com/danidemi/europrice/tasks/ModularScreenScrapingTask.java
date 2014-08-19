package com.danidemi.europrice.tasks;

import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.danidemi.europrice.db.ProductItem;
import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.Shop;
import com.danidemi.europrice.db.ShopRepository;
import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.poc.pricegrabber.ScrapedShopItem;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.ScrapeContextFactory;
import com.danidemi.europrice.tasks.scrapers.ProductItemScraper;
import com.danidemi.europrice.utils.Utils;

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
				
				Request one = tupla2.getOne();
				ProductItemScraper two = tupla2.getTwo();
				log.info("Scraping for '{}' on '{}'.", one, two);
				two.scrape(scrapeContext, one, callback);
				
			}
			
			
		}
				
	}
				
	public void setCtxFactory(ScrapeContextFactory ctxFactory) {
		this.ctxFactory = ctxFactory;
	}
		
}
