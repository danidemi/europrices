package com.danidemi.europrice.pricegrabber.screenscraping.shops;

import org.openqa.selenium.By;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProductCallback;
import com.danidemi.europrice.utils.Utils.Language;
import com.danidemi.jlubricant.screenscraping.action.GoToUrl;
import com.danidemi.jlubricant.screenscraping.action.ScrapeAction;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;
import com.danidemi.jlubricant.screenscraping.selector.InsideElementSelectors;

import static com.danidemi.jlubricant.screenscraping.action.Actions.forEachItem;
import static com.danidemi.jlubricant.screenscraping.action.Actions.forEachPageWithNextLinkDo;
import static com.danidemi.jlubricant.screenscraping.action.Actions.inSequence;
import static com.danidemi.jlubricant.screenscraping.action.Actions.search;

public class StockistiScraper extends AbstractShopScraper {
	
	public StockistiScraper() {
		super("Gli Stockisti", Language.it);
	}
	
	@Override
	public String toString() {
		return "Gli Stockisti";
	}	

	@Override
	protected ScrapeAction buildScrapeAction(ScrapeContext ctx, Request request, ScrapedProductCallback callback) throws Exception {
		
		action.setDescriptionSelector(By.cssSelector("H2.product-name"));
		action.setDescriptionScraper(InsideElementSelectors.text());
		
		action.setDetailUrlSelector(By.cssSelector("A"));
		action.setDetailUrlScraper(InsideElementSelectors.attribute("href"));
		
		action.setPriceSelector(By.cssSelector("span.price"));
		action.setPriceScraper(InsideElementSelectors.text());
		
		action.setShopName(getShopName());
		
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
