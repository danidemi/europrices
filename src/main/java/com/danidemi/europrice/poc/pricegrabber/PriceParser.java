package com.danidemi.europrice.poc.pricegrabber;

public class PriceParser {
    
    public long parse(String price) throws ParserException {
        try{
            return Long.parseLong( price.replaceAll("[^\\d]", ""));
        }catch(NumberFormatException e){
            throw new ParserException(e);
        }
            
        
    }
    
}
