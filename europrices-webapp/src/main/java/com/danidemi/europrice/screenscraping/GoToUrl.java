/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.screenscraping;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author daniele
 */
public class GoToUrl implements ScrapeAction {
    
    private URL url;

    public GoToUrl(String url) throws MalformedURLException {
        this( new URL( url) );
    }
    
    GoToUrl(URL url) {
        this.url = url;
    }
    
    @Override
    public void scrape(ScrapeContext ctx) {
        
        ctx.info("Browsing to {}", url.toString());
        
        WebDriver driver = ctx.getWebDriver();
        driver.get( url.toString() );
        
        ctx.info("Went to {}", url.toString());
    }

	@Override
	public void startScraping() {
	}

	@Override
	public void endScraping() {		
	}
    
}
