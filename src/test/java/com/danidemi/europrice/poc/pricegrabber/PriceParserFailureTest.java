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

public class PriceParserFailureTest {
        
    @Test(expected = ParserException.class)
    public void shouldParsePriceAsExpected() {
        new PriceParser().parse( " -- not a price -- " );
    }
    
}
