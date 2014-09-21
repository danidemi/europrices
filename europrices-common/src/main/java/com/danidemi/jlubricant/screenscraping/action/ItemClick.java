/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.jlubricant.screenscraping.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.danidemi.jlubricant.screenscraping.ScreenScrapingException;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;

/**
 * Click on the given item.
 * @author daniele
 */
public class ItemClick implements ScrapeAction {

    private By item;    
    
    @Override
    public void scrape(ScrapeContext ctx) throws ScreenScrapingException {
        
    	WebElement findElement = ctx.findElement(item);
    	if(findElement==null){
    		throw new ScreenScrapingException("I was supposed to click on item identified by " + item + " but I was not able to find such item.");
    	}
        ctx.info("Clicking on {}", item);
        
		findElement.click();
    }

    public void setItemToClick(By item) {
        this.item = item;
    }

	@Override
	public void startScraping() {		
	}

	@Override
	public void endScraping() {		
	}
    
    

    
}
