/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice;

import tmp.EPriceItGrabber;
import tmp.OsSelectionGrabber;
import tmp.StockistiGrabber;
import tmp.UtuizamobiSiGrabber;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProductCallback;
import com.danidemi.europrice.pricegrabber.screenscraping.action.SysoutProductCallback;
import com.danidemi.europrice.pricegrabber.screenscraping.shops.Request;

/**
 *
 * @author danidemi
 */
public class Demo {
    
    
    public static void main(String[] args){
        
        Request r = new Request("sony");
        
        ScrapedProductCallback callback = new SysoutProductCallback();
        
        new OsSelectionGrabber();
        new StockistiGrabber();
        new EPriceItGrabber();
        new UtuizamobiSiGrabber().run(r, callback);
        
    }
    
}
