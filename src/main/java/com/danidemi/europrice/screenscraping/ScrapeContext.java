/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.screenscraping;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author daniele
 */
public class ScrapeContext {

    private final PhantomJSDriver driver;
    private final Logger log;
    private WebElement sub;
    
    public ScrapeContext(){
    	
    	
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setCapability("phantomjs.binary.path", "/opt/phantomjs/phantomjs/bin/phantomjs");
        Proxy proxy = new Proxy();
        proxy.setHttpProxy("10.1.51.10:80");
        //capabilities.setCapability("proxy", proxy);
        
        
        
        driver = new PhantomJSDriver(capabilities);
        log = LoggerFactory.getLogger("scrape.session");
        Runtime.getRuntime().addShutdownHook(new Thread(){
        	@Override
        	public void run() {
        		log.info("Quitting PhantomJSDriver");
        		ScrapeContext.this.driver.quit();
        		log.info("PhantomJSDriver quitted");
        	}
        });
    }
    
    WebDriver getWebDriver() {
        return driver;
    }

    public void info(String string) {
        log.info(string);
    }

    public void info(String string, Object... params) {
        log.info(string, params);
    }

    /**
     * Expects to find exactly one element.
     * @throws ElementCardinalityException 
     * */
	public WebElement findUniqueElement(By fieldSelector) throws ElementCardinalityException {
		List<WebElement> foundElements = driver.findElements(fieldSelector);
		if( foundElements == null || foundElements.size() == 0 ) throw new NotSuchElementException( fieldSelector, driver );
		if( foundElements.size() != 1 ) throw new TooMuchElementsException( fieldSelector, 1 );
		return foundElements.get(0);
	}

    public WebElement findElement(By fieldSelector) {
        return driver.findElement(fieldSelector);
    }

    public List<WebElement> findElementsFromRoot(By itemSelector) {
        return driver.findElements(itemSelector);
    }
    
    public void setSubRootElement(WebElement sub){
    	info("Context set to {}" + sub);
        this.sub = sub;
    }

    public List<WebElement> findElementsFromSubRoot(By cssSelector) {
    	info("Looking for {} starting from {}", cssSelector, sub);
        return sub.findElements(cssSelector);
    }    
    
}
