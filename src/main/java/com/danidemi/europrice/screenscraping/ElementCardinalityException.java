package com.danidemi.europrice.screenscraping;

public abstract class ElementCardinalityException extends ScrapeException {

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
