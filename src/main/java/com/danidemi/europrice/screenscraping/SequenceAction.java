/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.danidemi.europrice.screenscraping;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of actions executed one after the other.
 * @author daniele
 */
public class SequenceAction implements ScrapeAction {
		
    private List<ScrapeAction> actions = new ArrayList<>();
 
    @Override
	public void startScraping() {
    	
	}
    
    @Override
    public void scrape(ScrapeContext ctx) {
        for (ScrapeAction scrapeAction : actions) {
        	scrapeAction.startScraping();
            scrapeAction.scrape(ctx);
            scrapeAction.endScraping();
        }
    }
    
    @Override
	public void endScraping() {
	
	}

    public void add(ScrapeAction action) {
        actions.add( action );
    }
    
	public final SequenceAction then(ScrapeAction sa){
		this.add(sa);
		return this;
	}    
    
}
