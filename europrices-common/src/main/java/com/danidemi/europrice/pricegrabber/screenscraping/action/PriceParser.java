package com.danidemi.europrice.pricegrabber.screenscraping.action;


public class PriceParser {
    
    public long parse(String price) throws ParserException {
        try{
            return Long.parseLong( price.replaceAll("[^\\d]", ""));
        }catch(NumberFormatException e){
            throw new ParserException(e);
        }
            
        
    }
    
}
