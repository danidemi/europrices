package com.danidemi.europrice.web.controller.api;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.danidemi.europrice.db.ProductItem;
import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.Shop;
import com.danidemi.europrice.db.ShopRepository;
import com.danidemi.europrice.utils.Utils.Language;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/ctx.xml")
@ActiveProfiles("test")
public class Api_0_0_1Test {

    @Autowired WebApplicationContext wac;

    @Autowired ShopRepository shopRepository;
    @Autowired ProductItemRepository itemRepository;
    
    MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void shouldSearchForTerm() throws Exception {
    	
    	fixture();
    	
    	
    	
    	
        this.mockMvc.perform(get("/api/search?searchTerms=compact")
        	.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.[0].priceInEuroCent").value(90_00))
            .andExpect(jsonPath("$.[1].priceInEuroCent").value(91_00))
            .andExpect(jsonPath("$.[2].priceInEuroCent").value(100_00))
            .andExpect(jsonPath("$.[3].priceInEuroCent").value(110_00));
    }

	private void fixture() {
		{
    	Shop shop = new Shop();
    	shop.setName("telefonini.it");
    	
    	ProductItem zamzungCompact = shop.newProductItem();
    	zamzungCompact.withKeywordsIn("zamzung compact 2");
    	zamzungCompact.setDetailsURL("http://url1");
    	zamzungCompact.setPriceInCent(90_00L);
    	zamzungCompact.setLanguage(Language.it);
    	
    	ProductItem zamzungCompact3 = shop.newProductItem();
    	zamzungCompact3.withKeywordsIn("zamzung compact 3");
    	zamzungCompact3.setDetailsURL("http://url2");
    	zamzungCompact3.setPriceInCent(91_00L);
    	zamzungCompact3.setLanguage(Language.it);
    	
    	ProductItem glPreviux = shop.newProductItem();
    	glPreviux.withKeywordsIn("gl previux");
    	glPreviux.setDetailsURL("http://url3");
    	glPreviux.setPriceInCent(92_00L);
    	glPreviux.setLanguage(Language.it);
    	
    	itemRepository.save( Arrays.asList( zamzungCompact, zamzungCompact3, glPreviux ) );
    	}
    	
    	{
	    	Shop shop = new Shop();
	    	shop.setName("telefonini.fr");
	    	
	    	ProductItem zamzungCompact = shop.newProductItem();
	    	zamzungCompact.withKeywordsIn("zamzung compact");
	    	zamzungCompact.setDetailsURL("http://url4");
	    	zamzungCompact.setPriceInCent(100_00L);
	    	zamzungCompact.setLanguage(Language.it);
	    	
	    	ProductItem zamzungCompact3 = shop.newProductItem();
	    	zamzungCompact3.withKeywordsIn("zamzung compact");
	    	zamzungCompact3.setDetailsURL("http://url5");
	    	zamzungCompact3.setPriceInCent(110_00L);
	    	zamzungCompact3.setLanguage(Language.it);
	    	
	    	ProductItem glPreviux = shop.newProductItem();
	    	glPreviux.withKeywordsIn("gl previux");
	    	glPreviux.setDetailsURL("http://url6");
	    	glPreviux.setPriceInCent(120_00L);
	    	glPreviux.setLanguage(Language.it);
			itemRepository.save(Arrays.asList(zamzungCompact, zamzungCompact3,
					glPreviux));
    	}
    	
    	{
	    	Shop shop = new Shop();
	    	shop.setName("moviles.es");
	    		    	
	    	ProductItem enginolaPreviux = shop.newProductItem();
	    	enginolaPreviux.withKeywordsIn("enginola previux");
	    	enginolaPreviux.setDetailsURL("http://url7");
	    	enginolaPreviux.setPriceInCent(77_00L);
	    	enginolaPreviux.setLanguage(Language.it);
			itemRepository.save(Arrays.asList(enginolaPreviux));
    	}    	
	}
	
}
