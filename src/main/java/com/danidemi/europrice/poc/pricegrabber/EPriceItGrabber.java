/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.danidemi.europrice.poc.pricegrabber;

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
 * @author danidemi
 */
public class EPriceItGrabber implements Grabber {

    private final HtmlUnitDriver driver;
    private final PriceParser priceParser;

    public EPriceItGrabber() {
        driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
        driver.setJavascriptEnabled(true);
        priceParser = new PriceParser();
    }

    @Override
    public void run(Request request, Callback callback) {

        String searchTerm = request.getSearchTerm();

        try{
            driver.get("http://www.eprice.it/search/qs=" + searchTerm + "&mets=" + searchTerm);
        }catch(Exception e){
            
        }
        

        do {
            final String divitem = ".item";

            // count number of items
            List<WebElement> findElementsByCssSelector = driver.findElementsByCssSelector(divitem);
            System.out.println( findElementsByCssSelector + " found for " + divitem + " in " + driver.getPageSource());
            
            // for each item extract info
            for (WebElement productBox : findElementsByCssSelector) {

                // price
                List<WebElement> findElements = productBox.findElements(By.cssSelector(".itemPrice"));
                String text = findElements.get(0).getText();
                Long priceInCent = null;
                try {
                    priceInCent = priceParser.parse(text);
                } catch (ParserException ex) {
                    Logger.getLogger(EPriceItGrabber.class.getName()).log(Level.SEVERE, null, ex);
                }

                //description
                findElements = productBox.findElements(By.cssSelector(".textCont"));
                String text2 = findElements.get(0).getText();

                //url detail
                List<WebElement> findElements2 = productBox.findElements(By.cssSelector(".linkTit"));
                final WebElement desc = findElements2.get(0);
                text = desc.getText();
                URL href = null;
                try {
                    href = new URL(desc.getAttribute("href"));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(OsSelectionGrabber.class.getName()).log(Level.SEVERE, null, ex);
                }

                Item item = new Item();
                item.setDescription(text2);
                item.setPriceInCent(priceInCent);
                item.setUrlDetail(href);

                callback.onNewItem(item);

            }

        }while (goToNextPageIfPossible());
    }

    private boolean goToNextPageIfPossible() {
        List<WebElement> findElementsByCssSelector = driver.findElementsByCssSelector(".btnAvanti");
        if(!findElementsByCssSelector.isEmpty()){
            findElementsByCssSelector.get(0).click();
            return true;
        }else{
            return false;
        }
    }

}
