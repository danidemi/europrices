package com.danidemi.europrice;

import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.poc.pricegrabber.SysoutCallback;
import com.danidemi.europrice.screenscraping.PhantomjsFactory;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.ScrapeContextFactory;
import com.danidemi.europrice.screenscraping.Search;
import com.danidemi.europrice.tasks.scrapers.OSelectionScrapeAction;
import com.danidemi.europrice.tasks.scrapers.StockistiScraper;
import com.danidemi.europrice.tasks.scrapers.UtuizamobiScrapeAction;

public class ScraperDemo {

	public static void main(String[] args) {
		
		ScrapeContext scrapeContext = null;
		try{
			
			ScrapeContextFactory factory = new PhantomjsFactory();
			
			SysoutCallback callback = new SysoutCallback();
			
			OSelectionScrapeAction scraper1 = new OSelectionScrapeAction();
			UtuizamobiScrapeAction scraper2 = new UtuizamobiScrapeAction();
			StockistiScraper scraper3 = new StockistiScraper();
			
			Request search = new Request("nexus");
			scrapeContext = factory.getScrapeContext();
			
			
			//scraper1.scrape(scrapeContext, search, callback);			
			//scraper2.scrape(scrapeContext, search, callback);
			scraper3.scrape(scrapeContext, search, callback);
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			scrapeContext.close();
		}
		
	}
	
}
