package com.danidemi.europrice.tasks;

import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.ScrapedShopItem;

public class DelegateCallback implements Callback {

	private Callback delegate;
	
	public DelegateCallback() {
	}
	
	public DelegateCallback(Callback delegate) {
		super();
		this.delegate = delegate;
	}

	public final void setDelegate(Callback delegate) {
		this.delegate = delegate;
	}

	@Override
	public void onStartScraping() {
		delegate.onStartScraping();
	}

	@Override
	public void onNewShopItem(ScrapedShopItem item) {
		delegate.onNewShopItem(item);
	}

	@Override
	public void onEndScraping() {
		delegate.onStartScraping();
	}
	
}
