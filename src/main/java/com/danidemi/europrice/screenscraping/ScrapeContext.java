/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.screenscraping;

import java.util.List;
import java.util.Set;

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

	private static final Logger log = LoggerFactory.getLogger(ScrapeContext.class);
	
	private WebDriver driver;
    private WebElement sub;
    
    public ScrapeContext(PhantomJSDriver driver2) {
    	this.driver = driver2;
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
    
    public void close() {
    	Set<String> windowHandles = driver.getWindowHandles();
    	for (String handle : windowHandles) {
    		driver.switchTo().window(handle);
    		log.info("Closing window {}", driver.getTitle());
    		driver.close();
		}
    	log.info("Closing driver");
    	driver.quit();
    	
    }
    
}
