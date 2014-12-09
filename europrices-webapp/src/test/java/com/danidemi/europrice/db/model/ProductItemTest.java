package com.danidemi.europrice.db.model;

import org.junit.Test;

import com.danidemi.europrice.db.model.ProductItem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ProductItemTest {
	
	@Test 
	public void shouldReturnADescription(){
		
		// given
		ProductItem tested = new ProductItem();
		tested.withKeywords("key1", "key2");
		
		// when
		String shortDescription = tested.getShortDescription();
		
		// then
		assertThat(shortDescription, equalTo("key1 key2"));
		
	}

	@Test
	public void shouldStoreKeywords() {
		
		// given
		ProductItem tested = new ProductItem();
		
		// when
		tested.withKeywordsIn(" Zamzung   45-2234  8 mB RAM ");
		
		// then
		assertThat( tested.getKeywordsBundle(), equalTo("|zamzung|45-2234|8|mb|ram|") );
		
	}
		
}
