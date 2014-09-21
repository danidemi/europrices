package com.danidemi.europrice.pricegrabber.screenscraping.action;

/**
 *
 * @author danidemi
 */
public class ParserException extends Exception {

    public ParserException() {
        super();
    }

    public ParserException(String message) {
        super(message);
    }

    ParserException(NumberFormatException e) {
        super(e);
    }
    
}
