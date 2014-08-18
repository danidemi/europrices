package com.danidemi.europrice.tasks.scrapers;

import static com.danidemi.europrice.screenscraping.Scrape.forEachItem;
import static com.danidemi.europrice.screenscraping.Scrape.forEachPageWithNextLinkDo;
import static com.danidemi.europrice.screenscraping.Scrape.inSequence;
import static com.danidemi.europrice.screenscraping.Scrape.search;

import org.openqa.selenium.By;

import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.screenscraping.GoToUrl;
import com.danidemi.europrice.screenscraping.ScrapeAction;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.Scrapers;
import com.danidemi.europrice.utils.Utils.Language;

public class BigShopLv extends AbstractShopScraper {

	public BigShopLv() {
		super("BIGSHOP-LV", Language.lv);
	}

	@Override
	protected ScrapeAction buildScrapeAction(ScrapeContext ctx,Request request, Callback callback) throws Exception {

		action.setDescriptionSelector(By.cssSelector("H3"));
		action.setDescriptionScraper(Scrapers.text());
		
		action.setDetailUrlSelector(By.cssSelector("A"));
		action.setDetailUrlScraper(Scrapers.attribute("href"));
		
		action.setPriceSelector(By.cssSelector(".price"));
		action.setPriceScraper(Scrapers.text());
		
		action.setShopName(getShopName());
		
		return
				inSequence( new GoToUrl("http://www.bigshop.lv/") )
				.then( 
						search( request.getSearchTerm() , 
						By.cssSelector("#search > input"), 
						By.cssSelector("#search > a")))
				.then( forEachPageWithNextLinkDo(
						By.cssSelector(".next"), 
						forEachItem(
								By.cssSelector("div.item"), action))
						)
								;
		
	}

}
