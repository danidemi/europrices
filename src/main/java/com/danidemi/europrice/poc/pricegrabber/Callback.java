package com.danidemi.europrice.poc.pricegrabber;

/**
 *
 * @author danidemi
 */
public interface Callback {
    
    void onStart();
    
    void onNewItem( Item item );
    
    void onEnd();
    
}
