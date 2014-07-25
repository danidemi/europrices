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
import com.danidemi.europrice.poc.pricegrabber.MyAction;
import com.danidemi.europrice.poc.pricegrabber.SysoutCallback;
import com.danidemi.europrice.screenscraping.ActionList;
import com.danidemi.europrice.screenscraping.ForEachItem;
import com.danidemi.europrice.screenscraping.ForEachPage;
import com.danidemi.europrice.screenscraping.GoToUrl;
import com.danidemi.europrice.screenscraping.ScrapeAction;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.Scrapers;
import com.danidemi.europrice.screenscraping.Search;
import com.danidemi.europrice.screenscraping.SimpleScreenScraper;

/**
 *
 * @author daniele
 */
public class OSelectionScraper implements ShopScraper {
    
    private ActionList root;
	private MyAction action;

	public OSelectionScraper() {
    	
    	try{
    		
    		action = new MyAction();
    		action.setDescriptionSelector(By.cssSelector("H1"));
    		action.setDescriptionScraper(Scrapers.text());
    		
    		action.setDetailUrlSelector(By.cssSelector("H1 A"));
    		action.setDetailUrlScraper(Scrapers.attribute("href"));
    		
    		action.setPriceSelector(By.cssSelector(".discount-price"));
    		action.setPriceScraper(Scrapers.text());
    		
    		action.setShopName("oselection.es");
    		
    		root = 
    				inSequence( new GoToUrl("http://www.oselection.es/") )
    				.then(
    						search("Samsung", By.cssSelector("input.buscador-text"), By.cssSelector("input.buscador-submit"))
    						)
    						.then( 
    								forEachPageWithNextLinkDo(
    										By.cssSelector("li.pager-next a"), 
    										forEachItem(
    												By.cssSelector("div.article-inner"), 
    												action))
    								)
    								;
    		
    	}catch(Exception e){
    		throw new RuntimeException(e);
    	}
    	
                
    }
	
	@Override
	public void setCallback(Callback callback) {
		action.setCallback(callback);
	}

	@Override
	public void run() {
		root.onStartScraping();
		root.scrape( new ScrapeContext() );
		root.onEndScraping();
	}
    
}
