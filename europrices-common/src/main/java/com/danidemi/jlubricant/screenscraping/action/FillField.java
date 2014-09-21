/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.jlubricant.screenscraping.action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.danidemi.jlubricant.screenscraping.context.ElementCardinalityException;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;

/**
 *
 * @author daniele
 */
public class FillField implements ScrapeAction {

    private By fieldSelector;
    private String text;
    
    private EnforceLifecycleSupport support = new EnforceLifecycleSupport(this);
    
	@Override
	public void startScraping() {
		support.startScraping();
	}

	@Override
	public void scrape(ScrapeContext ctx) throws ElementCardinalityException {
		support.scrape(ctx);
		
		ctx.info("Selecting search field '{}'", fieldSelector);
		final WebElement field = ctx.findUniqueElement(fieldSelector);
		
		ctx.info("Clicking search field '{}'", fieldSelector);
		field.click();
		
		ctx.info("Clearing search field '{}'", fieldSelector);
		field.clear();
		
		ctx.info("Typing '{}' in search field '{}'", text, fieldSelector);
		field.sendKeys( text ); 
		
	}
	
	@Override
	public void endScraping() {
		support.endScraping();
	}
    
    public void setFieldSelector(By fieldSelector) {
        this.fieldSelector = fieldSelector;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
}
