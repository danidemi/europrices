package com.danidemi.europrice.poc.pricegrabber;

import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
public class PriceParserTest {

	@Test public void shouldParseDifferentStringsAsPriceInCent() throws ParserException {
		
		assertThat( new PriceParser().parse("1.09000"), equalTo(109000L) );
		
	}
	
}
