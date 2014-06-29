/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.poc.pricegrabber;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 *
 * @author danidemi
 */
class SysoutCallback implements Callback {

    public SysoutCallback() {
    }

    @Override
    public void onStart() {
        
    }

    @Override
    public void onNewItem(Item item) {
        System.out.println("====================================");
        System.out.println("Price:" + DecimalFormat.getCurrencyInstance(Locale.ITALIAN).format( item.getPriceInCent() / 100.0) );
        System.out.println("Product:" + item.getDescription());
        System.out.println("URL:" + item.getUrlDetail()  );
        System.out.println("====================================");
    }

    @Override
    public void onEnd() {
        
    }
    
}
