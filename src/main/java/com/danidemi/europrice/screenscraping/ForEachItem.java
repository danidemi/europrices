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

/**
 * 
 * @author daniele
 */
public class ForEachItem implements ScrapeAction {

	private By itemSelector;
	private ScrapeAction action;
	private int maxItems;
	
	public ForEachItem() {
		setMaxItems(10);
	}

	@Override
	public void scrape(ScrapeContext ctx) {
		
		if(action == null) {
			throw new IllegalStateException("Please, set an action");
		}

		ctx.info("Looking for items {}", itemSelector);
		List<WebElement> items = ctx.findElementsFromRoot(itemSelector);

		int actualSize = Math.min(items.size(), maxItems);
		
		ctx.info("Found {} items, anyhow limited to {}.", items.size(), maxItems);
		for(int i=0; i<actualSize; i++){
			ctx.info("Invoking action {} with item {}", action, i+1);
			WebElement item = items.get(i);
			ctx.setSubRootElement(item);
			action.scrape(ctx);
		}

	}
	
	public void setMaxItems(int maxItems) {
		this.maxItems = Math.abs( maxItems );
	}

	public void setItemSelector(By itemSelector) {
		this.itemSelector = itemSelector;
	}

	public void setAction(ScrapeAction action) {
		this.action = action;
	}

	@Override
	public void startScraping() {
		this.action.startScraping();
	}

	@Override
	public void endScraping() {
		this.action.endScraping();
	}
	


}
