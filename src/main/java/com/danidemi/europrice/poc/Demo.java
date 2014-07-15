/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.poc;

import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.EPriceItGrabber;
import com.danidemi.europrice.poc.pricegrabber.OsSelectionGrabber;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.poc.pricegrabber.StockistiGrabber;
import com.danidemi.europrice.poc.pricegrabber.SysoutCallback;

/**
 *
 * @author danidemi
 */
public class Demo {
    
    
    public static void main(String[] args){
        
        Request r = new Request("sony");
        
        Callback callback = new SysoutCallback();
        
        new OsSelectionGrabber();
        new StockistiGrabber();
        new EPriceItGrabber().run(r, callback);
        
        
    }
    
}
