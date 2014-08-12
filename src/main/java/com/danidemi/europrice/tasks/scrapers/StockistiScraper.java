package com.danidemi.europrice.tasks.scrapers;

import org.openqa.selenium.By;

import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.screenscraping.GoToUrl;
import com.danidemi.europrice.screenscraping.ScrapeAction;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.Scrapers;

import static com.danidemi.europrice.screenscraping.Scrape.forEachItem;
import static com.danidemi.europrice.screenscraping.Scrape.forEachPageWithNextLinkDo;
import static com.danidemi.europrice.screenscraping.Scrape.inSequence;
import static com.danidemi.europrice.screenscraping.Scrape.search;

public class StockistiScraper extends AbstractShopScraper {
	
	@Override
	public String toString() {
		return "Gli Stockisti";
	}	

	@Override
	protected ScrapeAction buildScrapeAction(ScrapeContext ctx, Request request, Callback callback) throws Exception {
		
		action.setDescriptionSelector(By.cssSelector("H2.product-name"));
		action.setDescriptionScraper(Scrapers.text());
		
		action.setDetailUrlSelector(By.cssSelector("A"));
		action.setDetailUrlScraper(Scrapers.attribute("href"));
		
		action.setPriceSelector(By.cssSelector("span.price"));
		action.setPriceScraper(Scrapers.text());
		
		action.setShopName("oselection.es");
		
		return
				inSequence( new GoToUrl("http://www.glistockisti.it/") )
				.then( search( request.getSearchTerm() , By.cssSelector("input#search.input-text"), By.cssSelector("button.button")))
				.then( forEachPageWithNextLinkDo(
						By.cssSelector("a.next"), 
						forEachItem(
								By.cssSelector("li.item"), action))
						)
								;
	}

}
