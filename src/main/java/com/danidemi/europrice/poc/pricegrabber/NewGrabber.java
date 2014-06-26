package com.danidemi.europrice.poc.pricegrabber;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *
 * @author danidemi
 */
public class NewGrabber {

    public static void main(String[] args) {
        new NewGrabber().run();
    }

    private void run() {
        //WebDriver driver = new HtmlUnitDriver( true );
        
        WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
        ((HtmlUnitDriver) driver).setJavascriptEnabled(true);
        
        driver.get( "http://www.oselection.es/" );
        
        // clear and fill the search form
        driver.findElement(By.name("populate")).click();
        driver.findElement(By.name("populate")).clear();
        driver.findElement(By.name("populate")).sendKeys("Sony Xperia Z2");        
        
        // click the search form
        driver.findElement(By.name("op")).click();        
        
        // print price
        String text = driver.findElement(By.cssSelector("span.discount-price")).getText();        
        
        System.out.println( text );
    }
    
}
