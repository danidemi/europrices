package com.danidemi.europrice.db;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ProductItemTest {

	@Test
	public void shouldStoreKeywords() {
		
		// given
		ProductItem tested = new ProductItem();
		
		// when
		tested.setKeywordsBundle(" Zamzung   45-2234  8 mB RAM ");
		
		// then
		assertThat( tested.getKeywordsBundle(), equalTo("|zamzung|45-2234|8|mb|ram|") );
		
	}
	
	@Test
	public void shouldStoreKeywordsWithPipes() {
		
		// given
		ProductItem tested = new ProductItem();
		
		// when
		tested.setKeywordsBundle(" product ||Gost|| ");
		
		// then
		assertThat( tested.getKeywordsBundle(), equalTo("|product|||||gost|||||") );
		
	}	
	
}
