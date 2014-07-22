package com.danidemi.europrice.screenscraping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NotSuchElementException extends ElementCardinalityException {

	private static final long serialVersionUID = -2235996058192040382L;

	public NotSuchElementException(By fieldSelector, WebDriver driver) {
				
		super("No elements found using selector " + fieldSelector + " in page:\n" + driver.getPageSource());
	}

}
