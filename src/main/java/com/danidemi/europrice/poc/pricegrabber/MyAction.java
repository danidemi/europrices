/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.poc.pricegrabber;

import com.danidemi.europrice.screenscraping.ScrapeAction;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.Scraper;
import com.gargoylesoftware.htmlunit.BrowserVersion;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * 
 * @author daniele
 */
public class MyAction implements ScrapeAction {

	private PriceParser priceParser;
	
	private Callback callback;
	private Boolean status;
	
	private By priceSelector;
	private Scraper<String> priceScraper;
	
	private By descriptionSelector;
	private Scraper<String> descriptionScraper;

	private By detailUrlSelector;
	private Scraper<String> detailUrlSelectorScraper;

	private String shopName;

	public MyAction(Callback callback) {
		priceParser = new PriceParser();
		this.callback = callback;
	}
	
	public MyAction() {
		priceParser = new PriceParser();
	}
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public void setPriceSelector(By priceSelector) {
		this.priceSelector = priceSelector;
	}
	public void setPriceScraper(Scraper priceScraper){
		this.priceScraper = priceScraper;
	}
	
	public void setDescriptionSelector(By descriptionSelector) {
		this.descriptionSelector = descriptionSelector;
	}
	public void setDescriptionScraper(Scraper descriptionScraper) {
		this.descriptionScraper = descriptionScraper;
	}
	

	public void setDetailUrlSelector(By detailUrlSelector) {
		this.detailUrlSelector = detailUrlSelector;
	}
	public void setDetailUrlScraper(
			Scraper<String> detailUrlSelectorScraper) {
		this.detailUrlSelectorScraper = detailUrlSelectorScraper;
	}
	
	@Override
	public void onStartScraping() {
		if(Boolean.TRUE != status){
			callback.onStart();
			status = Boolean.TRUE;
		}
	}

	@Override
	public void onEndScraping() {
		if(Boolean.FALSE != status){
			callback.onEnd();
			status = Boolean.FALSE;
		}
	}

	@Override
	public void scrape(ScrapeContext ctx) {


		
		// search price
		List<WebElement> findElements = ctx.findElementsFromSubRoot(priceSelector);
		String text = priceScraper.get(findElements.get(0));
		Long priceInCent = null;
		try {
			priceInCent = priceParser.parse(text);
		} catch (ParserException ex) {
			ctx.info(ex.getMessage());
		}

		// search description
		List<WebElement> findElements2 = ctx.findElementsFromSubRoot(descriptionSelector);
		String descrption = descriptionScraper.get( findElements2.get(0) );
		
		// search url details
		List<WebElement> findElements3 = ctx.findElementsFromSubRoot(detailUrlSelector);
		URL href = null;
		try {
			WebElement theElms = findElements3.get(0);
			String theUrl = this.detailUrlSelectorScraper.get( theElms );
			href = new URL( theUrl);
		} catch (MalformedURLException ex) {
			ctx.info(ex.getMessage());
		}		// TODO Auto-generated method stub
		
		ctx.info("Found");
		ctx.info(descrption);
		ctx.info( String.valueOf( priceInCent ) );
		ctx.info( String.valueOf( href ) );

		Item item = new Item();
		item.setDescription(descrption);
		item.setPriceInCent(priceInCent);
		item.setUrlDetail(href);
		item.setShopName(shopName);

	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

}
