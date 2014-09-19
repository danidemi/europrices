package com.danidemi.europrice.pricegrabber.screenscraping.action;

import org.junit.Test;

import com.danidemi.europrice.pricegrabber.screenscraping.action.PriceParser;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
public class PriceParserTest {

	@Test public void shouldParseDifferentStringsAsPriceInCent() throws ParserException {
		
		assertThat( new PriceParser().parse("1.09000"), equalTo(109000L) );
		
	}
	
}
