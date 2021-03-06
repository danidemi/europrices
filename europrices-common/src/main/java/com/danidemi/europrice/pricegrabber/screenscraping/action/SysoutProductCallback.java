/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.pricegrabber.screenscraping.action;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 *
 * @author danidemi
 */
public class SysoutProductCallback implements ScrapedProductCallback {

    public SysoutProductCallback() {
    }

    @Override
    public void onStartScraping() {
        
    }

    @Override
    public void onNewShopItem(ScrapedProduct item) {
        System.out.println("====================================");
        System.out.println("Price:" + DecimalFormat.getCurrencyInstance(Locale.ITALIAN).format( item.getPriceInCent() / 100.0) );
        System.out.println("Product:" + item.getDescription());
        System.out.println("URL:" + item.getUrlDetail()  );
        System.out.println("====================================");
    }

    @Override
    public void onEndScraping() {
        
    }
    
}
