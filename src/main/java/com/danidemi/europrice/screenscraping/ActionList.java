/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.screenscraping;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniele
 */
public class ActionList implements ScrapeAction {
	
	public final ActionList then(ScrapeAction sa){
		this.add(sa);
		return this;
	}
	
    private List<ScrapeAction> actions = new ArrayList<>();
 
    @Override
	public void onStartScraping() {
	
	}
    
    @Override
    public void scrape(ScrapeContext ctx) {
        for (ScrapeAction scrapeAction : actions) {
            scrapeAction.scrape(ctx);
        }
    }
    
    @Override
	public void onEndScraping() {
	
	}

    public void add(ScrapeAction action) {
        actions.add( action );
    }
    
}
