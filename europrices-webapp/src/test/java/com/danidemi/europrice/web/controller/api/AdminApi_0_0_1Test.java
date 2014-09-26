package com.danidemi.europrice.web.controller.api;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.SearchRepository;
import com.danidemi.europrice.db.ShopRepository;
import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProduct;
import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProductCallback;
import com.danidemi.europrice.utils.Json;
import com.danidemi.europrice.utils.Utils.Language;

public class AdminApi_0_0_1Test {

	@Test public void shouldStore() throws URISyntaxException, Exception {
		
		AdminApi_0_0_1 adminApi_0_0_1 = new AdminApi_0_0_1();
		
		adminApi_0_0_1.productItemRep = mock(ProductItemRepository.class);
		adminApi_0_0_1.searchRepository = mock(SearchRepository.class);
		adminApi_0_0_1.shopRepo = mock(ShopRepository.class);
		adminApi_0_0_1.callback = mock(ScrapedProductCallback.class);
		
		MockMvc mockMvc = MockMvcBuilders
				.standaloneSetup( adminApi_0_0_1 )
				.build();
		
		ScrapedProduct product = new ScrapedProduct();
		product.setDescription("description");
		product.setLanguage(Language.ca);
		product.setPriceInCent(12345L);
		product.setShopName("Shop");
		product.setUrlDetail(new URL("http://www.product.com/12"));
		
		List<ScrapedProduct> asList = Arrays.asList( product );
		
		MockHttpServletRequestBuilder body = post("/adminapi/storeProducts")
		.contentType( MediaType.parseMediaType( "application/json" ))
		.content(new Json().toJson(asList));

		ResultActions action = mockMvc.perform( body );
		
		action.andExpect( status().isOk() );
		
	
	}
	
	
	
}
