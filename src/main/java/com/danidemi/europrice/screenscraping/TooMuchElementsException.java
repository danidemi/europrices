package com.danidemi.europrice.screenscraping;

import org.openqa.selenium.By;

public class TooMuchElementsException extends ElementCardinalityException {

	private static final long serialVersionUID = 6600964624382684619L;
	
	public TooMuchElementsException(By fieldSelector, int expected) {
		super("Wrong number pf elements found using selector " + fieldSelector + ". " + expected + " was expected.");
	}


}
