package com.danidemi.europrice.poc.pricegrabber;

/**
 *
 * @author danidemi
 */
class ParserException extends Exception {

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
