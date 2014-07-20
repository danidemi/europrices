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

    public ForEachPage() {
    }

    public void scrape(ScrapeContext ctx) {
        boolean goOn;
        do {

            action.scrape(ctx);

            List<WebElement> findElements = ctx.findElements(nextSelector);
            WebElement findElementByCssSelector;
            if (!findElements.isEmpty()) {
                findElementByCssSelector = findElements.get(0);
                findElementByCssSelector.click();
                goOn = true;
            } else {
                goOn = false;
            }
            
            ctx.info("Going to next page clicking on {} ? {}", nextSelector, goOn);

        } while (goOn);
    }

    public void setAction(ScrapeAction action) {
        this.action = action;
    }

    public void setNextSelector(By nextSelector) {
        this.nextSelector = nextSelector;
    }
    
    

}
