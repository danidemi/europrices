/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.poc.pricegrabber;

import com.danidemi.europrice.screenscraping.ScrapeAction;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.Scraper;
import com.danidemi.europrice.utils.Utils.Language;
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

	private Language language;

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
	
	/** 
	 * This scraper should return the price as a {@link String}.
	 * It will responsibility of the {@link PriceParser} to actually extract the numeric value. 
	 * */
	public void setPriceScraper(Scraper<String> priceScraper){
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
	public void startScraping() {
		if(Boolean.TRUE != status){
			callback.onStartScraping();
			status = Boolean.TRUE;
		}
	}

	@Override
	public void endScraping() {
		if(Boolean.FALSE != status){
			callback.onEndScraping();
			status = Boolean.FALSE;
		}
	}

	@Override
	public void scrape(ScrapeContext ctx) {

		boolean error = false;
		
		
		// search price
		
		Long priceInCent = null;
		try {
			List<WebElement> findElements = ctx.findElementsFromSubRoot(priceSelector);
			String text = priceScraper.get(findElements.get(0));
			priceInCent = priceParser.parse(text);
		} catch (Exception ex) {
			ctx.info("An error occurred while retrieving price:" + ex.getMessage());
			error = true;
		} 

		// search description
		String descrption = null;
		try {
			List<WebElement> findElements2 = ctx.findElementsFromSubRoot(descriptionSelector);
			descrption = descriptionScraper.get( findElements2.get(0) );						
		}catch(Exception e){
			ctx.info("An error occurred while retrieving price:" + e.getMessage());
			error = true;			
		}
		
		// search url details
		URL href = null;
		try {
			List<WebElement> findElements3 = ctx.findElementsFromSubRoot(detailUrlSelector);
			WebElement theElms = findElements3.get(0);
			String theUrl = this.detailUrlSelectorScraper.get( theElms );
			href = new URL( theUrl);
		} catch (Exception ex) {
			ctx.info("An error occurred while retrieving price:" + ex.getMessage());
			error = true;			
		}		
		
		
		if(!error){
			
			ctx.info("Found");
			ctx.info(descrption);
			ctx.info( String.valueOf( priceInCent ) );
			ctx.info( String.valueOf( href ) );
			
			ScrapedShopItem item = new ScrapedShopItem();
			item.setDescription(descrption);
			item.setPriceInCent(priceInCent);
			item.setUrlDetail(href);
			item.setShopName(shopName);
			item.setLanguage(language);
			
			callback.onNewShopItem(item);
			
		}else{
			ctx.info("Item not scraped due to previous errors");
		}

	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public Language getLanguage() {
		return language;
	}

}
