package com.danidemi.jlubricant.screenscraping.selector;

import org.openqa.selenium.WebElement;

/**
 * Fluent factory methods for selectors.
 * @author danidemi
 *
 */
public final class InsideElementSelectors {

	public static final InsideElementSelector<String> text(){
		return new InsideElementSelector<String>() {
			
			@Override
			public String get(WebElement we) {
				return we.getText();
			}
		};
	}
	
	public static final InsideElementSelector<String> attribute(final String attribName){
		return new InsideElementSelector<String>() {
			
			@Override
			public String get(WebElement we) {
				return we.getAttribute(attribName);
			}
		};
	}	
	
	private InsideElementSelectors() {
		throw new UnsupportedOperationException("Not meant to be instantiated");
	}
	
}
