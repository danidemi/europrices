/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.jlubricant.screenscraping.action;

import com.danidemi.jlubricant.screenscraping.ScreenScrapingException;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;

/**
 * A generic screen scraping action.
 */
public interface ScrapeAction {

	/**
	 * Invoked to inform this {@link ScrapeAction} that scraping is started.
	 */
	void startScraping();
	
	/**
	 * Invoked to require the action to actually run.
	 * @param ctx
	 */
    public void scrape(ScrapeContext ctx) throws ScreenScrapingException;

	/**
	 * Invoked to inform this {@link ScrapeAction} that scraping is ended.
	 */    
	void endScraping();
    
}
