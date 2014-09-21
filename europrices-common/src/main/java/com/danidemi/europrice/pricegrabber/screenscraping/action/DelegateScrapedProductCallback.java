package com.danidemi.europrice.pricegrabber.screenscraping.action;


public class DelegateScrapedProductCallback implements ScrapedProductCallback {

	private ScrapedProductCallback delegate;
	
	public DelegateScrapedProductCallback() {
	}
	
	public DelegateScrapedProductCallback(ScrapedProductCallback delegate) {
		super();
		this.delegate = delegate;
	}

	public final void setDelegate(ScrapedProductCallback delegate) {
		this.delegate = delegate;
	}

	@Override
	public void onStartScraping() {
		delegate.onStartScraping();
	}

	@Override
	public void onNewShopItem(ScrapedProduct item) {
		delegate.onNewShopItem(item);
	}

	@Override
	public void onEndScraping() {
		delegate.onStartScraping();
	}
	
}
