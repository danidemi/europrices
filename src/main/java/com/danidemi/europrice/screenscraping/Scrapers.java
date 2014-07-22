package com.danidemi.europrice.screenscraping;

import org.openqa.selenium.WebElement;

public final class Scrapers {

	public static final Scraper<String> text(){
		return new Scraper<String>() {
			
			@Override
			public String get(WebElement we) {
				return we.getText();
			}
		};
	}
	
	public static final Scraper<String> attribute(final String attribName){
		return new Scraper<String>() {
			
			@Override
			public String get(WebElement we) {
				return we.getAttribute(attribName);
			}
		};
	}	
	
	private Scrapers() {
		throw new UnsupportedOperationException("Not meant to be instantiated");
	}
	
}
