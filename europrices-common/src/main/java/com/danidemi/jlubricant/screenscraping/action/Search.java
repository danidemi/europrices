/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danidemi.jlubricant.screenscraping.action;

import org.openqa.selenium.By;

import com.danidemi.jlubricant.screenscraping.ScreenScrapingException;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;

/**
 *
 * @author daniele
 */
public class Search implements ScrapeAction {

    private Sequence actionList;
    private final FillField fillField;
    private final ItemClick click;

    public Search() {
        actionList = new Sequence();
        fillField = new FillField();
        actionList.add(fillField);
        click = new ItemClick();
        actionList.add(click);
    }
        
    @Override
    public void startScraping() {
    	actionList.startScraping();
    }
    
    @Override
    public void endScraping() {
    	actionList.endScraping();
    }

    @Override
    public void scrape(ScrapeContext ctx) throws ScreenScrapingException {
        actionList.scrape(ctx);
    }

    public void setSearchField(By fieldSelector) {
        fillField.setFieldSelector(fieldSelector);
    }

    public void setSearchText(String text) {
        fillField.setText(text);
    }

    public void setStartSearchButton(By item) {
        click.setItemToClick(item);
    }
    
    
    
    

}
