package com.danidemi.europrice.poc.pricegrabber;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author danidemi
 */
public class UtuizamobiSiGrabber implements Grabber {

    private final PhantomJSDriver driver;
    private final PriceParser priceParser;

    public UtuizamobiSiGrabber() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("phantomjs.binary.path", "/tmp/phantomjs-1.9.7-linux-i686/bin/phantomjs");
        System.out.println(capabilities.asMap());
        driver = new PhantomJSDriver(capabilities);

        priceParser = new PriceParser();

    }

    @Override
    public void run(Request request, Callback callback) {
        String searchTerm = request.getSearchTerm();

        try {
            driver.get("http://www.etuizamobi.si/catalogsearch/result/?q=" + searchTerm);
        } catch (Exception e) {

        }

        final String divitem = ".product-box";

        // count number of items
        List<WebElement> findElementsByCssSelector = driver.findElementsByCssSelector(divitem);
        System.out.println(findElementsByCssSelector + " found for " + divitem + " in " + driver.getPageSource());

        // for each item extract info
        for (WebElement productBox : findElementsByCssSelector) {

            // price
            List<WebElement> findElements = productBox.findElements(By.cssSelector(".price"));
            String text = findElements.get(0).getText();
            Long priceInCent = null;
            try {
                priceInCent = priceParser.parse(text);
            } catch (ParserException ex) {
                Logger.getLogger(EPriceItGrabber.class.getName()).log(Level.SEVERE, null, ex);
            }

            //description
            findElements = productBox.findElements(By.cssSelector(".product-name a"));
            String text2 = findElements.get(0).getText();

            //url detail
            List<WebElement> findElements2 = productBox.findElements(By.cssSelector(".product-name a"));
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

    }
    
}
