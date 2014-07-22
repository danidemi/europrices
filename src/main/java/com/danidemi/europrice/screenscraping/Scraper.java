package com.danidemi.europrice.screenscraping;

import org.openqa.selenium.WebElement;

/**
 * A Scraper select and extracts a minimal info.
 */
public interface Scraper<T> {
	
	T get(WebElement we);
	
}
