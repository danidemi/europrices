/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.pricegrabber.screenscraping.shops;

import static com.danidemi.jlubricant.screenscraping.action.Actions.forEachItem;
import static com.danidemi.jlubricant.screenscraping.action.Actions.forEachPageWithNextLinkDo;
import static com.danidemi.jlubricant.screenscraping.action.Actions.inSequence;
import static com.danidemi.jlubricant.screenscraping.action.Actions.search;

import java.net.MalformedURLException;

import org.openqa.selenium.By;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProductCallback;
import com.danidemi.europrice.utils.Utils.Language;
import com.danidemi.jlubricant.screenscraping.action.GoToUrl;
import com.danidemi.jlubricant.screenscraping.action.ScrapeAction;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;
import com.danidemi.jlubricant.screenscraping.selector.InsideElementSelectors;

/**
 * The {@link ProductItemScraper} for "http://www.oselection.es/"
 * @author daniele
 */
public class OSelectionScrapeAction extends AbstractShopScraper {
    
	public OSelectionScrapeAction() throws MalformedURLException {
		super("Oselection.es", Language.es);
    }
	
	@Override
	public String toString() {
		return getShopName();
	}

	@Override
	protected ScrapeAction buildScrapeAction(ScrapeContext ctx,
			Request request, ScrapedProductCallback callback) throws Exception {
		
		action.setDescriptionSelector(By.cssSelector("H1"));
		action.setDescriptionScraper(InsideElementSelectors.text());
		
		action.setDetailUrlSelector(By.cssSelector("H1 A"));
		action.setDetailUrlScraper(InsideElementSelectors.attribute("href"));
		
		action.setPriceSelector(By.cssSelector(".discount-price"));
		action.setPriceScraper(InsideElementSelectors.text());
		
		action.setShopName(getShopName());
		
		return
				inSequence( new GoToUrl("http://www.oselection.es/") )
				.then( search( request.getSearchTerm() , By.cssSelector("input.buscador-text"), By.cssSelector("input.buscador-submit")))
				.then( forEachPageWithNextLinkDo(
						By.cssSelector("li.pager-next a"), 
						forEachItem(
								By.cssSelector("div.article-inner"), action))
						)
								;
	}
	    
}
