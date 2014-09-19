/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.pricegrabber.screenscraping.action;

import com.danidemi.europrice.utils.Utils.Language;
import com.danidemi.jlubricant.screenscraping.action.ScrapeAction;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;
import com.danidemi.jlubricant.screenscraping.selector.InsideElementSelector;
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
 * An action that retrieve some information about a single item, as price, product description, and so on.
 * This action then provides the scraped product to a provided callback.
 * @author daniele
 */
public class ProductScrapeAction implements ScrapeAction {

	private PriceParser priceParser;
	
	private ScrapedProductCallback callback;
	private Boolean status;
	
	private By priceSelector;
	private InsideElementSelector<String> priceScraper;
	
	private By descriptionSelector;
	private InsideElementSelector<String> descriptionScraper;

	private By detailUrlSelector;
	private InsideElementSelector<String> detailUrlSelectorScraper;

	private String shopName;

	private Language language;

	public ProductScrapeAction(ScrapedProductCallback callback) {
		priceParser = new PriceParser();
		this.callback = callback;
	}
	
	public ProductScrapeAction() {
		priceParser = new PriceParser();
	}
	
	public void setCallback(ScrapedProductCallback callback) {
		this.callback = callback;
	}
	
	public void setPriceSelector(By priceSelector) {
		this.priceSelector = priceSelector;
	}
	
	/** 
	 * This scraper should return the price as a {@link String}.
	 * It will responsibility of the {@link PriceParser} to actually extract the numeric value. 
	 * */
	public void setPriceScraper(InsideElementSelector<String> priceScraper){
		this.priceScraper = priceScraper;
	}
	
	public void setDescriptionSelector(By descriptionSelector) {
		this.descriptionSelector = descriptionSelector;
	}
	public void setDescriptionScraper(InsideElementSelector descriptionScraper) {
		this.descriptionScraper = descriptionScraper;
	}
	

	public void setDetailUrlSelector(By detailUrlSelector) {
		this.detailUrlSelector = detailUrlSelector;
	}
	public void setDetailUrlScraper(
			InsideElementSelector<String> detailUrlSelectorScraper) {
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
			
			ScrapedProduct item = new ScrapedProduct();
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
