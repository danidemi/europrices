package com.danidemi.europrice.pricegrabber.screenscraping.shops;

import static com.danidemi.jlubricant.screenscraping.action.Actions.forEachItem;
import static com.danidemi.jlubricant.screenscraping.action.Actions.forEachPageWithNextLinkDo;
import static com.danidemi.jlubricant.screenscraping.action.Actions.goTo;
import static com.danidemi.jlubricant.screenscraping.action.Actions.inSequence;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProductCallback;
import com.danidemi.europrice.utils.Utils.Language;
import com.danidemi.jlubricant.screenscraping.action.ScrapeAction;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;
import com.danidemi.jlubricant.screenscraping.selector.InsideElementSelector;

/**
 * It seems this shops only has covers!
 */
public class UtuizamobiScrapeAction extends AbstractShopScraper {
	
	private static final Logger log = LoggerFactory.getLogger(UtuizamobiScrapeAction.class);
	
	public UtuizamobiScrapeAction() {
		super("Etui Za Mobi.si", Language.sl);
	}

	@Override
	public String toString() {
		return getShopName();
	}
	
	@Override
	protected ScrapeAction buildScrapeAction(ScrapeContext ctx,
			Request request, ScrapedProductCallback callback) throws Exception {

		action.setShopName(getShopName());
		action.setDescriptionSelector(By.cssSelector(".product-name a"));
		action.setDescriptionScraper(new InsideElementSelector<String>() {

			@Override
			public String get(WebElement we) {
				return we.getText();
			}
		});
		action.setDetailUrlSelector(By.cssSelector(".product-name a"));
		action.setDetailUrlScraper(new InsideElementSelector<String>() {

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
		action.setPriceScraper( new InsideElementSelector<String>() {

			@Override
			public String get(WebElement we) {
	            return we.getText();
			}
		} );
		
		String searchTerm = request.getSearchTerm();
		return 
				inSequence( goTo("http://www.etuizamobi.si/catalogsearch/result/?q=" + searchTerm) )
				.then(
					forEachPageWithNextLinkDo(
							By.cssSelector(".next"), 
							forEachItem(
									By.cssSelector(".item"), 
									action
							))

				);
	}

}
