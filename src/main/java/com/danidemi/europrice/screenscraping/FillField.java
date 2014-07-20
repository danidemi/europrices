/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.screenscraping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author daniele
 */
public class FillField implements ScrapeAction {

    private By fieldSelector;
    private String text;
    
    @Override
    public void scrape(ScrapeContext ctx) {
       
        ctx.info("Typing '{}' in field identified by '{}'", text, fieldSelector);
        final WebElement field = ctx.findElement(fieldSelector);
        
        field.click();
        field.clear();
        field.sendKeys( text ); 
        
    }

    public void setFieldSelector(By fieldSelector) {
        this.fieldSelector = fieldSelector;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
}
