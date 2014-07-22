/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.screenscraping;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.MyAction;
import com.danidemi.europrice.poc.pricegrabber.SysoutCallback;

/**
 *
 * @author daniele
 */
public class ScrapeScript {
    
    public static void main(String[] args) {
        try {
            new ScrapeScript().scrape();
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }
        
    private void scrape2() throws MalformedURLException {
        
        ActionList list = new ActionList();
        
        list.add( new GoToUrl("http://www.oselection.es/") );
        
        final Search search = new Search();
        search.setSearchField( By.cssSelector("input.buscador-text") );
        search.setSearchText("Samsung");
        search.setStartSearchButton( By.cssSelector("input.buscador-submit") );
        list.add( search);
        
        ForEachPage forEachPage = new ForEachPage();
        forEachPage.setNextSelector(By.cssSelector("li.pager-next a"));
        
        final ForEachItem forEachItem = new ForEachItem();
        forEachItem.setItemSelector(By.cssSelector("div.article-inner"));
        
        MyAction action = new MyAction(new SysoutCallback());
        action.setDescriptionSelector(By.cssSelector("H1"));
        action.setDescriptionScraper(Scrapers.text());
        
        action.setDetailUrlSelector(By.cssSelector("H1 A"));
        action.setDetailUrlScraper(Scrapers.attribute("href"));
        
        action.setPriceSelector(By.cssSelector(".discount-price"));
        action.setPriceScraper(Scrapers.text());            
        
		forEachItem.setAction(action);
        
        forEachPage.setAction(forEachItem);
        list.add(forEachPage);
                
        list.onStartScraping();
        list.scrape(new ScrapeContext());
        
    }

    private void scrape() throws MalformedURLException {
    	
        MyAction action = new MyAction(new SysoutCallback());
        action.setDescriptionSelector(By.cssSelector("H1"));
        action.setDescriptionScraper(Scrapers.text());
        
        action.setDetailUrlSelector(By.cssSelector("H1 A"));
        action.setDetailUrlScraper(Scrapers.attribute("href"));
        
        action.setPriceSelector(By.cssSelector(".discount-price"));
        action.setPriceScraper(Scrapers.text());
        
        ActionList list = new ActionList()
        	.then( new GoToUrl("http://www.oselection.es/") )
        	.then( 
        			new Search()
        			.withSearchField(By.cssSelector("input.buscador-text"), "Samsung")
        			.withSearchButton(By.cssSelector("input.buscador-submit"))
        	)
        	.then( 
        			ForEachPage.forEachPageWithNextLinkDo(
        					By.cssSelector("li.pager-next a"),
        					ForEachItem.forEachItem(
        							By.cssSelector("div.article-inner"), 
        							action)
        					
        			)
        	)
        	;
        

        list.onStartScraping();
        list.scrape(new ScrapeContext());
        list.onEndScraping();
        
    }
    
    
}
