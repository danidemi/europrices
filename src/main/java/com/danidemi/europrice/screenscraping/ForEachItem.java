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

	@Override
	public void scrape(ScrapeContext ctx) {
		
		if(action == null) {
			throw new IllegalStateException("Please, set an action");
		}

		ctx.info("Looking for items {}", itemSelector);
		List<WebElement> items = ctx.findElementsFromRoot(itemSelector);

		ctx.info("Found {} items", items.size());
		for(int i=0; i<items.size(); i++){
			ctx.info("Invoking action {} with item {}", action, i+1);
			WebElement item = items.get(i);
			ctx.setSubRootElement(item);
			action.scrape(ctx);
		}

	}

	public void setItemSelector(By itemSelector) {
		this.itemSelector = itemSelector;
	}

	public void setAction(ScrapeAction action) {
		this.action = action;
	}

	@Override
	public void onStartScraping() {
		this.action.onStartScraping();
	}

	@Override
	public void onEndScraping() {
		this.action.onEndScraping();
	}

}
