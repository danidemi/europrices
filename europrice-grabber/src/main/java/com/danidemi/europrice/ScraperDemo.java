package com.danidemi.europrice;

import com.danidemi.europrice.pricegrabber.screenscraping.action.SysoutProductCallback;
import com.danidemi.europrice.pricegrabber.screenscraping.shops.OSelectionScrapeAction;
import com.danidemi.europrice.pricegrabber.screenscraping.shops.Request;
import com.danidemi.europrice.pricegrabber.screenscraping.shops.StockistiScraper;
import com.danidemi.europrice.pricegrabber.screenscraping.shops.UtuizamobiScrapeAction;
import com.danidemi.jlubricant.screenscraping.action.Search;
import com.danidemi.jlubricant.screenscraping.context.PhantomjsFactory;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContextFactory;

public class ScraperDemo {

	public static void main(String[] args) {
		
		ScrapeContext scrapeContext = null;
		try{
			
			ScrapeContextFactory factory = new PhantomjsFactory();
			
			SysoutProductCallback callback = new SysoutProductCallback();
			
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
