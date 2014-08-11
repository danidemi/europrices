/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.screenscraping;

/**
 * A generic scrape action.
 *
 * @author daniele
 */
public interface ScrapeAction {

	void startScraping();
	
    public void scrape(ScrapeContext ctx);

	void endScraping();
    
}
