package com.danidemi.europrice.poc.pricegrabber;

import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PriceParserSuccessTest {
    
    @Parameters
    public static List<Object[]> params() {
        return Arrays.asList( 
                new Object[]{"64,00 €", 6400L},
                new Object[]{"1 159,00 €", 115900L},
                new Object[]{"579,00 €", 57900L}
        );
    }
    
    private final String asRetrievedFromHtml;
    private final Long asExpected;
    
    public PriceParserSuccessTest( String asRetrievedFromHtml, Long asExpected ){
        this.asRetrievedFromHtml = asRetrievedFromHtml;
        this.asExpected = asExpected;
    }
    
    @Test
    public void shouldParsePriceAsExpected() {
        assertThat( new PriceParser().parse( asRetrievedFromHtml ), is( asExpected ));
    }
    
}
