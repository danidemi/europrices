/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.screenscraping;

import com.danidemi.europrice.poc.pricegrabber.PriceParser;
import java.util.List;
import org.openqa.selenium.By;
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
    
    ScrapeContext(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("phantomjs.binary.path", "/opt/phantomjs/phantomjs-1.9.7-linux-x86_64/bin/phantomjs");
        driver = new PhantomJSDriver(capabilities);
        log = LoggerFactory.getLogger("scrape.session");
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

    public WebElement findElement(By fieldSelector) {
        return driver.findElement(fieldSelector);
    }

    public List<WebElement> findElements(By itemSelector) {
        return driver.findElements(itemSelector);
    }
    
    public void setSubcontext(WebElement sub){
        this.sub = sub;
    }

    public List<WebElement> findElement2(By cssSelector) {
        return sub.findElements(cssSelector);
    }
    
    
    
    
    
}
