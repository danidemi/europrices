/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.poc.pricegrabber;

/**
 *
 * @author danidemi
 */
interface Grabber {
    
    public void run(Request request, Callback callback);
    
}
