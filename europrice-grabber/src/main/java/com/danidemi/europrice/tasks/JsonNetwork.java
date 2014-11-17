package com.danidemi.europrice.tasks;

import java.io.IOException;
import java.util.List;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProduct;
import com.danidemi.europrice.utils.Json;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public abstract class JsonNetwork {

	protected Json mapper = new Json();

	protected String map(List<ScrapedProduct> productsToStore) throws JsonProcessingException {
		String json = mapper.toJson(productsToStore);
		return json;
	}

	public void storeNewProducts(List<ScrapedProduct> productsToStore)
			throws JsonGenerationException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		
	}		

}
