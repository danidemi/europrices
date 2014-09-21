package com.danidemi.europrice.pricegrabber.screenscraping.action;


/**
 *
 * @author danidemi
 */
public interface ScrapedProductCallback {
    
	/**
	 * Invoked when the scraping start.
	 */
    void onStartScraping();
    
    /**
     * Invoked every time a product is found.
     */
    void onNewShopItem( ScrapedProduct item );

	/**
	 * Invoked when the scraping end.
	 */    
    void onEndScraping();
    
}
