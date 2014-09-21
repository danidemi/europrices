package com.danidemi.jlubricant.screenscraping.context;

import com.danidemi.jlubricant.screenscraping.ScreenScrapingException;


public abstract class ElementCardinalityException extends ScreenScrapingException {

	private static final long serialVersionUID = -6493755734411454539L;

	public ElementCardinalityException() {
		super();
	}

	public ElementCardinalityException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElementCardinalityException(String message) {
		super(message);
	}

	public ElementCardinalityException(Throwable cause) {
		super(cause);	
	}

}
