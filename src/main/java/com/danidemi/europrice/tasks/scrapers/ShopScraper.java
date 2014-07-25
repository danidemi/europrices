package com.danidemi.europrice.tasks.scrapers;

import com.danidemi.europrice.poc.pricegrabber.Callback;


public interface ShopScraper extends Runnable {

	void setCallback(Callback callback);
	

}
