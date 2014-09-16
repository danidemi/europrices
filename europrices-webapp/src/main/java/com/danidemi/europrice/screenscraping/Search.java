/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danidemi.europrice.screenscraping;

import org.openqa.selenium.By;

/**
 *
 * @author daniele
 */
public class Search implements ScrapeAction {

    private SequenceAction actionList;
    private final FillField fillField;
    private final ItemClick click;

    public Search() {
        actionList = new SequenceAction();
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
    public void scrape(ScrapeContext ctx) throws ScrapeException {
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
