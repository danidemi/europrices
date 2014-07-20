/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.poc.pricegrabber;

import com.danidemi.europrice.screenscraping.ScrapeAction;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *
 * @author daniele
 */
public class MyAction implements ScrapeAction {
    
    private PriceParser priceParser;
    
    public MyAction() {
        priceParser = new PriceParser();
    }    

    @Override
    public void scrape(ScrapeContext ctx) {
        
        List<WebElement> findElements = ctx.findElement2(By.cssSelector(".discount-price"));
        
                String text = findElements.get(0).getText();
                Long priceInCent = null;
                try {
                    priceInCent = priceParser.parse( text );
                } catch (ParserException ex) {
                    ctx.info(ex.getMessage());
                }

                List<WebElement> findElements2 = ctx.findElement2(By.cssSelector("header h1 a"));
                final WebElement desc = findElements2.get(0);
                String text2 = desc.getText();
                URL href = null;
                try {
                    href = new URL( desc.getAttribute("href") );
                } catch (MalformedURLException ex) {
                    ctx.info(ex.getMessage());
                }
                
                
                
                Item item = new Item();
                item.setDescription(text2);
                item.setPriceInCent(priceInCent);
                item.setUrlDetail(href);
                
                System.out.println("item = " + item);
    }
    
}
