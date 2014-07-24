/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danidemi.europrice.screenscraping;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author daniele
 */
class ForEachPage implements ScrapeAction {

    private ScrapeAction action;
    private By nextSelector;
    private int pageNumber;
    private int maxPages;

    public ForEachPage() {
    	setMaxPages(3);
    }
    
    public void setMaxPages(int maxPages) {
		this.maxPages = Math.abs( maxPages );
	}
    
	@Override
	public void onStartScraping() {
		pageNumber = 1;
		action.onStartScraping();
	}

	@Override
	public void onEndScraping() {
		action.onEndScraping();
	}

    public void scrape(ScrapeContext ctx) {
        boolean goOn;
        do {

        	ctx.info("Invoking action {} on page {}", action, pageNumber);
        	
            action.scrape(ctx);

            List<WebElement> nextItems = ctx.findElementsFromRoot(nextSelector);
            WebElement nextItem;
            if (!nextItems.isEmpty() && pageNumber<maxPages) {
                nextItem = nextItems.get(0);
                nextItem.click();
                goOn = true;
                pageNumber ++;
            } else {
                goOn = false;
            }
            
            if(goOn){
            	ctx.info("Going to page {} clicking on {}", pageNumber, nextSelector);            	
            }else{
            	ctx.info("Next page item not found, or limit reached. No more page to browse.");
            }
            

        } while (goOn);
    }

    public void setAction(ScrapeAction action) {
        this.action = action;
    }

    public void setNextSelector(By nextSelector) {
        this.nextSelector = nextSelector;
    }


    
    

}
