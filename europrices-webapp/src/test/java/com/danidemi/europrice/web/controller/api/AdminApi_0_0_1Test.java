package com.danidemi.europrice.web.controller.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProduct;
import com.danidemi.europrice.utils.Json;
import com.danidemi.europrice.utils.Utils.Language;

public class AdminApi_0_0_1Test {

	@Test public void shouldStore() throws URISyntaxException, Exception {
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup( new AdminApi_0_0_1() ).build();
		
		ScrapedProduct product = new ScrapedProduct();
		product.setDescription("description");
		product.setLanguage(Language.ca);
		product.setPriceInCent(12345L);
		product.setShopName("Shop");
		product.setUrlDetail(new URL("http://www.product.com/12"));
		
		MockHttpServletRequestBuilder body = post("/adminApi/storeProducts")
		.contentType( MediaType.parseMediaType( "application/json" ))
		.content(new Json().toJson(product));
		ResultActions action = mockMvc.perform( body );
		
		action.andExpect( status().isOk() );
		
	
	}
	
	
	
}
