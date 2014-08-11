package com.danidemi.europrice.tasks.scrapers;

import static com.danidemi.europrice.screenscraping.Scrape.forEachItem;
import static com.danidemi.europrice.screenscraping.Scrape.goTo;
import static com.danidemi.europrice.screenscraping.Scrape.inSequence;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.parser.ParserException;

import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.MyAction;
import com.danidemi.europrice.poc.pricegrabber.PriceParser;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.screenscraping.ScrapeAction;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.Scraper;

public class UtuizamobiScrapeAction extends AbstractShopScraper {
	
	private static final Logger log = LoggerFactory.getLogger(UtuizamobiScrapeAction.class);

	@Override
	protected ScrapeAction buildScrapeAction(ScrapeContext ctx,
			Request request, Callback callback) throws Exception {

		action.setShopName("etuizamobi.si");
		action.setDescriptionSelector(By.cssSelector(".product-name a"));
		action.setDescriptionScraper(new Scraper<String>() {

			@Override
			public String get(WebElement we) {
				return we.getText();
			}
		});
		action.setDetailUrlSelector(By.cssSelector(".product-name a"));
		action.setDetailUrlScraper(new Scraper<String>() {

			@Override
			public String get(WebElement we) {
	            URL href = null;
	            try {
	                href = new URL(we.getAttribute("href"));
	            } catch (MalformedURLException ex) {
	                log.error("An error occurred", ex);
	            }
	            return href.toString();
			}
		});
		action.setPriceSelector(By.cssSelector(".price"));
		action.setPriceScraper( new Scraper<String>() {

			@Override
			public String get(WebElement we) {
	            return we.getText();
			}
		} );
		
		String searchTerm = request.getSearchTerm();
		return 
				inSequence( goTo("http://www.etuizamobi.si/catalogsearch/result/?q=" + searchTerm) )
				.then(  
						forEachItem(
								By.cssSelector(".item"), 
								action
						)
				);
	}

}
