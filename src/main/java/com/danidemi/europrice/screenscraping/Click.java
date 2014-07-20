/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.screenscraping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author daniele
 */
public class Click implements ScrapeAction {

    private By item;    
    
    @Override
    public void scrape(ScrapeContext ctx) {
        
        ctx.info("Clicking on {}", item);
        
        ctx.findElement(item).click();
    }

    public void setItem(By item) {
        this.item = item;
    }
    
    

    
}
