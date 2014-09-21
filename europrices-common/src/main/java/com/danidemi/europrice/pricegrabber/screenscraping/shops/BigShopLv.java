package com.danidemi.europrice.pricegrabber.screenscraping.shops;

import static com.danidemi.jlubricant.screenscraping.action.Actions.forEachItem;
import static com.danidemi.jlubricant.screenscraping.action.Actions.forEachPageWithNextLinkDo;
import static com.danidemi.jlubricant.screenscraping.action.Actions.inSequence;
import static com.danidemi.jlubricant.screenscraping.action.Actions.search;

import org.openqa.selenium.By;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProductCallback;
import com.danidemi.europrice.utils.Utils.Language;
import com.danidemi.jlubricant.screenscraping.action.GoToUrl;
import com.danidemi.jlubricant.screenscraping.action.ScrapeAction;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;
import com.danidemi.jlubricant.screenscraping.selector.InsideElementSelectors;

public class BigShopLv extends AbstractShopScraper {

	public BigShopLv() {
		super("BIGSHOP-LV", Language.lv);
	}

	@Override
	protected ScrapeAction buildScrapeAction(ScrapeContext ctx,Request request, ScrapedProductCallback callback) throws Exception {

		action.setDescriptionSelector(By.cssSelector("H3"));
		action.setDescriptionScraper(InsideElementSelectors.text());
		
		action.setDetailUrlSelector(By.cssSelector("A"));
		action.setDetailUrlScraper(InsideElementSelectors.attribute("href"));
		
		action.setPriceSelector(By.cssSelector(".price"));
		action.setPriceScraper(InsideElementSelectors.text());
		
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
