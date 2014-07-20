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
class Search implements ScrapeAction {

    private ActionList actionList;
    private final FillField fillField;
    private final Click click;

    public Search() {
        actionList = new ActionList();
        fillField = new FillField();
        actionList.add(fillField);
        click = new Click();
        actionList.add(click);
    }

    @Override
    public void scrape(ScrapeContext ctx) {
        actionList.scrape(ctx);
    }

    public void setSearchField(By fieldSelector) {
        fillField.setFieldSelector(fieldSelector);
    }

    public void setSearchText(String text) {
        fillField.setText(text);
    }

    public void setStartSearchButton(By item) {
        click.setItem(item);
    }
    
    
    
    

}
