/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.tasks.scrapers;

import static com.danidemi.europrice.screenscraping.Scrape.forEachItem;
import static com.danidemi.europrice.screenscraping.Scrape.forEachPageWithNextLinkDo;
import static com.danidemi.europrice.screenscraping.Scrape.inSequence;
import static com.danidemi.europrice.screenscraping.Scrape.search;

import java.net.MalformedURLException;

import org.openqa.selenium.By;

import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.screenscraping.GoToUrl;
import com.danidemi.europrice.screenscraping.ScrapeAction;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.Scrapers;

/**
 * The {@link ProductItemScraper} for "http://www.oselection.es/"
 * @author daniele
 */
public class OSelectionScrapeAction extends AbstractShopScraper {
    
	public OSelectionScrapeAction() throws MalformedURLException {
		super("Oselection.es");
    }
	
	@Override
	public String toString() {
		return getShopName();
	}

	@Override
	protected ScrapeAction buildScrapeAction(ScrapeContext ctx,
			Request request, Callback callback) throws Exception {
		
		action.setDescriptionSelector(By.cssSelector("H1"));
		action.setDescriptionScraper(Scrapers.text());
		
		action.setDetailUrlSelector(By.cssSelector("H1 A"));
		action.setDetailUrlScraper(Scrapers.attribute("href"));
		
		action.setPriceSelector(By.cssSelector(".discount-price"));
		action.setPriceScraper(Scrapers.text());
		
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
