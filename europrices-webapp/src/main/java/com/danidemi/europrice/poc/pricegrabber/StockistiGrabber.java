/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.poc.pricegrabber;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *
 * @author danidemi
 */
public class StockistiGrabber implements Grabber {

    private HtmlUnitDriver driver;
    private PriceParser priceParser;
    
    public StockistiGrabber() {
        driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
        driver.setJavascriptEnabled(true);
        priceParser = new PriceParser();
                
    }    
    
    
    @Override
    public void run(Request request, Callback callback) {

        callback.onStartScraping();
        
        driver.get( "http://www.glistockisti.it/" );
        
        final WebElement searchForm = driver.findElement(By.id("search"));
        searchForm.click();        
        searchForm.clear();
        searchForm.sendKeys( request.getSearchTerm() );
        
        // click the search form
        driver.findElement(By.cssSelector("button.button")).click();          
        
        // count number of items
        List<WebElement> items = driver.findElementsByCssSelector(".prodotto-griglia");
        
        for (WebElement item : items) {
            
            
            
        }
        
        callback.onEndScraping();
        
    }
    
}
