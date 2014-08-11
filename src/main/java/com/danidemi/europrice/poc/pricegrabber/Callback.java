package com.danidemi.europrice.poc.pricegrabber;

/**
 *
 * @author danidemi
 */
public interface Callback {
    
    void onStartScraping();
    
    void onNewShopItem( ShopItem item );
    
    void onEndScraping();
    
}
