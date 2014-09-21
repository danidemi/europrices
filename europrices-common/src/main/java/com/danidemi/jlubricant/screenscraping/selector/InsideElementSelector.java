package com.danidemi.jlubricant.screenscraping.selector;

import org.openqa.selenium.WebElement;

/**
 * Implementations are able to extract a single piece of info from a WebElement.
 * This is needed for CSS selectors that are able just to select elements, instead of
 * XPath expression that are able to select attributes. 
 */
public interface InsideElementSelector<T> {
	
	T get(WebElement we);
	
}
