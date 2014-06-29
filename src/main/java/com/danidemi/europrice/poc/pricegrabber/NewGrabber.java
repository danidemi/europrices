package com.danidemi.europrice.poc.pricegrabber;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *
 * @author danidemi
 */
public class NewGrabber {
    
    private HtmlUnitDriver driver;
    private PriceParser priceParser;
    private Callback callback;
    
    public static void main(String[] args) {
        new NewGrabber().run();
        
    }

    public NewGrabber() {
        driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
        driver.setJavascriptEnabled(true);
        priceParser = new PriceParser();
        callback = new SysoutCallback();
        
    }
    
    private void run() {
        //WebDriver driver = new HtmlUnitDriver( true );
        
    
    
        
        driver.get( "http://www.oselection.es/" );
        
        // clear and fill the search form
        driver.findElement(By.name("populate")).click();
        driver.findElement(By.name("populate")).clear();
        //driver.findElement(By.name("populate")).sendKeys("Sony Xperia Z2");        
        driver.findElement(By.name("populate")).sendKeys("Sony");        
        
        // click the search form
        driver.findElement(By.name("op")).click();        
        
        do {
            

        
            // count number of items
            List<WebElement> findElementsByCssSelector = driver.findElementsByCssSelector("div.article-inner");

            // for each item extract info
            for (WebElement productBox : findElementsByCssSelector) {

                List<WebElement> findElements = productBox.findElements(By.cssSelector(".discount-price"));
                String text = findElements.get(0).getText();
                Long priceInCent = null;
                try {
                    priceInCent = priceParser.parse( text );
                } catch (ParserException ex) {
                    Logger.getLogger(NewGrabber.class.getName()).log(Level.SEVERE, null, ex);
                }

                List<WebElement> findElements2 = productBox.findElements(By.cssSelector("header h1 a"));
                final WebElement desc = findElements2.get(0);
                String text2 = desc.getText();
                URL href = null;
                try {
                    href = new URL( desc.getAttribute("href") );
                } catch (MalformedURLException ex) {
                    Logger.getLogger(NewGrabber.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
                Item item = new Item();
                item.setDescription(text2);
                item.setPriceInCent(priceInCent);
                item.setUrlDetail(href);
                
                callback.onNewItem(item);


            }

        
        }while(goToNextPageIfPossible());

        
        driver.close();
        driver.quit();
        
        
    }

    private boolean goToNextPageIfPossible() {
        List<WebElement> findElements = driver.findElements(By.cssSelector("li.pager-next a"));
        WebElement findElementByCssSelector;
        if(!findElements.isEmpty()){ 
            findElementByCssSelector=findElements.get(0);
            findElementByCssSelector.click();
            return true;
        }else{
            return false;
        }
    }
    
}
