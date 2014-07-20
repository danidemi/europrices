/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.screenscraping;

import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;

/**
 *
 * @author daniele
 */
public class ScrapeScript {
    
    public static void main(String[] args) {
        try {
            new ScrapeScript().scrape();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void scrape() throws MalformedURLException {
        
        ActionList list = new ActionList();
        
        list.add( new GoToUrl("http://www.oselection.es/") );
        
        final Search search = new Search();
        search.setSearchField(By.name("populate"));
        search.setSearchText("Samsung");
        search.setStartSearchButton(By.name("op"));
        list.add( search);
        
        ForEachPage forEachPage = new ForEachPage();
        forEachPage.setNextSelector(By.cssSelector("li.pager-next a"));
        final ForEachItem forEachItem = new ForEachItem();
        forEachItem.setItemSelector(By.cssSelector("div.article-inner"));
        
        forEachPage.setAction(forEachItem);
        list.add(forEachPage);
                
        list.scrape(new ScrapeContext());
        
    }
    
    
}
