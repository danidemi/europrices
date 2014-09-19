package com.danidemi.jlubricant.screenscraping.action;

import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;


/**
 * A support class that helps in veryfing that the action lifecycle is respected by the caller
 * @author danidemi
 *
 */
class EnforceLifecycleSupport implements ScrapeAction {

	private boolean isStarted;
	private ScrapeAction master;
	
	public EnforceLifecycleSupport(ScrapeAction master) {
		this.isStarted = false;
		this.master = master;
	}

	@Override
	public void startScraping() {
		if(isStarted) throw new IllegalStateException("Action " + master + " has been already started. It cannot be started twice.");
		this.isStarted = true;
	}

	@Override
	public void scrape(ScrapeContext ctx) {
		if(!isStarted) throw new IllegalStateException("Action " + master + " has not been started, so it cannot srape yet.");
	}

	@Override
	public void endScraping() {
		if(!isStarted) throw new IllegalStateException("Action " + master + " has been already ended. It cannot be ended twice.");
		this.isStarted = false;
	}

}
