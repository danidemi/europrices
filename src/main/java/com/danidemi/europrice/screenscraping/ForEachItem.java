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

    private WebDriver driver;
    private By itemSelector;
    private ScrapeAction action;
    
    @Override
    public void scrape(ScrapeContext ctx) {
        
            ctx.info("Looking for items {}", itemSelector);
            List<WebElement> items = ctx.findElements(itemSelector);

            ctx.info("Found {} items", items.size());
            for (WebElement item : items) {
                ctx.setSubcontext(item);
                action.scrape(ctx);
            }
    }

    public void setItemSelector(By itemSelector) {
        this.itemSelector = itemSelector;
    }

    public void setAction(ScrapeAction action) {
        this.action = action;
    }
    
}
